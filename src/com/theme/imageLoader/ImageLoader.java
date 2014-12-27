package com.theme.imageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageLoader {
	DiskLruCache memoryCache;
	FileCache fileCache;
	public static Drawable d;
	Context c;
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	File cacheDir;
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	int width = 0;

	public ImageLoader(Context context) {
		// Make the background thead low priority. This way it will not affect
		// the UI performance
		this.c = context;
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

		fileCache = new FileCache(context);
	}

	public static File getDiskCacheDir(Context context, String uniqueName) {
		// Check if media is mounted or storage is built-in, if so, try and use
		// external cache dir
		// otherwise use internal cache dir
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Utils.isExternalStorageRemovable() ? Utils
				.getExternalCacheDir(context).getPath() : context.getCacheDir()
				.getPath();

		return new File(cachePath + File.separator + uniqueName);
	}

	// final int stub_id=R.drawable.stub;
	public Bitmap DisplayImage(String url, ImageView imageView, int width) {
		this.width = width;
		DiskLruCache.openCache(c, getDiskCacheDir(c, "thumbnail"),
				DISK_CACHE_SIZE);
		memoryCache = new DiskLruCache(getDiskCacheDir(c, "thumbnail"),
				DISK_CACHE_SIZE);
		memoryCache.put(url, getBitmap(url));
		// imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {

			// imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			// imageView.setImageBitmap(bitmap);

			Log.v("first", "first");
		} else {
			Bitmap bit=queuePhoto(url, imageView);
			Log.v("second", "second");

		}
		//clearCache();
		return bitmap;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void DisplayImageNew(String url, ImageView imageView,
			RelativeLayout rlayout) {
		memoryCache.put(url, getBitmap(url));
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			d = new BitmapDrawable(c.getResources(), bitmap);

			rlayout.setBackgroundDrawable(d);
			// imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			// imageView.setImageBitmap(bitmap);

			Log.v("first", "first");
		} else {
			queuePhoto(url, imageView);
			Log.v("second", "second");

		}
	}

	public void DisplayImg(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			// imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			// imageView.setImageBitmap(getRoundedCornerBitmap( bitmap,
			// 0,70,70));

			Log.v("first", "first");
		} else {
			queuePhoto(url, imageView);
			Log.v("second", "second");

		}
	}

	private Bitmap queuePhoto(String url, ImageView imageView) {
		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.
		photosQueue.Clean(imageView);
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}

		// start thread if it's not started yet
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
		return null;
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			addImageToGallery(bitmap);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}// Lalit

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = width;

			// Testing

			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	PhotosQueue photosQueue = new PhotosQueue();

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	// stores list of photos to download
	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		// removes all instances of this ImageView
		public void Clean(ImageView image) {
			for (int j = 0; j < photosToLoad.size();) {
				if (photosToLoad.get(j).imageView == image)
					photosToLoad.remove(j);
				else
					++j;
			}
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// thread waits until there are any images to load in the
					// queue
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							photoToLoad = photosQueue.photosToLoad.pop();
						}
						Bitmap bmp = getBitmap(photoToLoad.url);
						memoryCache.put(photoToLoad.url, bmp);
						String tag = imageViews.get(photoToLoad.imageView);
						
						if (tag != null && tag.equals(photoToLoad.url)) {
							BitmapDisplayer bd = new BitmapDisplayer(bmp,
									photoToLoad.imageView);
							Activity a = (Activity) photoToLoad.imageView
									.getContext();
							a.runOnUiThread(bd);
						}
					}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				// allow thread to exit
			}
		}
	}

	void addImageToGallery(Bitmap bmp) {

		//new ImageSave().execute(bmp);

		// SAVE in gallery
//		MediaStore.Images.Media.insertImage(c.getContentResolver(), bmp,
//				"test_" + System.currentTimeMillis() + ".jpg",
//				"SwipeCash Images");
	}
	
//	public static void addImageToGallery(final String filePath, final Context context) {
//
//	    ContentValues values = new ContentValues();
//
//	    values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
//	    values.put(Images.Media.MIME_TYPE, "image/jpeg");
//	    values.put(MediaStore.MediaColumns.DATA, filePath);
//
//	    context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
//	}

//	class ImageSave extends AsyncTask<Bitmap, Void, Void>{
//
//		@Override
//		protected Void doInBackground(Bitmap... arg0) {
//			// TODO Auto-generated method stub
//			OutputStream output;
//			 File filepath = Environment.getExternalStorageDirectory();
//			 File dir = new File(filepath.getAbsolutePath() + "/Swipe Cash/");
//			 dir.mkdirs();
//			 File file = new File(dir, "SwipeCash" + "_"
//			 + System.currentTimeMillis() + ".jpg");
//			
//			 try {
//			 output = new FileOutputStream(file);
//			 arg0[0].compress(Bitmap.CompressFormat.JPEG, 50, output);
//			 //MediaStore.Images.Media.insertImage(getContentResolver(),
//			 //yourBitmap, yourTitle , yourDescription);
//			 output.flush();
//			 output.close();
//			 // finish();
//			 // Toast.makeText(context,
//			 // "Image save to gallery.", 4000).show();
//			 }
//			
//			 catch (Exception e) {
//			
//			 e.printStackTrace();
//			 }
//			return null;
//		}
//		
//	}
	PhotosLoader photoLoaderThread = new PhotosLoader();

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap b, ImageView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			// if (bitmap != null)
			// imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));

		}
	}

	public void clearCache() {
		memoryCache.clearCache();
		fileCache.clear();
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels,
			int width, int height) {

		Bitmap output = Bitmap.createScaledBitmap(bitmap, width, height, true);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// public void DisplayImage(String replaceAll, ImageView img) {
	// // TODO Auto-generated method stub
	//
	// }
}
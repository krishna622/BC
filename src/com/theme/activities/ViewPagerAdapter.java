package com.theme.activities;

import com.theme.R;
import com.theme.imageLoader.ImageLoader;

import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPagerAdapter extends PagerAdapter {

	Context context;
	LayoutInflater inflater;
	private String[] url = {
			"https://www.google.co.in/search?q=hd+wallpapers+720x1280&espv=2&biw=852&bih=423&tbm=isch&tbo=u&source=univ&sa=X&ei=IY2eVJ70MdCXuQTatoH4AQ&ved=0CBsQsAQ#facrc=_&imgdii=_&imgrc=5plnJNbcAXFXEM%253A%3BwrqwFVB2PdseAM%3Bhttp%253A%252F%252Fwww.123mobilewallpapers.com%252Fwp-content%252Fuploads%252F2014%252F12%252Fchristmas_funny_mouse_wallpaper.jpg",
			"http://images.mygalaxys3wallpaper.com/Gallery/4_Buildings/My-Samsung-Galaxy-S3-Wallpaper-HD-Buildings(57).jpg",
			"http://imgprix.com/file/7561/720x1280/vibrant-colorful-sky-tree.jpg",
			"https://www.google.co.in/search?espv=2&biw=852&bih=423&tbm=isch&q=hd+wallpapers+720x1280+cars&revid=2037986545&sa=X&ei=JY2eVKrME8yjugTmlYKICQ&ved=0CCQQ1QIoAg&dpr=1.5&cad=cbv&sei=P5CeVLqWCZTkuQSw_ILgCQ#facrc=_&imgdii=_&imgrc=W1U6fn828MHv3M%253A%3BTwk4BbeC1NQVWM%3Bhttp%253A%252F%252Fstatic.wallarc.com%252F51a29e55d4f2075875.jpg",
			"http://wallpaperscraft.com/image/pussycat_dolls_car_girls_desert_road_2446_720x1280.jpg",
			"http://imagesearch.co/wp-content/uploads/2014/02/Sparkling-Blue-Universe-Wallpaper-5222-720x1080.jpg",
			"http://imagesearch.co/wp-content/uploads/2014/02/Colorful-Universe-Wallpaper-640x960.jpg",
			"http://www.wallpaper77.com/upload/DesktopWallpapers/cache/Dead-Space-Wallpaper-9-dead-space-wallpapers-game-wallpapers-720x1280.jpg",
			"http://www.wallpapermotion.com/wallpapers/720x1280/12283-Stalker-(www.WallpaperMotion.com).jpg",
			"http://images.hdwallpaperpics.net/5190c092492c355409.jpg",
			"http://www.samsung-wallpapers.com/uploads/allimg/131128/1-13112Q05R0.jpg",
			"http://c.mwp4.me/media/wallpapers_1080x1920/comics/1/2/batman-and-superman-comic-mobile-wallpaper-1080x1920-14023-243879998.jpg",
			"http://www.hdwallpaperbackground.com/uploads/allimg/130405/HD%20wallpapers%20pictures%20of%20famous%20Chinese%20singer%20Ada%2010_720x1280.jpg" };

	public ViewPagerAdapter(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return url.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

	
		// Button imgflag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//String fontPath = "corbel_0.ttf";
		//Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		View itemView = inflater.inflate(R.layout.image_pager, container,
				false);
		ImageView imageView=(ImageView)itemView.findViewById(R.id.ivFragFirst);
		ImageLoader imageLoader = new ImageLoader(context);
		Bitmap bitmap=imageLoader.DisplayImage(url[position], imageView,700);
		if(bitmap!=null){
			BitmapDrawable bitDrawable= new BitmapDrawable(context.getResources(), bitmap);
			imageView.setBackground(bitDrawable);
		}

		((ViewPager) container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);
	}

}

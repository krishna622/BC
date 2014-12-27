package com.theme.adapter;

import com.theme.fragment.FirstFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{

	private String []url={"https://www.google.co.in/search?q=hd+wallpapers+720x1280&espv=2&biw=852&bih=423&tbm=isch&tbo=u&source=univ&sa=X&ei=IY2eVJ70MdCXuQTatoH4AQ&ved=0CBsQsAQ#facrc=_&imgdii=_&imgrc=5plnJNbcAXFXEM%253A%3BwrqwFVB2PdseAM%3Bhttp%253A%252F%252Fwww.123mobilewallpapers.com%252Fwp-content%252Fuploads%252F2014%252F12%252Fchristmas_funny_mouse_wallpaper.jpg",
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
			"http://www.hdwallpaperbackground.com/uploads/allimg/130405/HD%20wallpapers%20pictures%20of%20famous%20Chinese%20singer%20Ada%2010_720x1280.jpg"};
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		switch(pos) {

		case 0: return FirstFragment.newInstance(url[0]);
		case 1: return FirstFragment.newInstance(url[1]);
		case 2: return FirstFragment.newInstance(url[2]);
		case 3: return FirstFragment.newInstance(url[3]);
		case 4: return FirstFragment.newInstance(url[4]);
		case 5: return FirstFragment.newInstance(url[5]);
		case 6: return FirstFragment.newInstance(url[6]);
		case 7: return FirstFragment.newInstance(url[7]);
		case 8: return FirstFragment.newInstance(url[8]);
		case 9: return FirstFragment.newInstance(url[9]);
		case 10: return FirstFragment.newInstance(url[10]);
		case 11: return FirstFragment.newInstance(url[11]);
		case 12: return FirstFragment.newInstance(url[12]);
		}
		return FirstFragment.newInstance(url[12]);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return url.length;
	}

}

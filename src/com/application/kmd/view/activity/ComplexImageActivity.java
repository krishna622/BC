package com.application.kmd.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.application.kmd.view.R;
import com.application.kmd.view.fragment.ImageGridFragment;
import com.application.kmd.view.fragment.ImageListFragment;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ComplexImageActivity extends FragmentActivity {

	private static final String STATE_POSITION = "STATE_POSITION";

	private ViewPager pager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_complex);

		int pagerPosition = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_POSITION);

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager()));
		pager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentPagerAdapter {

		Fragment listFragment;
		Fragment gridFragment;

		ImagePagerAdapter(FragmentManager fm) {
			super(fm);
			listFragment = new ImageListFragment();
			gridFragment = new ImageGridFragment();
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return listFragment;
				case 1:
					return gridFragment;
				default:
					return null;
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return getString(R.string.title_list);
				case 1:
					return getString(R.string.title_grid);
				default:
					return null;
			}
		}
	}
}
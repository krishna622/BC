package com.theme.activities;

import com.theme.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class HomeActivity extends Activity {

	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		adapter = new ViewPagerAdapter(HomeActivity.this);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i2) {

			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});
		viewPager.setAdapter(adapter);

	}
}

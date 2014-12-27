package com.theme.activities;

import com.theme.R;
import com.theme.adapter.MyPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class HomeActivity extends FragmentActivity {

	private ViewPager pager;
	private MyPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		pager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);
	}
}

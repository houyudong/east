package com.xflyme.go4.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PropertyFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	public PropertyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public void addFragment(Fragment fragment) {
		fragments.add(fragment);
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}

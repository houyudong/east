package com.xflyme.go4.activity;

import java.util.ArrayList;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity implements OnPageChangeListener {
	private ViewPager mViewPager;
	private int currIndex = 0;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;

	private Button btnSkip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide2);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setOnPageChangeListener(this);

		btnSkip = (Button) findViewById(R.id.btn_guide_skip);
		btnSkip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PropertyApplication.mUserCache.setFirst(false);
				Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});

		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.guide_page1, null);
		View view2 = mLi.inflate(R.layout.guide_page2, null);
		View view3 = mLi.inflate(R.layout.guide_page3, null);

		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		final ArrayList<String> titles = new ArrayList<String>();
		titles.add("①");
		titles.add("②");
		titles.add("③");

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titles.get(position);
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
	}

	public void startbutton(View v) {
		Intent intent = new Intent();
		intent.setClass(GuideActivity.this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		Animation animation = null;
		switch (position) {
		case 0:
			mPage0.setImageDrawable(getResources().getDrawable(R.drawable.guide_selected));
			mPage1.setImageDrawable(getResources().getDrawable(R.drawable.guide_unselect));
			if (currIndex == position + 1) {
				animation = new TranslateAnimation(20 * (position + 1), 20 * position, 0, 0);
			} /*
				 * else if (currIndex == 2) { animation = new
				 * TranslateAnimation(20*2, 0, 0, 0); }
				 */
			break;
		case 1:
			mPage1.setImageDrawable(getResources().getDrawable(R.drawable.guide_selected));
			mPage0.setImageDrawable(getResources().getDrawable(R.drawable.guide_unselect));
			mPage2.setImageDrawable(getResources().getDrawable(R.drawable.guide_unselect));
			if (currIndex == position - 1) {
				animation = new TranslateAnimation(20 * (position - 1), 20 * position, 0, 0);

			} else if (currIndex == position + 1) {
				animation = new TranslateAnimation(20 * (position + 1), 20 * position, 0, 0);
			}
			break;
		case 2:
			mPage2.setImageDrawable(getResources().getDrawable(R.drawable.guide_selected));
			mPage1.setImageDrawable(getResources().getDrawable(R.drawable.guide_unselect));
			if (currIndex == position - 1) {
				animation = new TranslateAnimation(20 * (position - 1), 20 * position, 0, 0);
			}
			break;
		}
		currIndex = position;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		// mPageImg.startAnimation(animation);
	}

}

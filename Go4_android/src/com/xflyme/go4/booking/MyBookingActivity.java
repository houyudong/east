package com.xflyme.go4.booking;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xflyme.go4.R;
import com.xflyme.go4.adapter.PropertyFragmentPagerAdapter;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class MyBookingActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private Button btnCurrent;
	private Button btnHistory;

	private ViewPager vpBooking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);

		context = MyBookingActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.mybooking_title).toUpperCase());

		btnCurrent = (Button) findViewById(R.id.switch_button1);
		btnHistory = (Button) findViewById(R.id.switch_button2);

		btnCurrent.setSelected(true);
		btnCurrent.setText(getString(R.string.booking_current).toUpperCase());
		btnHistory.setText(getString(R.string.booking_history).toUpperCase());

		btnCurrent.setOnClickListener(this);
		btnHistory.setOnClickListener(this);

		vpBooking = (ViewPager) findViewById(R.id.vp_managerment);
		PropertyFragmentPagerAdapter pagerAdapter = new PropertyFragmentPagerAdapter(getSupportFragmentManager());

		CurrentBookFragment currentBookFragment = new CurrentBookFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", 1);
		currentBookFragment.setArguments(bundle);

		CurrentBookFragment historyBookFragment = new CurrentBookFragment();
		Bundle bundle1 = new Bundle();
		bundle1.putInt("type", 2);
		historyBookFragment.setArguments(bundle1);

		pagerAdapter.addFragment(currentBookFragment);
		pagerAdapter.addFragment(historyBookFragment);

		vpBooking.setOffscreenPageLimit(pagerAdapter.getCount());
		vpBooking.setAdapter(pagerAdapter);
		vpBooking.setOnPageChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.switch_button1:
			btnCurrent.setSelected(true);
			btnHistory.setSelected(false);
			vpBooking.setCurrentItem(0);
			break;

		case R.id.switch_button2:
			btnCurrent.setSelected(false);
			btnHistory.setSelected(true);
			vpBooking.setCurrentItem(1);
			break;

		default:
			break;
		}
	}

	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		if (position == 0) {
			btnCurrent.setSelected(true);
			btnHistory.setSelected(false);
		} else if (position == 1) {
			btnCurrent.setSelected(false);
			btnHistory.setSelected(true);

		}

	}

}

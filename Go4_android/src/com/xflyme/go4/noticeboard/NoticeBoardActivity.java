package com.xflyme.go4.noticeboard;


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

import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.adapter.PropertyFragmentPagerAdapter;

/**
 * 
 * @Description:Notice Board
 * @author:lxf
 * @see:
 * @since:
 * @Date:2015-06-28
 */
public class NoticeBoardActivity extends FragmentActivity implements OnPageChangeListener,
		OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private Button btnRecent;
	private Button btnPost;

	private ViewPager vpManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticeboard);

		context = NoticeBoardActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.noticeboard_title).toUpperCase());

		btnRecent = (Button) findViewById(R.id.switch_button1);
		btnPost = (Button) findViewById(R.id.switch_button2);

		btnRecent.setSelected(true);
		btnRecent.setText(getString(R.string.noticeboard_recent).toUpperCase());
		btnPost.setText(getString(R.string.noticeboard_post).toUpperCase());

		btnRecent.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		
		vpManager = (ViewPager) findViewById(R.id.vp_managerment);
		PropertyFragmentPagerAdapter pagerAdapter = new PropertyFragmentPagerAdapter(getSupportFragmentManager());
		
		RecentNoticeFragment recentFragment = new RecentNoticeFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("category", PropertyConstant.NOTICE_TYPE_RECENT);
		recentFragment.setArguments(bundle);
		
		RecentNoticeFragment postFargment = new RecentNoticeFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putInt("category", PropertyConstant.NOTICE_TYPE_POST);
		postFargment.setArguments(bundle2);
		
		
		pagerAdapter.addFragment(recentFragment);
		pagerAdapter.addFragment(postFargment);
		
		vpManager.setOffscreenPageLimit(pagerAdapter.getCount());
		vpManager.setAdapter(pagerAdapter);
		vpManager.setOnPageChangeListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.switch_button1:
			btnRecent.setSelected(true);
			btnPost.setSelected(false);
			vpManager.setCurrentItem(0);
			break;
			
		case R.id.switch_button2:
			btnRecent.setSelected(false);
			btnPost.setSelected(true);
			vpManager.setCurrentItem(1);

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
			btnRecent.setSelected(true);
			btnPost.setSelected(false);
		}else if (position == 1) {
			btnRecent.setSelected(false);
			btnPost.setSelected(true);
			
		}
		
	}

}

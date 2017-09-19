package com.xflyme.go4.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
public class ActivityActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;
	private RelativeLayout rlRight;

	private ImageView ivRight;

	private Button btnBaby;
	private Button btnEducation;
	private Button btnResale;
	private Button btnExercuse;

	private ViewPager vpManager;

	private List<Button> buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);

		context = ActivityActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlRight = (RelativeLayout) findViewById(R.id.rl_top_right);
		ivRight = (ImageView) findViewById(R.id.iv_title_right);

		ivRight.setImageResource(R.drawable.main_plus);
		

		rlLeft.setOnClickListener(this);
		rlRight.setOnClickListener(this);
		tvTitle.setText(getString(R.string.activities_title).toUpperCase());

		btnBaby = (Button) findViewById(R.id.switch_btn_baby);
		btnEducation = (Button) findViewById(R.id.switch_btn_education);
		btnResale = (Button) findViewById(R.id.switch_btn_resale);
		btnExercuse = (Button) findViewById(R.id.switch_btn_exercuse);

		btnBaby.setOnClickListener(this);
		btnEducation.setOnClickListener(this);
		btnResale.setOnClickListener(this);
		btnExercuse.setOnClickListener(this);

		buttons = new ArrayList<Button>();

		buttons.add(btnBaby);
		buttons.add(btnEducation);
		buttons.add(btnResale);
		buttons.add(btnExercuse);

		btnBaby.setSelected(true);

		vpManager = (ViewPager) findViewById(R.id.vp_managerment);
		PropertyFragmentPagerAdapter pagerAdapter = new PropertyFragmentPagerAdapter(getSupportFragmentManager());

		ActivityListFragment babyFragment = new ActivityListFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("category", 100);
		babyFragment.setArguments(bundle);

		ActivityListFragment educationFragment = new ActivityListFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putInt("category", 101);
		educationFragment.setArguments(bundle2);

		ActivityListFragment resaleFragment = new ActivityListFragment();
		Bundle bundle3 = new Bundle();
		bundle3.putInt("category", 102);
		resaleFragment.setArguments(bundle3);

		ActivityListFragment exercuseFragment = new ActivityListFragment();
		Bundle bundle4 = new Bundle();
		bundle4.putInt("category", 103);
		exercuseFragment.setArguments(bundle4);

		pagerAdapter.addFragment(babyFragment);
		pagerAdapter.addFragment(educationFragment);
		pagerAdapter.addFragment(resaleFragment);
		pagerAdapter.addFragment(exercuseFragment);

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

		case R.id.rl_top_right:
			Intent intent = new Intent(context, AddActivityActivity.class);
			startActivity(intent);
			break;

		case R.id.switch_btn_baby:
			switchButtons(0);
			vpManager.setCurrentItem(0);
			break;

		case R.id.switch_btn_education:
			switchButtons(1);
			vpManager.setCurrentItem(1);
			break;

		case R.id.switch_btn_resale:
			switchButtons(2);
			vpManager.setCurrentItem(2);
			break;

		case R.id.switch_btn_exercuse:
			switchButtons(3);
			vpManager.setCurrentItem(3);
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
		switchButtons(position);
	}

	void switchButtons(int i) {
		vpManager.setCurrentItem(i);
		for (int j = 0; j < buttons.size(); j++) {
			buttons.get(j).setSelected(false);
		}
		buttons.get(i).setSelected(true);

	}

}

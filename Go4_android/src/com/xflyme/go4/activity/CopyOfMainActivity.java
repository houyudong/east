package com.xflyme.go4.activity;

import java.util.ArrayList;

import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xflyme.go4.R;
import com.xflyme.go4.activities.ActivityActivity;
import com.xflyme.go4.bill.BillActivity;
import com.xflyme.go4.bookfacility.BookFacilityActivity;
import com.xflyme.go4.booking.MyBookingActivity;
import com.xflyme.go4.entity.HomePageEntity;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.entity.HomePageEntity.HomePageItem;
import com.xflyme.go4.feedback.FeedBackActivity;
import com.xflyme.go4.manager.ManagermentActivity;
import com.xflyme.go4.me.MeActivity;
import com.xflyme.go4.noticeboard.NoticeBoardActivity;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class CopyOfMainActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener ,HomePageObserver {

	private Context context;
	private RelativeLayout rlManagerment;
	private RelativeLayout rlNoticeBoard;
	private RelativeLayout rlActivities;
	
	private final int IMAGE_CHANGE_TIME = 8000;


	// 轮播图
	private ViewPager viewPager;
	private LinearLayout llDotLayout;
	private RelativeLayout rlVpAd;
	private ImagePagerAdapter imagePagerAdapter;
	private int currentPagePosition;

	private TextView tvPropertyName;
	private TextView tvPropertyDesc;
	private ImageView ivArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = CopyOfMainActivity.this;
		HomePageLogic.getInstance().addObserver(this);
		initView();
		HomePageLogic.getInstance().onGetHomePage();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		HomePageLogic.getInstance().removeObserver(this);
	}
	
	void initView() {

		rlManagerment = (RelativeLayout) findViewById(R.id.rl_main_managerment);
		rlManagerment.setOnClickListener(this);

		rlNoticeBoard = (RelativeLayout) findViewById(R.id.rl_main_noticeboard);
		rlNoticeBoard.setOnClickListener(this);

		rlActivities = (RelativeLayout) findViewById(R.id.rl_main_activities);
		rlActivities.setOnClickListener(this);

		// 轮播图
		rlVpAd = (RelativeLayout) findViewById(R.id.rl_main_ad);
		viewPager = (ViewPager) findViewById(R.id.vp_main_picture);
		llDotLayout = (LinearLayout) findViewById(R.id.ll_main_dot);

		imagePagerAdapter = new ImagePagerAdapter(context,
				new ArrayList<HomePageItem>());
		viewPager.setAdapter(imagePagerAdapter);
		viewPager.setOnPageChangeListener(this);

		tvPropertyDesc = (TextView) findViewById(R.id.tv_main_property_desc);
		tvPropertyName = (TextView) findViewById(R.id.tv_main_property_name);

		tvPropertyDesc.setText(PropertyApplication.mUserCache.getUserInfo()
				.getData().getCurCommunityName());
		tvPropertyName.setText(PropertyApplication.mUserCache.getUserInfo()
				.getData().getCurCommunityName());
		
		UserInfoEntity user = PropertyApplication.mUserCache.getUserInfo();
		
		ivArrow = (ImageView) findViewById(R.id.main_arrow);
		ivArrow.setOnClickListener(this);
		
		if (user.getData().getRelations().size() > 1) {
			ivArrow.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.main_feed_btn:
			intent.setClass(context, FeedBackActivity.class);
			startActivity(intent);
			break;

		case R.id.main_booking_btn:
			intent.setClass(context, MyBookingActivity.class);
			startActivity(intent);
			break;

		case R.id.main_setting_btn:
			intent.setClass(context, MeActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_main_managerment:
			intent.setClass(context, ManagermentActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_main_bookfacility:
			intent.setClass(context, BookFacilityActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_main_bill:
			intent.setClass(context, BillActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_main_noticeboard:
			intent.setClass(context, NoticeBoardActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_main_activities:
			intent.setClass(context, ActivityActivity.class);
			startActivity(intent);
			break;
			
		case R.id.main_arrow:
			intent.setClass(context, SelectPropertyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("userInfoEntity", PropertyApplication.mUserCache.getUserInfo());
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	public void initHeadImageViewPager(ArrayList<HomePageItem> items) {

		if (items == null || items.size() == 0) {
			viewPager.setVisibility(View.GONE);
			rlVpAd.setVisibility(View.GONE);
			llDotLayout.setVisibility(View.GONE);
		} else {
			rlVpAd.setVisibility(View.VISIBLE);
			int mationSize = items.size();
			viewPager.setVisibility(View.VISIBLE);
			if (mationSize > 1) {
				llDotLayout.setVisibility(View.VISIBLE);
				if (llDotLayout.getChildCount() != mationSize) {
					llDotLayout.removeAllViews();
					for (int i = 0; i < mationSize; i++) {
						ImageView dotImage = new ImageView(context);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								15, 15);
						params.rightMargin = 5;
						dotImage.setLayoutParams(params);
						if (i == currentPagePosition % mationSize) {
							dotImage.setBackgroundResource(R.drawable.guide_selected);
						} else {
							dotImage.setBackgroundResource(R.drawable.guide_unselect);
						}
						llDotLayout.addView(dotImage);
					}
				}
			} else {
				llDotLayout.setVisibility(View.GONE);
			}
			imagePagerAdapter.refresh(items);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		if (llDotLayout.getChildCount() > 1) {
			int newPosition = position % llDotLayout.getChildCount();
			for (int i = 0; i < llDotLayout.getChildCount(); i++) {
				llDotLayout.getChildAt(i).setBackgroundResource(
						R.drawable.guide_unselect);
			}
			llDotLayout.getChildAt(newPosition).setBackgroundResource(
					R.drawable.guide_selected);
		}
	}

	@Override
	public void onGetHomePageSuccess(HomePageEntity homePageEntity) {

		rlVpAd.setVisibility(View.VISIBLE);
		initHeadImageViewPager(homePageEntity.getData());
		imageHandler.postDelayed(imageRunnable, IMAGE_CHANGE_TIME);

	}
	
	private Runnable imageRunnable = new Runnable() {
		@Override
		public void run() {
			imageHandler.postDelayed(this, IMAGE_CHANGE_TIME);
		}
	};
	
	
	@SuppressLint("HandlerLeak")
	private final Handler imageHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			if (imagePagerAdapter.getSize() > 1) {

				viewPager.setCurrentItem(
						viewPager.getCurrentItem() + 1, true);
				if (llDotLayout.getChildCount() > 1) {
					for (int i = 0; i < llDotLayout.getChildCount(); i++) {
						llDotLayout.getChildAt(i).setBackgroundResource(
								R.drawable.guide_unselect);
					}
					llDotLayout.getChildAt(
							viewPager.getCurrentItem()
									% llDotLayout.getChildCount())
							.setBackgroundResource(
									R.drawable.guide_selected);
				}
			}
		}
	};


	@Override
	public void onGetHomePageFail(int resultCode, String message) {
		rlVpAd.setVisibility(View.GONE);
	}

}

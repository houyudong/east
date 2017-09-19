package com.xflyme.go4.activity;


import com.xflyme.go4.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.xflyme.go4.R;
import com.xflyme.go4.activities.ActivityActivity;
import com.xflyme.go4.bill.BillActivity;
import com.xflyme.go4.bookfacility.BookFacilityActivity;
import com.xflyme.go4.booking.MyBookingActivity;
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
public class MainActivity extends BaseActivity implements OnClickListener {

	private Context context;
	private RelativeLayout rlManagerment;
	private RelativeLayout rlNoticeBoard;
	private RelativeLayout rlActivities;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		context = MainActivity.this;
		initView();
		HomePageLogic.getInstance().onGetHomePage();
	}
	
	void initView() {

		rlManagerment = (RelativeLayout) findViewById(R.id.rl_main_managerment);
		rlManagerment.setOnClickListener(this);

		rlNoticeBoard = (RelativeLayout) findViewById(R.id.rl_main_noticeboard);
		rlNoticeBoard.setOnClickListener(this);

		rlActivities = (RelativeLayout) findViewById(R.id.rl_main_activities);
		rlActivities.setOnClickListener(this);

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
			

		default:
			break;
		}
	}



}

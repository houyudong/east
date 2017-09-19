package com.xflyme.go4.me;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.activities.MyActivitiesActivity;
import com.xflyme.go4.activities.MyFavoritesActivity;
import com.xflyme.go4.activity.LoginActivity;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.view.CircleImageView;
import com.xflyme.go4.view.HaloToast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * TAB ME
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class MeActivity extends BaseActivity implements OnClickListener, LogoutObserver {

	private Context context;

	private RelativeLayout rlLeft;
	private TextView titleCenter;

	private CircleImageView ivHead;
	private TextView tvName;
	private TextView tvPhone;
	private TextView tvMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me);

		context = MeActivity.this;
		LogoutLogic.getInstance().addObserver(this);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		UserInfoEntity userInfo = PropertyApplication.mUserCache.getUserInfo();
		tvName.setText(userInfo.getData().getUserName());
		tvPhone.setText(userInfo.getData().getTelphone());
		tvMail.setText(userInfo.getData().getEmail());

		ImageLoader.getInstance().displayImage(userInfo.getData().getPicUrl(), ivHead);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogoutLogic.getInstance().removeObserver(this);
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		titleCenter = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);

		titleCenter.setText(getString(R.string.me_title));

		ivHead = (CircleImageView) findViewById(R.id.iv_me_head);
		tvName = (TextView) findViewById(R.id.tv_me_name);
		tvPhone = (TextView) findViewById(R.id.tv_me_phone);
		tvMail = (TextView) findViewById(R.id.tv_me_mail);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_me_info:
			intent = new Intent(context, MeUpdateActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_me_property:
			intent = new Intent(context, AddPropertyActivity.class);
			startActivity(intent);
			break;

		case R.id.rl_top_left:
			finish();
			break;

		case R.id.btn_me_password:
			intent = new Intent(context, ChangePasswordActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_me_favorites:
			intent = new Intent(context, MyFavoritesActivity.class);
			startActivity(intent);
			break;

		case R.id.btn_me_invite:
			Uri smsToUri = Uri.parse("smsto:");
			Intent intent1 = new Intent(Intent.ACTION_SENDTO, smsToUri);
			startActivity(intent1);
			break;

		case R.id.btn_me_activities:
			intent = new Intent(context, MyActivitiesActivity.class);
			startActivity(intent);
			break;

//		case R.id.btn_me_logout:  //新的设计文档去掉
//			UserInfoEntity userInfo = PropertyApplication.mUserCache.getUserInfo();
//			LogoutLogic.getInstance().onLogout(userInfo.getData().getUserid() + "");
//			HaloToast.showNewWaitDialog(context, false, getString(R.string.loading));
//			break;

		default:
			break;
		}
	}

	@Override
	public void onLogoutResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		if (!TextUtils.isEmpty(message)) {
			toast(message);
		}
		if (resultCode == 0) {
			PropertyApplication.mUserCache.setLogined(false);
			PropertyApplication.mUserCache.setToken("");
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);

			PropertyApplication app = (PropertyApplication) MeActivity.this.getApplication();
			app.finishAll();

			finish();
		}
	}
}

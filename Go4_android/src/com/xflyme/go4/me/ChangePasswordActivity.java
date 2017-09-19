package com.xflyme.go4.me;

import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.activity.LoginActivity;
import com.xflyme.go4.view.HaloToast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Change Password
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class ChangePasswordActivity extends BaseActivity implements OnClickListener, ChangePasswordObserver {

	private Context context;

	private RelativeLayout rlLeft;
	private TextView titleCenter;

	private TextView tvOld;
	private TextView tvNew;
	private TextView tvReNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);

		context = ChangePasswordActivity.this;
		ChangePasswrodLogic.getInstance().addObserver(this);

		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ChangePasswrodLogic.getInstance().removeObserver(this);
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		titleCenter = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);

		titleCenter.setText(getString(R.string.me_password));

		tvOld = (TextView) findViewById(R.id.et_password_old);
		tvNew = (TextView) findViewById(R.id.et_password_password);
		tvReNew = (TextView) findViewById(R.id.et_password_repeat);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_changepassword_confirm:

			if (TextUtils.isEmpty(tvOld.getText().toString())) {
				toast(getString(R.string.password_is_null));
				return;
			}

			if (TextUtils.isEmpty(tvNew.getText().toString())) {
				toast(getString(R.string.new_password_is_null));
				return;
			}

			if (tvNew.getText().toString().equals(tvOld.getText().toString())) {
				toast(getString(R.string.old_new_same));
				return;
			}

			if (TextUtils.isEmpty(tvReNew.getText().toString())) {
				toast(getString(R.string.new_password_repeat_is_null));
				return;
			}

			if (!tvNew.getText().toString().equals(tvReNew.getText().toString())) {
				toast(getString(R.string.password_different));
				return;
			}

			ChangePasswrodLogic.getInstance().onChangePassword(tvOld.getText().toString(), tvNew.getText().toString());
			HaloToast.showNewWaitDialog(context, false, "");

			break;

		default:
			break;
		}
	}

	@Override
	public void onChangePasswrodResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		if (resultCode == 0) {
			toast(getString(R.string.chgpwd_success));

			PropertyApplication.mUserCache.setLogined(false);
			PropertyApplication.mUserCache.setToken("");

			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);

			PropertyApplication app = (PropertyApplication) ChangePasswordActivity.this.getApplication();
			app.finishAll();

			finish();
		} else {
			if (TextUtils.isEmpty(message)) {
				toast(getString(R.string.chgpwd_fail));
			} else {
				toast(message);
			}
		}
	}
}

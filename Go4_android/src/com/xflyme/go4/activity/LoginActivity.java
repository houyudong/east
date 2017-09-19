package com.xflyme.go4.activity;

import com.xflyme.go4.PropertyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.util.RegexUtil;
import com.xflyme.go4.view.HaloToast;

/**
 * 
 * @Description:登录界面
 * @author:lxf
 * @see:
 * @since:
 */
public class LoginActivity extends Activity implements OnClickListener, LoginObserver {

	private Context context;
	private TextView tvForgotPassword;
	private Button btnLogin;
	private EditText etEmail;
	private EditText etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login2);

		context = LoginActivity.this;
		LoginLogic.getInstance().addObserver(this);

		initView();
	}

	void initView() {
		btnLogin = (Button) findViewById(R.id.btn_login_login);
		tvForgotPassword = (TextView) findViewById(R.id.tv_login_forgot);
		etEmail = (EditText) findViewById(R.id.et_login_email);
		etPassword = (EditText) findViewById(R.id.et_login_password);

		btnLogin.setOnClickListener(this);
		tvForgotPassword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_login_login:

			if (TextUtils.isEmpty(etEmail.getText().toString())) {
				Toast.makeText(context, getString(R.string.email_is_null), Toast.LENGTH_SHORT).show();
				return;
			}

			if (!RegexUtil.isEmail(etEmail.getText().toString())) {
				Toast.makeText(context, getString(R.string.email_format_wrong), Toast.LENGTH_SHORT).show();
				return;
			}

			if (TextUtils.isEmpty(etPassword.getText().toString())) {

				Toast.makeText(context, getString(R.string.password_is_null), Toast.LENGTH_SHORT).show();
				return;
			}

			LoginLogic.getInstance().onLogin(etEmail.getText().toString(), etPassword.getText().toString());

			HaloToast.showNewWaitDialog(context, false, "Login");

			break;

		case R.id.tv_login_forgot:
			intent = new Intent(context, ForgotPasswordActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LoginLogic.getInstance().removeObserver(this);
	}

	@Override
	public void onLoginSuccess(UserInfoEntity userInfoEntity) {

		PropertyApplication.mUserCache.setLogined(true);
		HaloToast.dismissWaitDialog();

		if (userInfoEntity.getData().getRelations().size() >= 2) {

			Intent intent = new Intent(context, SelectPropertyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("userInfoEntity", userInfoEntity);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();

		} else {

			PropertyApplication.mUserCache.setHouseId(userInfoEntity.getData().getCurHousesId());
			PropertyApplication.mUserCache.setCommunityId(userInfoEntity.getData().getCurCommunityId());

			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onLoginFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

}

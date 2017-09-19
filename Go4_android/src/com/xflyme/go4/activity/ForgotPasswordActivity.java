package com.xflyme.go4.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.R;
import com.xflyme.go4.util.RegexUtil;
import com.xflyme.go4.view.HaloToast;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class ForgotPasswordActivity extends BaseActivity implements
		OnClickListener, ForgotPasswordObserver {

	private EditText etEmail;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassowrd);

		context = ForgotPasswordActivity.this;
		etEmail = (EditText) findViewById(R.id.et_login_email);
		ForgotPasswordLogic.getInstance().addObserver(this);
	}

	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(etEmail.getText().toString())) {
			Toast.makeText(context, getString(R.string.email_is_null),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!RegexUtil.isEmail(etEmail.getText().toString())) {
			Toast.makeText(context, getString(R.string.email_format_wrong),
					Toast.LENGTH_SHORT).show();
			return;
		}
		ForgotPasswordLogic.getInstance().onFindPassword(etEmail.getText().toString());
		HaloToast.showNewWaitDialog(context, false, "...");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ForgotPasswordLogic.getInstance().removeObserver(this);
	}
	
	@Override
	public void onFindPassWordResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		finish();
	}

}

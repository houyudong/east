package com.xflyme.go4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.util.CLog;

/**
 * 
 * @Description:公共基类的实现
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月11日
 */
public abstract class BaseActivity extends Activity {
	protected static final String TAG = "BaseNetActivity";

	private PropertyApplication app = null;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (PropertyApplication) this.getApplication();
		app.addActivity(this);
		context = BaseActivity.this;
		CLog.d(TAG, getClass().getName());
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void toast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

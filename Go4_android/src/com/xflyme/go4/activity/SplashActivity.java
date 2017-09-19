package com.xflyme.go4.activity;

import com.xflyme.go4.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class SplashActivity extends BaseActivity {

	private ImageView ivSplash;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		context = SplashActivity.this;
		ivSplash = (ImageView) findViewById(R.id.image_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent();
				if (PropertyApplication.mUserCache.isLogined()) {
					intent.setClass(context, MainActivity.class);
				} else {
					if (PropertyApplication.mUserCache.isFirst()) {
						intent.setClass(context, GuideActivity.class);
					} else {
						intent.setClass(context, LoginActivity.class);
					}

				}
				startActivity(intent);
				finish();
			}
		}, 3000);
	}

}

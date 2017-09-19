package com.xflyme.go4.activity;

import java.util.List;

import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * 
 * @Description:检测系统版本是否升级逻辑
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月22日
 */
public class ForgotPasswordLogic extends BaseLogic<ForgotPasswordObserver> {

	public static ForgotPasswordLogic getInstance() {
		return Singlton.getInstance(ForgotPasswordLogic.class);
	}

	public void onFindPassword(final String email) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				Log.e("login", "login");
				result = ProtocolImpl.getInstance().onFindPassword(email);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onFindPasswordResult(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onFindPasswordResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					Log.e("token", result.getToken());
					onFindPasswordResult(result.getResult(),result.getMessage());
				}
			}

		};
	}

	private void onFindPasswordResult(int resultCode,String message) {
		List<ForgotPasswordObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onFindPassWordResult(resultCode, message);;
		}
	}


}
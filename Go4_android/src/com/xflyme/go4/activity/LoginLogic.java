package com.xflyme.go4.activity;

import java.util.List;

import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.google.gson.Gson;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;
import com.xflyme.go4.util.CLog;

/**
 * 
 * @Description:检测系统版本是否升级逻辑
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月22日
 */
public class LoginLogic extends BaseLogic<LoginObserver> {

	public static LoginLogic getInstance() {
		return Singlton.getInstance(LoginLogic.class);
	}

	public void onLogin(final String email, final String password) {

		new BackForeTask(true) {

			UserInfoEntity result = null;

			@Override
			public void onBack() {
				Log.e("login", "login");
				result = ProtocolImpl.getInstance().onLogin(email, password);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onLoginFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onLoginFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					Log.e("token", result.getToken());
					PropertyApplication.mUserCache.setUserInfo(new Gson()
							.toJson(result));
					onLoginSuccess(result);
				}
			}

		};
	}

	private void onLoginSuccess(UserInfoEntity userInfoEntity) {
		List<LoginObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onLoginSuccess(userInfoEntity);
		}
	}

	private void onLoginFail(int resultCode, String message) {
		List<LoginObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onLoginFail(resultCode, message);
		}
	}

}
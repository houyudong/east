package com.xflyme.go4.me;

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
public class ChangePasswrodLogic extends BaseLogic<ChangePasswordObserver> {

	public static ChangePasswrodLogic getInstance() {
		return Singlton.getInstance(ChangePasswrodLogic.class);
	}

	public void onChangePassword(final String oldpwd, final String newpwd) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				Log.e("login", "login");
				result = ProtocolImpl.getInstance().onChangePassword(oldpwd, newpwd);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onChangePasswordResult(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onChangePasswordResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onChangePasswordResult(result.getResult(), "");
				}
			}

		};
	}

	private void onChangePasswordResult(int resultCode, String message) {
		List<ChangePasswordObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onChangePasswrodResult(resultCode, message);
		}
	}

}
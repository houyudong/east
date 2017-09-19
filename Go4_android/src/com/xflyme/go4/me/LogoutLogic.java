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
public class LogoutLogic extends BaseLogic<LogoutObserver> {

	public static LogoutLogic getInstance() {
		return Singlton.getInstance(LogoutLogic.class);
	}

	public void onLogout(final String userid) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onLogout(userid);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onLogoutResult(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onLogoutResult(result.getResult(), result.getMessage());
				} else {
					onLogoutResult(result.getResult(), result.getMessage());
				}
			}

		};
	}

	private void onLogoutResult(int resultCode, String message) {
		List<LogoutObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onLogoutResult(resultCode, message);
		}
	}

}
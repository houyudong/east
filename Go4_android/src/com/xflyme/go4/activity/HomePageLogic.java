package com.xflyme.go4.activity;

import java.util.List;

import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.HomePageEntity;
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
public class HomePageLogic extends BaseLogic<HomePageObserver> {

	public static HomePageLogic getInstance() {
		return Singlton.getInstance(HomePageLogic.class);
	}

	public void onGetHomePage() {

		new BackForeTask(true) {

			HomePageEntity result = null;

			@Override
			public void onBack() {
				Log.e("login", "login");
				result = ProtocolImpl.getInstance().onGetHomePage();

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetHomePageFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetHomePageFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetHomePageSuccess(result);
				}
			}

		};
	}

	private void onGetHomePageSuccess(HomePageEntity homePageEntity) {
		List<HomePageObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetHomePageSuccess(homePageEntity);
		}
	}

	private void onGetHomePageFail(int resultCode, String message) {
		List<HomePageObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetHomePageFail(resultCode, message);
		}
	}

}
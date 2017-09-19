package com.xflyme.go4.bookfacility;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.SiteDetailEntity;
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
public class SiteDetailLogic extends BaseLogic<SiteDetailObserver> {

	public static SiteDetailLogic getInstance() {
		return Singlton.getInstance(SiteDetailLogic.class);
	}

	public void onGetSiteDetail(final String searchDate,final int siteId) {

		new BackForeTask(true) {

			SiteDetailEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetSiteDetail(searchDate, siteId);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetSiteDetailFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetSiteDetailFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetSiteDetailSuccess(result);
				}
			}

		};
	}

	private void onGetSiteDetailSuccess(SiteDetailEntity siteDetailEntity) {
		List<SiteDetailObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetSiteDetailSuccess(siteDetailEntity);
		}
	}

	private void onGetSiteDetailFail(int resultCode, String message) {
		List<SiteDetailObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetSiteDetailFail(resultCode, message);
		}
	}

	
}
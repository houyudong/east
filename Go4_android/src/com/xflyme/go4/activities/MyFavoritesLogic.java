package com.xflyme.go4.activities;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * <p>
 * <li>简述：<一句话介绍类的作用></li>
 * <li>详述：<详细介绍类的方法及作用></li>
 * </p>
 * 
 * @author yanxf
 * @since 1.0
 * @see
 */
public class MyFavoritesLogic extends BaseLogic<MyFavoritesObserver> {
	public static MyFavoritesLogic getInstance() {
		return Singlton.getInstance(MyFavoritesLogic.class);
	}

	public void onGetMyFavorites(final int pageNo, final int pageSize) {

		new BackForeTask(true) {

			ActivityResp result = null;

			@Override
			public void onBack() {
				Log.e("activityList", "activityList");
				result = ProtocolImpl.getInstance().onGetMyFavorites(pageNo, pageSize);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetMyFavoritesFail(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetMyFavoritesFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetMyFavoritesSuccess(result);
				}
			}

		};
	}

	public void onAddFavorite(final ArrayList<Integer> orgIds, final int type) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				Log.e("activityList", "activityList");
				result = ProtocolImpl.getInstance().onAddFavorite(orgIds, type);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onAddFavoriteResult(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onAddFavoriteResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onAddFavoriteResult(result.getResult(), "success");
				}
			}

		};
	}

	private void onGetMyFavoritesSuccess(ActivityResp activityResp) {
		List<MyFavoritesObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyFavoritesSuccess(activityResp);
		}
	}

	private void onGetMyFavoritesFail(int resultCode, String message) {
		List<MyFavoritesObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyFavoritesFail(resultCode, message);
		}
	}

	private void onAddFavoriteResult(int resultCode, String message) {
		List<MyFavoritesObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onAddFavoriteResult(resultCode, message);
		}
	}
}

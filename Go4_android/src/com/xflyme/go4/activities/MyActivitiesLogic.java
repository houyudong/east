package com.xflyme.go4.activities;

import java.util.List;
import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * <p>
 *  <li>简述：<一句话介绍类的作用></li>
 *  <li>详述：<详细介绍类的方法及作用></li>
 * </p>
 * @author yanxf
 * @since  1.0
 * @see 
 */
public class MyActivitiesLogic extends BaseLogic<MyActivitiesObserver>{
	public static MyActivitiesLogic getInstance() {
		return Singlton.getInstance(MyActivitiesLogic.class);
	} 
	
	public void onGetMyFavorites(final int pageNo,final int pageSize) {

		new BackForeTask(true) {
			
			ActivityResp result= null;

			@Override
			public void onBack() {
				Log.e("activityList", "activityList");
				result = ProtocolImpl.getInstance().onGetMyActivities(pageNo, pageSize);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetMyFavoritesFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
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
	
	private void onGetMyFavoritesSuccess(ActivityResp activityResp){
		List<MyActivitiesObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyFavoritesSuccess(activityResp);
		}
	}
	
	private void onGetMyFavoritesFail(int resultCode, String message){
		List<MyActivitiesObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyFavoritesFail(resultCode, message);
		}
	}
}

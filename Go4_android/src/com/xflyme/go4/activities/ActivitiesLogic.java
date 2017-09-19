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
public class ActivitiesLogic extends BaseLogic<ActivityListObserver>{
	public static ActivitiesLogic getInstance() {
		return Singlton.getInstance(ActivitiesLogic.class);
	} 
	
	public void onGetActivity(final int pageNo,final int pageSize,final int category,final int activityId) {

		new BackForeTask(true) {
			
			ActivityResp result= null;

			@Override
			public void onBack() {
				Log.e("activityList", "activityList");
				result = ProtocolImpl.getInstance().onGetActivity(pageNo, pageSize, category, activityId);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onActivityListFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onActivityListFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onActivityListSuccess(result);
				}
			}

		};
	}
	
	private void onActivityListSuccess(ActivityResp activityResp){
		List<ActivityListObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onActivityListSuccess(activityResp);
		}
	}
	
	private void onActivityListFail(int resultCode, String message){
		List<ActivityListObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onActivityListFail(resultCode, message);
		}
	}
}

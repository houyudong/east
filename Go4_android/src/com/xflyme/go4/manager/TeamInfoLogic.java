package com.xflyme.go4.manager;

import java.util.List;

import android.R.integer;
import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.google.gson.Gson;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.TeamInfoEntity;
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
public class TeamInfoLogic extends BaseLogic<TeamInfoObserver> {

	public static TeamInfoLogic getInstance() {
		return Singlton.getInstance(TeamInfoLogic.class);
	}

	public void onGetTeamInfo(final int pageNo,final int pageSize) {

		new BackForeTask(true) {

			TeamInfoEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetTeamInfo(pageNo, pageSize);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetTeamInfoFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetTeamInfoFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetTeamInfoSuccess(result);
				}
			}

		};
	}

	private void onGetTeamInfoSuccess(TeamInfoEntity teamInfoEntity) {
		List<TeamInfoObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetTeamInfoSuccess(teamInfoEntity);
		}
	}

	private void onGetTeamInfoFail(int resultCode, String message) {
		List<TeamInfoObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetTeamInfoFail(resultCode, message);
		}
	}

}
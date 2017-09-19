package com.xflyme.go4.me;

import java.util.List;

import com.google.gson.Gson;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;
import com.xflyme.go4.R;

import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

public class UserInfoLogic extends BaseLogic<UserInfoObserver> {
	public static UserInfoLogic getInstance() {
		return Singlton.getInstance(UserInfoLogic.class);
	}

	public void updateUserInfo(final String birth, final String mobile,
			final String email, final String name, final String[] image) {
		new BackForeTask(true) {
			UserInfoEntity result = null;

			@Override
			public void onFore() {
				if (result == null) {
					onUpdteUserInfoResult(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onUpdteUserInfoResult(result.getResult(),
							result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					Log.e("token", result.getToken());
					if (result.getData() != null) {
						PropertyApplication.mUserCache.setUserInfo(new Gson()
						.toJson(result));
					}
					onUpdteUserInfoResult(result.getResult(),
							result.getMessage());
				}
			}

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().updateUserInfo(birth,
						mobile, email, name, image);
			}
		};
	}

	private void onUpdteUserInfoResult(int errorCode, String msg) {
		List<UserInfoObserver> tmpList = getObservers();
		for (UserInfoObserver o : tmpList) {
			o.onUpdteUserInfoResult(errorCode, msg);
		}

	}

}

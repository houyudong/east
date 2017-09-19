package com.xflyme.go4.activities;

import java.util.List;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;
import com.xflyme.go4.R;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

public class AddActivityLogic extends BaseLogic<AddActivityObserver> {
	public static AddActivityLogic getInstance() {
		return Singlton.getInstance(AddActivityLogic.class);
	}

	public void onAddActivity(final int category,final String name,final String content, final String[] image) {
		new BackForeTask(true) {
			BaseEntity result = null;

			@Override
			public void onFore() {
				if (result == null) {
					onAddActivityResult(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onAddActivityResult(result.getResult(),
							result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onAddActivityResult(result.getResult(),
							result.getMessage());
				}
			}

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onAddActivity(category, name, content, image);
			}
		};
	}

	private void onAddActivityResult(int errorCode, String msg) {
		List<AddActivityObserver> tmpList = getObservers();
		for (AddActivityObserver o : tmpList) {
			o.onAddActivityResult(errorCode, msg);
		}

	}

}

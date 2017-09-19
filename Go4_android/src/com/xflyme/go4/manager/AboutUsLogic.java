package com.xflyme.go4.manager;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.AboutUsEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * @author apple
 *	关于我们逻辑
 */
public class AboutUsLogic extends BaseLogic<AboutUsObserver> {

	public static AboutUsLogic getInstance() {
		return Singlton.getInstance(AboutUsLogic.class);
	}

	public void onGetAboutUs(final int type) {

		new BackForeTask(true) {

			AboutUsEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetAboutUs(type);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetAboutUsFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetAboutUsFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetAboutUsSuccess(result);
				}
			}

		};
	}

	private void onGetAboutUsSuccess(AboutUsEntity aboutUsEntity) {
		List<AboutUsObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetAboutUsSuccess(aboutUsEntity);
		}
	}

	private void onGetAboutUsFail(int resultCode, String message) {
		List<AboutUsObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetAboutUsFail(resultCode, message);
		}
	}

}
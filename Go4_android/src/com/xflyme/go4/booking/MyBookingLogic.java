package com.xflyme.go4.booking;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.MyBookingEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * @author apple notice board
 */
public class MyBookingLogic extends BaseLogic<MyBookingObserver> {

	public static MyBookingLogic getInstance() {
		return Singlton.getInstance(MyBookingLogic.class);
	}

	public void onGetMyBooking(final int pageNo, final int pageSize, final int type) {

		new BackForeTask(true) {

			MyBookingEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetMyBooking(pageNo, pageSize, type);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetMyBookingFail(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetMyBookingFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetMyBookingSuccess(result);
				}
			}

		};
	}

	private void onGetMyBookingSuccess(MyBookingEntity bookingEntity) {
		List<MyBookingObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyBookingSuccess(bookingEntity);
		}
	}

	private void onGetMyBookingFail(int resultCode, String message) {
		List<MyBookingObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetMyBookingFail(resultCode, message);
		}
	}

}
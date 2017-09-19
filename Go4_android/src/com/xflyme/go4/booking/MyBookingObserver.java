package com.xflyme.go4.booking;

import com.xflyme.go4.entity.MyBookingEntity;

public interface MyBookingObserver {

	public void onGetMyBookingSuccess(MyBookingEntity bookingEntity);

	public void onGetMyBookingFail(int resultCode, String message);

}

package com.xflyme.go4.entity;

import java.util.ArrayList;

public class MyBookingEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyBookingData data;

	public MyBookingData getData() {
		return data;
	}

	public void setData(MyBookingData data) {
		this.data = data;
	}

	public class MyBookingData {
		private ArrayList<BookFacilityItem> schedulesList;

		public ArrayList<BookFacilityItem> getSchedulesList() {
			return schedulesList;
		}

		public void setSchedulesList(ArrayList<BookFacilityItem> schedulesList) {
			this.schedulesList = schedulesList;
		}
	}
}

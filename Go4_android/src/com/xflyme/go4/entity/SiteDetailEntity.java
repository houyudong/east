package com.xflyme.go4.entity;

import java.util.ArrayList;


public class SiteDetailEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SiteDetailData data;

	public SiteDetailData getData() {
		return data;
	}

	public void setData(SiteDetailData data) {
		this.data = data;
	}

	public class SiteDetailData {
		private SiteDetailDetail daydetail;

		public SiteDetailDetail getDaydetail() {
			return daydetail;
		}

		public void setDaydetail(SiteDetailDetail daydetail) {
			this.daydetail = daydetail;
		}
	}
	
	public class SiteDetailDetail{
		private ArrayList<SiteDetailDayItem> dayjson;
		private ArrayList<SiteDetailMouthItem> mouthjson;

		public ArrayList<SiteDetailDayItem> getDayjson() {
			return dayjson;
		}

		public void setDayjson(ArrayList<SiteDetailDayItem> dayjson) {
			this.dayjson = dayjson;
		}

		public ArrayList<SiteDetailMouthItem> getMouthjson() {
			return mouthjson;
		}

		public void setMouthjson(ArrayList<SiteDetailMouthItem> mouthjson) {
			this.mouthjson = mouthjson;
		}
	}

	public class SiteDetailMouthItem {
		private String date;
		private int state;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
	}

	public class SiteDetailDayItem {
		private String dayTimeDesc;
		private String dayTime;
		private int isOrder;
		private int isCancel;
		private boolean selected;
		private int dayType;
		
		public int getDayType() {
			return dayType;
		}

		public void setDayType(int dayType) {
			this.dayType = dayType;
		}

		public String getDayTimeDesc() {
			return dayTimeDesc;
		}

		public void setDayTimeDesc(String dayTimeDesc) {
			this.dayTimeDesc = dayTimeDesc;
		}

		public String getDayTime() {
			return dayTime;
		}

		public void setDayTime(String dayTime) {
			this.dayTime = dayTime;
		}

		public int getIsOrder() {
			return isOrder;
		}

		public void setIsOrder(int isOrder) {
			this.isOrder = isOrder;
		}

		public int getIsCancel() {
			return isCancel;
		}

		public void setIsCancel(int isCancel) {
			this.isCancel = isCancel;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
}

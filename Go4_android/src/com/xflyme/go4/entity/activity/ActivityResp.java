package com.xflyme.go4.entity.activity;

import java.util.List;
import java.util.Map;
import com.xflyme.go4.entity.BaseEntity;

public class ActivityResp extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActivityRespData data;
	
	public ActivityRespData getData() {
		return data;
	}

	public void setData(ActivityRespData data) {
		this.data = data;
	}

	public class ActivityRespData{
		private Map<?, ?> categoryMap;
		private BusActivity busActivity;
		private List<BusActivity> listBusActivity;

		public BusActivity getBusActivity() {
			return busActivity;
		}

		public void setBusActivity(BusActivity busActivity) {
			this.busActivity = busActivity;
		}

		public List<BusActivity> getListBusActivity() {
			return listBusActivity;
		}

		public void setListBusActivity(List<BusActivity> listBusActivity) {
			this.listBusActivity = listBusActivity;
		}

		public Map<?, ?> getCategoryMap() {
			return categoryMap;
		}

		public void setCategoryMap(Map<?, ?> categoryMap) {
			this.categoryMap = categoryMap;
		}	
	}
	
	

}

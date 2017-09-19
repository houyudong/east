package com.xflyme.go4.entity;

import java.util.ArrayList;


public class ServerBookFacilityEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BookFacilityData data;
	
	public BookFacilityData getData() {
		return data;
	}

	public void setData(BookFacilityData data) {
		this.data = data;
	}

	public class BookFacilityData{
		private ArrayList<BookFacilityItem> schSitesList;

		public ArrayList<BookFacilityItem> getSchSitesList() {
			return schSitesList;
		}

		public void setSchSitesList(ArrayList<BookFacilityItem> schSitesList) {
			this.schSitesList = schSitesList;
		}
		
	}
	

}

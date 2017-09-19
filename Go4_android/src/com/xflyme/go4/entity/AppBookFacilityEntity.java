package com.xflyme.go4.entity;

import java.io.Serializable;
import java.util.ArrayList;


public class AppBookFacilityEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private ArrayList<BookFacilityItem> items;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<BookFacilityItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<BookFacilityItem> items) {
		this.items = items;
	}

}

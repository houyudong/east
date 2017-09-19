package com.xflyme.go4.entity;

import java.util.ArrayList;

import com.xflyme.go4.entity.BaseEntity;

public class TeamInfoEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<TeamMemItem> data;

	public ArrayList<TeamMemItem> getData() {
		return data;
	}

	public void setData(ArrayList<TeamMemItem> data) {
		this.data = data;
	}

}

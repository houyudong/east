package com.xflyme.go4.entity;

import java.util.ArrayList;

public class HomePageEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<HomePageItem> data;

	public ArrayList<HomePageItem> getData() {
		return data;
	}

	public void setData(ArrayList<HomePageItem> data) {
		this.data = data;
	}

	public class HomePageItem {
		private int targetType;
		private String targetId;
		private String photo;
		private String title;

		public int getTargetType() {
			return targetType;
		}

		public void setTargetType(int targetType) {
			this.targetType = targetType;
		}

		public String getTargetId() {
			return targetId;
		}

		public void setTargetId(String targetId) {
			this.targetId = targetId;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}

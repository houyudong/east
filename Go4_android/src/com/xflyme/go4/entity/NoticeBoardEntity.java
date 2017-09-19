package com.xflyme.go4.entity;

import java.util.ArrayList;


public class NoticeBoardEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NoticeBoardData data;

	public NoticeBoardData getData() {
		return data;
	}

	public void setData(NoticeBoardData data) {
		this.data = data;
	}

	public class NoticeBoardData {
		private ArrayList<NoticeBoardItem> listBusNotice;

		public ArrayList<NoticeBoardItem> getListBusNotice() {
			return listBusNotice;
		}

		public void setListBusNotice(ArrayList<NoticeBoardItem> listBusNotice) {
			this.listBusNotice = listBusNotice;
		}
	}

}

package com.xflyme.go4.entity;

import java.util.ArrayList;

public class NoticeBoardDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NoticeBoardDetailData data;

	public NoticeBoardDetailData getData() {
		return data;
	}

	public void setData(NoticeBoardDetailData data) {
		this.data = data;
	}

	public class NoticeBoardDetailData {
		private NoticeBoardItem busNotice;
		private ArrayList<NoticeBoardItem> listBusNotice;

		public NoticeBoardItem getBusNotice() {
			return busNotice;
		}

		public void setBusNotice(NoticeBoardItem busNotice) {
			this.busNotice = busNotice;
		}

		public ArrayList<NoticeBoardItem> getListBusNotice() {
			return listBusNotice;
		}

		public void setListBusNotice(ArrayList<NoticeBoardItem> listBusNotice) {
			this.listBusNotice = listBusNotice;
		}
	}
}

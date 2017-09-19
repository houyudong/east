package com.xflyme.go4.entity;

import java.util.ArrayList;

public class ReplyEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReplyData data;

	public ReplyData getData() {
		return data;
	}

	public void setData(ReplyData data) {
		this.data = data;
	}

	public class ReplyData {
		private ArrayList<ReplayItem> lsitActivityReply;

		public ArrayList<ReplayItem> getLsitActivityReply() {
			return lsitActivityReply;
		}

		public void setLsitActivityReply(ArrayList<ReplayItem> lsitActivityReply) {
			this.lsitActivityReply = lsitActivityReply;
		}
	}

}

package com.xflyme.go4.entity;

import java.util.ArrayList;

public class FeedBackEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FeedBackData data;

	public FeedBackData getData() {
		return data;
	}

	public void setData(FeedBackData data) {
		this.data = data;
	}

	public class FeedBackData {
		private int feedbackId;
		private int readState;
		private String modifyTime;
		private ArrayList<FeedBackItem> replyList;

		public int getFeedbackId() {
			return feedbackId;
		}

		public void setFeedbackId(int feedbackId) {
			this.feedbackId = feedbackId;
		}

		public int getReadState() {
			return readState;
		}

		public void setReadState(int readState) {
			this.readState = readState;
		}

		public String getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(String modifyTime) {
			this.modifyTime = modifyTime;
		}

		public ArrayList<FeedBackItem> getReplyList() {
			return replyList;
		}

		public void setReplyList(ArrayList<FeedBackItem> replyList) {
			this.replyList = replyList;
		}

	}

}

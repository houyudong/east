package com.xflyme.go4.entity;

import java.io.Serializable;
import java.util.ArrayList;


public class VoteEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int isVote;
	private VoteData data;
	
	public int getIsVote() {
		return isVote;
	}

	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}

	public VoteData getData() {
		return data;
	}

	public void setData(VoteData data) {
		this.data = data;
	}

	public class VoteData{
		private ArrayList<VoteItem> hasNotVote;
		private ArrayList<VoteDataItem> listHasVote;
		public ArrayList<VoteItem> getHasNotVote() {
			return hasNotVote;
		}
		public void setHasNotVote(ArrayList<VoteItem> hasNotVote) {
			this.hasNotVote = hasNotVote;
		}
		public ArrayList<VoteDataItem> getListHasVote() {
			return listHasVote;
		}
		public void setListHasVote(ArrayList<VoteDataItem> listHasVote) {
			this.listHasVote = listHasVote;
		}
	}
	
	public class VoteDataItem{
		private int choiceId;
		private String title;
		private String choice;
		private int count;
		public int getChoiceId() {
			return choiceId;
		}
		public void setChoiceId(int choiceId) {
			this.choiceId = choiceId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getChoice() {
			return choice;
		}
		public void setChoice(String choice) {
			this.choice = choice;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		
		
	}
}

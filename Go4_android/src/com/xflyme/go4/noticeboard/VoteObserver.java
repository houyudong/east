package com.xflyme.go4.noticeboard;


import com.xflyme.go4.entity.VoteEntity;

public interface VoteObserver {	
	
	
	public void onGetVoteSuccess(VoteEntity voteEntity);
	
	public void onGetVoteFail(int resultCode,String message);

	public void onVoteResult(int resultCode,String message);
	
}

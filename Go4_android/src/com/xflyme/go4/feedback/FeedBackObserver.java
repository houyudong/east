package com.xflyme.go4.feedback;


import com.xflyme.go4.entity.FeedBackEntity;

public interface FeedBackObserver {	
	
	
	public void onGetFeedBackSuccess(FeedBackEntity feedBackEntity);
	
	public void onGetFeedBackFail(int resultCode,String message);
	
	public void onSaveFeedBackResult(int resultCode,String message);
	
}

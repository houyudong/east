package com.xflyme.go4.noticeboard;


import com.xflyme.go4.entity.NoticeBoardDetailEntity;
import com.xflyme.go4.entity.NoticeBoardEntity;

public interface NoticeBoardObserver {	
	
	
	public void onGetNoticeBoardSuccess(NoticeBoardEntity noticeBoardEntity);
	
	public void onGetNoticeBoardFail(int resultCode,String message);
	
	public void onGetNoticeBoardDetailSuccess(NoticeBoardDetailEntity noticeBoardDetailEntity);
	
	public void onGetNoticeBoardDetailFail(int resultCode,String message);
	
}

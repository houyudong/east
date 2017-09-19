package com.xflyme.go4.manager;


import com.xflyme.go4.entity.AboutUsEntity;

public interface AboutUsObserver {	
	
	
	public void onGetAboutUsSuccess(AboutUsEntity aboutUsEntity);
	
	public void onGetAboutUsFail(int resultCode,String message);
	
}

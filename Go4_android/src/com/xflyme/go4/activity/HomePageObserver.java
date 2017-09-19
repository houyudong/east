package com.xflyme.go4.activity;


import com.xflyme.go4.entity.HomePageEntity;

public interface HomePageObserver {	
	
	public void onGetHomePageSuccess(HomePageEntity homePageEntity);
	
	public void onGetHomePageFail(int resultCode,String message);
	
}

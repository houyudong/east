package com.xflyme.go4.bookfacility;


import com.xflyme.go4.entity.SiteDetailEntity;

public interface SiteDetailObserver {	
	
	
	public void onGetSiteDetailSuccess(SiteDetailEntity siteDetailEntity);
	
	public void onGetSiteDetailFail(int resultCode,String message);
	
}

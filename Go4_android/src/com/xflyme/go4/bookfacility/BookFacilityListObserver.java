package com.xflyme.go4.bookfacility;


import com.xflyme.go4.entity.ServerBookFacilityEntity;

public interface BookFacilityListObserver {	
	
	
	public void onGetBookFacilityListSuccess(ServerBookFacilityEntity bookFacilityEntity);
	
	public void onGetBookFacilityListFail(int resultCode,String message);
	
}

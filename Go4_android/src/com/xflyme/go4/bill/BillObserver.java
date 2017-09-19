package com.xflyme.go4.bill;


import com.xflyme.go4.entity.BillEntity;

public interface BillObserver {	
	
	
	public void onGetBillSuccess(BillEntity billEntity);
	
	public void onGetBillFail(int resultCode,String message);
	
}

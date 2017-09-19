package com.xflyme.go4.activity;


import com.xflyme.go4.entity.UserInfoEntity;

public interface LoginObserver {	
	
	public void onLoginSuccess(UserInfoEntity userInfoEntity);
	
	public void onLoginFail(int resultCode,String message);
	
}

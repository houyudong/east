package com.xflyme.go4.manager;


import com.xflyme.go4.entity.TeamInfoEntity;

public interface TeamInfoObserver {	
	
	
	public void onGetTeamInfoSuccess(TeamInfoEntity teamInfoEntity);
	
	public void onGetTeamInfoFail(int resultCode,String message);
	
}

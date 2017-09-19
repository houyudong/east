package com.xflyme.go4.noticeboard;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.VoteEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * @author apple
 *	notice board
 */
public class VoteLogic extends BaseLogic<VoteObserver> {

	public static VoteLogic getInstance() {
		return Singlton.getInstance(VoteLogic.class);
	}

	public void onGetVote(final int noticeId) {

		new BackForeTask(true) {

			VoteEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetVote(noticeId);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetVoteFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetVoteFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetVoteSuccess(result);
				}
			}

		};
	}
	
	public void onVote(final int noticeId,final int choiceId) {
		
		new BackForeTask(true) {
			
			BaseEntity result = null;
			
			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onVote(noticeId, choiceId);
				
			}
			
			@Override
			public void onFore() {
				if (result == null) {
					onVote(
							-1,
							PropertyApplication
							.getContext()
							.getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onVote(result.getResult(), "Vote Fail !");
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onVote(0,"Vote Success!");
				}
			}
			
		};
	}
	
	private void onGetVoteSuccess(VoteEntity voteEntity){
		List<VoteObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetVoteSuccess(voteEntity);
		}
	}
	
	private void onVote(int resultCode,String message){
		List<VoteObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onVoteResult(resultCode,message);
		}
	}
	
	private void onGetVoteFail(int resultCode, String message) {
		List<VoteObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetVoteFail(resultCode, message);
		}
	}

}
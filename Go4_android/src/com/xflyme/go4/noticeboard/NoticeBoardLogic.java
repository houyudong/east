package com.xflyme.go4.noticeboard;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.NoticeBoardDetailEntity;
import com.xflyme.go4.entity.NoticeBoardEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * @author apple
 *	notice board
 */
public class NoticeBoardLogic extends BaseLogic<NoticeBoardObserver> {

	public static NoticeBoardLogic getInstance() {
		return Singlton.getInstance(NoticeBoardLogic.class);
	}

	public void onGetNoticeBoardList(final int pageNo,final int pageSize,final int category,final int activityId) {

		new BackForeTask(true) {

			NoticeBoardEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetNoticeBoardList(pageNo, pageSize, category, activityId);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetNoticeBoardListFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetNoticeBoardListFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetNoticeBoardListSuccess(result);
				}
			}

		};
	}

	public void onGetNoticeBoardDetail(final int pageNo,final int pageSize,final int category,final int activityId) {
		
		new BackForeTask(true) {
			
			NoticeBoardDetailEntity result = null;
			
			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetNoticeBoardDetail(pageNo, pageSize, category, activityId);
				
			}
			
			@Override
			public void onFore() {
				if (result == null) {
					onGetNoticeBoardDetailFail(
							-1,
							PropertyApplication
							.getContext()
							.getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetNoticeBoardDetailFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetNoticeBoardDetailSuccess(result);
				}
			}
			
		};
	}
	
	private void onGetNoticeBoardListSuccess(NoticeBoardEntity noticeBoardEntity){
		List<NoticeBoardObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetNoticeBoardSuccess(noticeBoardEntity);
		}
	}
	
	private void onGetNoticeBoardListFail(int resultCode, String message) {
		List<NoticeBoardObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetNoticeBoardFail(resultCode, message);
		}
	}
	
	private void onGetNoticeBoardDetailSuccess(NoticeBoardDetailEntity detailEntity){
		List<NoticeBoardObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetNoticeBoardDetailSuccess(detailEntity);
		}
	}
	
	private void onGetNoticeBoardDetailFail(int resultCode, String message) {
		List<NoticeBoardObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetNoticeBoardDetailFail(resultCode, message);
		}
	}

}
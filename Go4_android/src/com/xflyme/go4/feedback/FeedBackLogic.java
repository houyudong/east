package com.xflyme.go4.feedback;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.FeedBackEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * 
 * @Description:检测系统版本是否升级逻辑
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月22日
 */
public class FeedBackLogic extends BaseLogic<FeedBackObserver> {

	public static FeedBackLogic getInstance() {
		return Singlton.getInstance(FeedBackLogic.class);
	}

	public void onGetFeedBack(final int pageNo,final int pageSize,final int feedbackId) {

		new BackForeTask(true) {

			FeedBackEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetFeedBack(pageNo, pageSize, feedbackId);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetFeedbackFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetFeedbackFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetFeedBackSuccess(result);
				}
			}

		};
	}

	public void onSaveFeedBack(final String content,final int feedbackId) {
		
		new BackForeTask(true) {
			
			BaseEntity result = null;
			
			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onSaveFeedBack(content, feedbackId);
				
			}
			
			@Override
			public void onFore() {
				if (result == null) {
					onSaveFeedBackResult(
							-1,
							PropertyApplication
							.getContext()
							.getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onSaveFeedBackResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onSaveFeedBackResult(result.getResult(),result.getMessage());
				}
			}
			
		};
	}
	
	private void onGetFeedBackSuccess(FeedBackEntity feedBackEntity) {
		List<FeedBackObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetFeedBackSuccess(feedBackEntity);
		}
	}

	private void onGetFeedbackFail(int resultCode, String message) {
		List<FeedBackObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetFeedBackFail(resultCode, message);
		}
	}

	private void onSaveFeedBackResult(int resultCode, String message) {
		List<FeedBackObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onSaveFeedBackResult(resultCode, message);
		}
	}
	
}
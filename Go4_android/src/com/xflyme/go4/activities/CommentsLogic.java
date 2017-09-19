package com.xflyme.go4.activities;

import java.util.List;
import android.util.Log;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.ReplyEntity;
import com.xflyme.go4.netwrok.ProtocolImpl;

/**
 * <p>
 * <li>简述：<一句话介绍类的作用></li>
 * <li>详述：<详细介绍类的方法及作用></li>
 * </p>
 * 
 * @author yanxf
 * @since 1.0
 * @see
 */
public class CommentsLogic extends BaseLogic<CommentsObserver> {
	public static CommentsLogic getInstance() {
		return Singlton.getInstance(CommentsLogic.class);
	}

	public void onGetReplay(final int activityId, final int pageNo, final int pageSize) {

		new BackForeTask(true) {

			ReplyEntity result = null;

			@Override
			public void onBack() {
				Log.e("activityList", "activityList");
				result = ProtocolImpl.getInstance().onGetReplay(activityId, pageNo, pageSize);
			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetReplyFail(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetReplyFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetReplySuccess(result);
				}
			}

		};
	}

	public void onReply(final String content, final int activityId) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onReply(content, activityId);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onReplyResult(-1, PropertyApplication.getContext().getResources()
							.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onReplyResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onReplyResult(result.getResult(), result.getMessage());
				}
			}

		};
	}

	private void onGetReplySuccess(ReplyEntity replyEntity) {
		List<CommentsObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetReplySuccess(replyEntity);
		}
	}

	private void onGetReplyFail(int resultCode, String message) {
		List<CommentsObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetReplyFail(resultCode, message);
		}
	}

	private void onReplyResult(int resultCode, String message) {
		List<CommentsObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onReplyResult(resultCode, message);
		}
	}
}

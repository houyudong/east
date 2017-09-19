package com.xflyme.go4.activities;

import com.xflyme.go4.entity.ReplyEntity;

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
public interface CommentsObserver {
	public void onGetReplySuccess(ReplyEntity replyEntity);

	public void onGetReplyFail(int resultCode, String message);

	public void onReplyResult(int resultCode, String message);
}

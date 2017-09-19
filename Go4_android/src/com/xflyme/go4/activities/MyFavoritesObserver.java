package com.xflyme.go4.activities;

import com.xflyme.go4.entity.activity.ActivityResp;

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
public interface MyFavoritesObserver {
	public void onGetMyFavoritesSuccess(ActivityResp activityResp);

	public void onGetMyFavoritesFail(int resultCode, String message);

	public void onAddFavoriteResult(int resultCode, String message);
}

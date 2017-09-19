package com.xflyme.go4.bookfacility;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.ServerBookFacilityEntity;
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
public class BookFacilityListLogic extends BaseLogic<BookFacilityListObserver> {

	public static BookFacilityListLogic getInstance() {
		return Singlton.getInstance(BookFacilityListLogic.class);
	}

	public void onGetBookFacilityList(final int pageNo,final int pageSize) {

		new BackForeTask(true) {

			ServerBookFacilityEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetBookFacilityList(pageNo, pageSize);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetBookFacilityListFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetBookFacilityListFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetBookFacilityListSuccess(result);
				}
			}

		};
	}

	private void onGetBookFacilityListSuccess(ServerBookFacilityEntity serverBookFacilityEntity) {
		List<BookFacilityListObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetBookFacilityListSuccess(serverBookFacilityEntity);
		}
	}

	private void onGetBookFacilityListFail(int resultCode, String message) {
		List<BookFacilityListObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetBookFacilityListFail(resultCode, message);
		}
	}

	
}
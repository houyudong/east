package com.xflyme.go4.bookfacility;

import java.util.List;

import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.OrderDetailItem;
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
public class OrderLogic extends BaseLogic<Orderbserver> {

	public static OrderLogic getInstance() {
		return Singlton.getInstance(OrderLogic.class);
	}

	public void onOrder(final OrderDetailItem item) {

		new BackForeTask(true) {

			BaseEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onOrder(item);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onOrderResult(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onOrderResult(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onOrderResult(result.getResult(), result.getMessage());
				}
			}

		};
	}

	private void onOrderResult(int resultCode, String message) {
		List<Orderbserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onOrderResult(resultCode, message);
		}
	}

	
}
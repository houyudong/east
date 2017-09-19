package com.xflyme.go4.bill;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.xutil.Singlton;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.controller.BaseLogic;
import com.xflyme.go4.entity.BillEntity;
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
public class BillLogic extends BaseLogic<BillObserver> {

	public static BillLogic getInstance() {
		return Singlton.getInstance(BillLogic.class);
	}

	public void onGetBill(final int pageNo,final int pageSize,final int type) {

		new BackForeTask(true) {

			BillEntity result = null;

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().onGetBill(pageNo, pageSize, type);

			}

			@Override
			public void onFore() {
				if (result == null) {
					onGetBillFail(
							-1,
							PropertyApplication
									.getContext()
									.getResources()
									.getString(R.string.str_network_timeout_msg));
				} else if (result.getResult() != 0) {
					onGetBillFail(result.getResult(), result.getMessage());
				} else {
					PropertyApplication.mUserCache.setToken(result.getToken());
					onGetBillSuccess(result);
				}
			}

		};
	}

	private void onGetBillSuccess(BillEntity billEntity) {
		List<BillObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetBillSuccess(billEntity);
		}
	}

	private void onGetBillFail(int resultCode, String message) {
		List<BillObserver> observers = getObservers();
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).onGetBillFail(resultCode, message);
		}
	}

}
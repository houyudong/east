package com.xflyme.go4.controller;

import java.util.List;

import android.content.Context;
import android.xutil.task.BackForeTask;

import com.xflyme.go4.controller.observer.CheckVersionObserver;
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
public class CheckVersionLogic extends BaseLogic<CheckVersionObserver>{


	/*private CheckUpdataEntity check = null;//用来判断1.5秒内是否请求成功，成功则不弹出等待框，不成功则弹出等待框
	public CheckVersionLogic() {
	}

	public CheckVersionLogic(Context context) {
		
	}
	
	*//** ===      判断是否三秒之内获取检测版本是否成功                === *//*
	public CheckUpdataEntity isGetSuccess(){
		return check;
	} 

	*//**
	 * 判断检测更新请求是否成功
	 * 
	 * @param vercode
	 *//*
	public void CheckNewVersion(final String vercode) {
		new BackForeTask(true) {
			CheckUpdataEntity result = null;
			@Override
			public void onFore() {
				
				check = result;

				if (result == null) {
					fireCheckVersionFail(-1, null);
				} else if (result.getRetcode() != 0) {
					fireCheckVersionFail(result.getRetcode(),
							result.getMessage());
				} else {
					fireCheckVersionSucc(result);
				}
			}

			@Override
			public void onBack() {
				result = ProtocolImpl.getInstance().checkVersion(vercode);

			}
		};

	}
	*//**
	 * 版本更新请求失败
	 * 
	 * @param errorCode
	 * @param msg
	 *//*
	private void fireCheckVersionFail(int errorCode, String msg) {
		List<CheckVersionObserver> tmpList = getObservers();
		for (CheckVersionObserver observer : tmpList) {
			observer.onCheckVersionFail(errorCode, msg);
		}
	}

	*//**
	 * 版本更新请求成功
	 * 
	 * @param newEntity
	 *//*
	private void fireCheckVersionSucc(CheckUpdataEntity newEntity) {
		List<CheckVersionObserver> tmpList = getObservers();
		for (CheckVersionObserver observer : tmpList) {
			observer.onCheckVersionSucc(newEntity);
		}
	}*/
}

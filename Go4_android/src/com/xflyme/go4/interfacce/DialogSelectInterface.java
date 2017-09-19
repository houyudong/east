package com.xflyme.go4.interfacce;

import android.app.AlertDialog;
/**
 * 带有单选按钮的弹窗口接口
 * 
 * @Description:
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:
 */
public interface DialogSelectInterface {
	public void onFirstRadioListener(AlertDialog dialog);
	public void onSecondRadioListener(AlertDialog dialog);
	public void onOkListener(AlertDialog dialog);
	public void onCancleListener(AlertDialog dialog);
}

package com.xflyme.go4.view;

import com.xflyme.go4.view.NumberPicker.OnPickListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.xflyme.go4.R;

/**
 * 
 * @Description:自定义封装弹窗，吐司提示类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class HaloToast {

	private static CustomProgressDialog progressDialog;

	public final static void show(Context c, String s) {
		show(c, s, Toast.LENGTH_SHORT);
	}

	public final static void show(Context c, String s, int duration) {
		View layout = getToastView(c);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setVisibility(View.VISIBLE);
		text.setText(s);
		showToast(layout, duration);
	}

	@SuppressLint("InflateParams")
	private static View getToastView(Context c) {
		return LayoutInflater.from(c).inflate(R.layout.toast, null);
	}

	private static void showToast(View view, int duration) {
		Toast toast = new Toast(view.getContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}

	public static void showToast(View view, int duration, int gravity,
			int yOffset) {
		Toast toast = new Toast(view.getContext());
		toast.setGravity(Gravity.BOTTOM, 0, yOffset);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}

	public static void dismissWaitDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		progressDialog = null;
	}

	public static void showNewWaitDialog(Context context, boolean is_cancel,
			String content) {
		showProgressDialog(context, content, is_cancel, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

			}

		});
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param cotent
	 *            内容信息
	 * @param is_cancel
	 *            是否可以取消
	 * @param listener
	 *            按键监听器
	 */
	public static void showProgressDialog(Context context, String cotent,
			boolean is_cancel, OnCancelListener listener) {
		progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setMessage(cotent);
		progressDialog.setCancelable(is_cancel);
		progressDialog.setCanceledOnTouchOutside(false);
		if (listener != null) {
			progressDialog.setOnCancelListener(listener);
		}
		progressDialog.show();

	}

	/**
	 * @Description: 滑动选择器
	 * @param arrs
	 * @param height
	 * @param listener
	 * @param title
	 * @author:刘祥飞
	 */
	public static void showPicker(Context context, String[] arrs, int height,
			OnPickListener listener, String title) {
		NumberPicker picker = new NumberPicker(context, arrs, listener);
		picker.setTitle(title);
		WheelView wheelView = picker.getWheelView();

		wheelView.setVisibleItems(5);
		wheelView.setCyclic(false);
		wheelView.setCurrentItem(height);
		picker.show();
	}

}

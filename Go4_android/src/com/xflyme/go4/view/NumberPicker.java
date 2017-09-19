package com.xflyme.go4.view;

import com.xflyme.go4.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * 
 * @Description:数字滚动器
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class NumberPicker implements OnClickListener {

	private Context mContext;
	private String[] items;

	private PopupWindow mPopupWindow;
	private Button cancelBtn;
	private LinearLayout layout;
	private Button doneBtn;
	private TextView titleTV;
	private WheelView wheelView;

	private OnPickListener mOnPickListener;

	public NumberPicker(Context mContext, String[] items,
			OnPickListener pickListener) {
		this.mContext = mContext;
		this.items = items;
		this.mOnPickListener = pickListener;
		initPopupWindow();
	}

	@SuppressLint("InflateParams")
	private void initPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		layout = (LinearLayout) inflater.inflate(R.layout.numberpicker, null);
		doneBtn = (Button) layout.findViewById(R.id.numberpicker_done);
		cancelBtn = (Button) layout.findViewById(R.id.numberpicker_cancle);
		titleTV = (TextView) layout.findViewById(R.id.numberpicker_title);
		wheelView = (WheelView) layout
				.findViewById(R.id.numberpicker_wheelview);
		PickerWheelAdapter mAdapter = new PickerWheelAdapter(items);
		wheelView.setAdapter(mAdapter);

		mPopupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);

		setListener();

		// AnimationSet set = new AnimationSet(true);
		// Animation animation;
		// animation = new TranslateAnimation(
		// Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
		// Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f
		// );
		// animation.setDuration(250);
		/*
		 * set.addAnimation(animation); LayoutAnimationController controller =
		 * new LayoutAnimationController(set, 0.2f);
		 * layout.setLayoutAnimation(controller);
		 */
		// layout.setAnimation(animation);
		mPopupWindow.setAnimationStyle(R.style.picker_anim_style);
	}

	public WheelView getWheelView() {
		return this.wheelView;
	}

	public void setTitle(String title) {
		titleTV.setText(title);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.numberpicker_cancle:
			mOnPickListener.onCancle();
			break;
		case R.id.numberpicker_done:
			mOnPickListener.onDone(wheelView.getCurrentItem());
			break;
		default:
			break;
		}
		this.dismiss();
	}

	private void setListener() {
		cancelBtn.setOnClickListener(this);
		doneBtn.setOnClickListener(this);
	}

	public void show() {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
				.getAttributes();
		lp.alpha = 0.5f; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
		// mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.welcome));
		layout.setFocusable(true);// 设置该view能监听事件
		layout.setFocusableInTouchMode(true);// 设置该view能监听事件
		layout.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && mPopupWindow != null) {
					dismiss();
				}
				return true;
			}
		});
		mPopupWindow.update();
		mPopupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
	}

	public void dismiss() {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
				.getAttributes();
		lp.alpha = 1.0f; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
		mOnPickListener.onCancle();
		mPopupWindow.dismiss();
		mPopupWindow = null;
	}

	/**
	 * 
	 * @Description:WheelView适配器
	 * @author:
	 * @see:
	 * @since:
	 * @copyright © ciyun.cn
	 * @Date:2014年8月12日
	 */
	private class PickerWheelAdapter implements WheelView.WheelAdapter {
		private String texts[];

		public PickerWheelAdapter(String items[]) {
			this.texts = items;
		}

		public String getItem(int index) {
			if (index >= 0 && index < texts.length) {
				return texts[index].toString();
			}
			return null;
		}

		public int getItemsCount() {
			return texts.length;
		}

		public int getMaximumLength() {
			return -1;
		}

	}

	/**
	 * 
	 * @Description:Pick监听器
	 * @author:
	 * @see:
	 * @since:
	 * @copyright © ciyun.cn
	 * @Date:2014年8月12日
	 */
	public interface OnPickListener {
		public void onDone(int index);

		public void onCancle();
	}
}

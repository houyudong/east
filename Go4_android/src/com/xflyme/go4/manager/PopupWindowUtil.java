package com.xflyme.go4.manager;

import java.util.List;

import com.xflyme.go4.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class PopupWindowUtil<T> implements OnClickListener {
	PopupWindow popupWindow;
	private Context context;
	private OnPopupClickListener onPopupClickListener;
	int width = 0;

	public PopupWindowUtil(Context context,
			OnPopupClickListener onPopupClickListener) {
		this.context = context;
		this.onPopupClickListener = onPopupClickListener;
	}

	public void showActionWindow(View parent, List<ContactItem> tabs) {
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		View view = getView(context, tabs);
		popupWindow = new PopupWindow(view, popWidth, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的最右端

		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;

		int xPos = (int) (screenWidth - popupWindow.getWidth() - context
				.getResources().getDimension(R.dimen.popupWindow_margin));
		// popupWindow.showAsDropDown(parent, -10,0);
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, xPos,
				location[1] + parent.getHeight() - 20);
	}

	private View getView(Context context, List<ContactItem> tabs) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.background_popup);
		for (int i = 0; i < tabs.size(); i++) {
			if (i != tabs.size() - 1) {
				String name = "";
				if (tabs.get(i) instanceof ContactItem) {
					name = ((List<ContactItem>) tabs).get(i).getTypename();
				}
				Button btn = getButton(context, name, i);
				ImageView img = getImageView(context);
				layout.addView(btn);
				layout.addView(img);
			} else {
				String name = "";
				if (tabs.get(i) instanceof ContactItem) {
					name = ((List<ContactItem>) tabs).get(i).getTypename();
				}
				Button btn = getButton(context, name, i);
				layout.addView(btn);
			}
		}

		return layout;
	}

	private Button getButton(Context context, String text, int i) {
		Button btn = new Button(context);
		btn.setText(text);
		btn.setTextColor(context.getResources().getColor(R.color.white));
		btn.setTextSize(14f);
		btn.setTag(i);
		btn.setPadding(20, 25, 20, 25);
		btn.setBackgroundColor(Color.TRANSPARENT);
		ColorStateList csl = (ColorStateList) context.getResources()
				.getColorStateList(R.color.popup_text_selector);

		btn.setTextColor(csl);
		btn.setOnClickListener(this);
		return btn;
	}

	private static ImageView getImageView(Context context) {
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.color.popup_line);
		img.setPadding(20, 0, 20, 0);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));
		return img;
	}

	@Override
	public void onClick(View v) {

		onPopupClickListener.onClick(v, popupWindow);
	}

}

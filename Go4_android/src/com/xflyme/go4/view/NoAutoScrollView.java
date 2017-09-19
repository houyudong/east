package com.xflyme.go4.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 
 * @Description:禁止子布局变动时ScrollView自动定位的ScrollView
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-24
 */
public class NoAutoScrollView extends ScrollView{

	public NoAutoScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NoAutoScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoAutoScrollView(Context context) {
		super(context);
	}

	@Override
	protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
		return 0;
	}
}

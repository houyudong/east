package com.xflyme.go4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class NoSlideListView extends ListView {

	public NoSlideListView(Context context) {
		super(context);
	}

    public NoSlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public NoSlideListView(Context context, AttributeSet attrs, int  defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	if (ev.getAction() == MotionEvent.ACTION_MOVE) {
    		return true;  
    	}
    	return super.dispatchTouchEvent(ev);
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}

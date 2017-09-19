package com.xflyme.go4.view;

import com.xflyme.go4.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleView extends LinearLayout implements Checkable {

	private TextView mText;
	private CheckBox mCheckBox;

	public SingleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public SingleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SingleView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.item_vote, this, true);
		mText = (TextView) v.findViewById(R.id.item_vote);
		mCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
	}

	@Override
	public void setChecked(boolean checked) {
		mCheckBox.setChecked(checked);

	}

	@Override
	public boolean isChecked() {
		return mCheckBox.isChecked();
	}

	@Override
	public void toggle() {
		mCheckBox.toggle();
	}

	public void setTitle(String text) {
		mText.setText(text);
	}
}

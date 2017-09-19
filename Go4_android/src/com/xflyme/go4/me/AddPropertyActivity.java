package com.xflyme.go4.me;

import com.xflyme.go4.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Add property
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class AddPropertyActivity extends Activity implements OnClickListener {

	private Context context;

	private RelativeLayout rlLeft;
	private TextView titleCenter;
	private ImageView ivLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_property);

		context = AddPropertyActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		titleCenter = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);

		ivLeft = (ImageView) findViewById(R.id.iv_title_left);
		ivLeft.setOnClickListener(this);

		titleCenter.setText(getString(R.string.me_property));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;

		case R.id.iv_property_property_select:
			Toast.makeText(context, "iv_property_property_select", Toast.LENGTH_LONG).show();
			break;

		case R.id.btn_property_add:
			Toast.makeText(context, "add", Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
	}
}

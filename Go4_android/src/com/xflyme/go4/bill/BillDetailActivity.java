package com.xflyme.go4.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.R;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class BillDetailActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private ImageView ivDetail;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billdetail);

		context = BillDetailActivity.this;
		
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.bill_detail_itle).toUpperCase());
		
		ivDetail = (ImageView) findViewById(R.id.iv_bill_detail);
		
		ImageLoader.getInstance().displayImage(url, ivDetail);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		default:
			break;
		}
	}

}

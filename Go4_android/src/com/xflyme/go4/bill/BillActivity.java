package com.xflyme.go4.bill;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.adapter.PropertyFragmentPagerAdapter;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class BillActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private Button btnRecent;
	private Button btnHistory;

	private ViewPager vpBill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_managerment);

		context = BillActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.bill_itle).toUpperCase());

		btnRecent = (Button) findViewById(R.id.switch_button1);
		btnHistory = (Button) findViewById(R.id.switch_button2);

		btnRecent.setSelected(true);
		btnRecent.setText(getString(R.string.bill_recent).toUpperCase());
		btnHistory.setText(getString(R.string.bill_history).toUpperCase());

		btnRecent.setOnClickListener(this);
		btnHistory.setOnClickListener(this);

		vpBill = (ViewPager) findViewById(R.id.vp_managerment);
		PropertyFragmentPagerAdapter pagerAdapter = new PropertyFragmentPagerAdapter(
				getSupportFragmentManager());
		
		HistoryBillFragment recentFragment = new HistoryBillFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", PropertyConstant.BILL_TYPE_RECENT);
		recentFragment.setArguments(bundle);
		
		HistoryBillFragment historyBillFragment = new HistoryBillFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putInt("type", PropertyConstant.BILL_TYPE_HISTORY);
		historyBillFragment.setArguments(bundle2);
		
		pagerAdapter.addFragment(recentFragment);
		pagerAdapter.addFragment(historyBillFragment);

		vpBill.setOffscreenPageLimit(pagerAdapter.getCount());
		vpBill.setAdapter(pagerAdapter);
		vpBill.setOnPageChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.switch_button1:
			btnRecent.setSelected(true);
			btnHistory.setSelected(false);
			vpBill.setCurrentItem(0);
			break;

		case R.id.switch_button2:
			btnRecent.setSelected(false);
			btnHistory.setSelected(true);
			vpBill.setCurrentItem(1);
			break;

		default:
			break;
		}
	}

	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		if (position == 0) {
			btnRecent.setSelected(true);
			btnHistory.setSelected(false);
		} else if (position == 1) {
			btnRecent.setSelected(false);
			btnHistory.setSelected(true);

		}

	}

}

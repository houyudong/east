package com.xflyme.go4.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xflyme.go4.BaseActivity;
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
public class ManagermentActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	private RelativeLayout rlRight;
	private ImageView ivRight;
	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private Button btnTeam;
	private Button btnAbout;

	private ViewPager vpManager;

	private List<ContactItem> items;

	private String enquiryPhone;
	private String secruityPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_managerment);

		context = ManagermentActivity.this;
		initView();
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);
		rlRight = (RelativeLayout) findViewById(R.id.rl_top_right);
		ivRight = (ImageView) findViewById(R.id.iv_title_right);
		ivRight.setBackgroundResource(R.drawable.manager_phone);

		rlLeft.setOnClickListener(this);
		rlRight.setOnClickListener(this);
		tvTitle.setText(getString(R.string.manager_title).toUpperCase());

		rlRight.setEnabled(false);

		btnTeam = (Button) findViewById(R.id.switch_button1);
		btnAbout = (Button) findViewById(R.id.switch_button2);

		btnTeam.setSelected(true);
		btnTeam.setText(getString(R.string.manager_team).toUpperCase());
		btnAbout.setText(getString(R.string.manager_about).toUpperCase());

		btnTeam.setOnClickListener(this);
		btnAbout.setOnClickListener(this);

		vpManager = (ViewPager) findViewById(R.id.vp_managerment);
		PropertyFragmentPagerAdapter pagerAdapter = new PropertyFragmentPagerAdapter(getSupportFragmentManager());
		pagerAdapter.addFragment(new TeamFragment());
		pagerAdapter.addFragment(new GuardFragment());

		vpManager.setOffscreenPageLimit(pagerAdapter.getCount());
		vpManager.setAdapter(pagerAdapter);
		vpManager.setOnPageChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.rl_top_right:
			showContact(v);
			break;

		case R.id.switch_button1:
			btnTeam.setSelected(true);
			btnAbout.setSelected(false);
			vpManager.setCurrentItem(0);
			break;

		case R.id.switch_button2:
			btnTeam.setSelected(false);
			btnAbout.setSelected(true);
			vpManager.setCurrentItem(1);
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
			btnTeam.setSelected(true);
			btnAbout.setSelected(false);
		} else if (position == 1) {
			btnTeam.setSelected(false);
			btnAbout.setSelected(true);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void showContact(View v) {
		new PopupWindowUtil(context, new OnPopupClickListener() {

			@Override
			public void onClick(View v, PopupWindow popupWindow) {
				popupWindow.dismiss();
				int position = (Integer) v.getTag();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + items.get(position).getPhone()));
				startActivity(intent);
			}
		}).showActionWindow(v, items);
	}

	public void setPhone(String enquiryPhone, String secruityPhone) {
		this.enquiryPhone = enquiryPhone;
		this.secruityPhone = secruityPhone;

		if (items == null) {
			items = new ArrayList<ContactItem>();
		}
		items.clear();
		if (!TextUtils.isEmpty(enquiryPhone)) {
			ContactItem item = new ContactItem();
			item.setTypeid(1);
			item.setTypename("Enquiry:" + enquiryPhone);
			item.setPhone(enquiryPhone);
			items.add(item);
		}
		if (!TextUtils.isEmpty(secruityPhone)) {
			ContactItem item = new ContactItem();
			item.setTypeid(2);
			item.setTypename("Security:" + secruityPhone);
			item.setPhone(secruityPhone);
			items.add(item);
		}

		if (!TextUtils.isEmpty(enquiryPhone) || !TextUtils.isEmpty(secruityPhone)) {
			rlRight.setEnabled(true);
		}

	}

}

package com.xflyme.go4.bookfacility;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.booking.MyBookingActivity;
import com.xflyme.go4.entity.OrderDetailItem;
import com.xflyme.go4.entity.UserInfoItem;
import com.xflyme.go4.view.HaloToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookConfirmActivity extends Activity implements OnClickListener,
		Orderbserver {

	private Context context;
	private ImageView ivLeft;
	private TextView tvTitle;
	private OrderDetailItem orderDetail;

	private EditText etName;
	private EditText etProperty;
	private EditText etUnit;
	private EditText etFacility;
	private EditText etDate;
	private EditText etTime;
	private EditText etFee;
	private EditText etContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookconfirm);
		context = BookConfirmActivity.this;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		orderDetail = (OrderDetailItem) bundle.getSerializable("orderDetail");

		OrderLogic.getInstance().addObserver(this);

		initView();
	}

	void initView() {
		ivLeft = (ImageView) findViewById(R.id.iv_title_left);
		ivLeft.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.text_title_center);
		tvTitle.setText(getString(R.string.facility_confirm).toUpperCase());

		etName = (EditText) findViewById(R.id.et_book_confirm_name);
		etProperty = (EditText) findViewById(R.id.et_book_confirm_property);
		etUnit = (EditText) findViewById(R.id.et_book_confirm_unit);
		etFacility = (EditText) findViewById(R.id.et_book_confirm_facility);
		etDate = (EditText) findViewById(R.id.et_book_confirm_date);
		etTime = (EditText) findViewById(R.id.et_book_confirm_time);
		etFee = (EditText) findViewById(R.id.et_book_confirm_fee);
		etContact = (EditText) findViewById(R.id.et_book_confirm_contact);

		UserInfoItem userInfo = PropertyApplication.mUserCache.getUserInfo()
				.getData();
		etName.setText(userInfo.getUserName());
		etProperty.setText(orderDetail.getCommunityName());
		etUnit.setText(orderDetail.getHousesId() + "-"
				+ orderDetail.getHousesName());
		etFacility.setText(orderDetail.getSiteName());
		etDate.setText(orderDetail.getOrderDate());
		etTime.setText(orderDetail.getOrderTime());
		etFee.setText("0");
		etContact.setText(orderDetail.getTel());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;

		case R.id.btn_confirm:
			OrderLogic.getInstance().onOrder(orderDetail);

			HaloToast.showNewWaitDialog(context, false,
					getString(R.string.loading));
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OrderLogic.getInstance().removeObserver(this);
	}

	@Override
	public void onOrderResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		if (resultCode == 0) {
			finish();
		}
	}

}

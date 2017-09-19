package com.xflyme.go4.booking;

import com.xflyme.go4.R;
import com.xflyme.go4.bookfacility.OrderLogic;
import com.xflyme.go4.bookfacility.Orderbserver;
import com.xflyme.go4.entity.BookFacilityItem;
import com.xflyme.go4.entity.OrderDetailItem;
import com.xflyme.go4.view.HaloToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends Activity implements OnClickListener, Orderbserver {

	private Context context;
	private ImageView ivLeft;
	private TextView tvTitle;
	private RelativeLayout rlBottom;

	private BookFacilityItem item;
	private int type;

	private TextView tvName;
	private TextView tvProperty;
	private TextView tvUnit;
	private TextView tvFacility;
	private TextView tvDate;
	private TextView tvTime;
	private TextView tvFee;
	private TextView tvPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookdetail);
		context = BookDetailActivity.this;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		item = (BookFacilityItem) bundle.getSerializable("item");
		type = bundle.getInt("type");

		OrderLogic.getInstance().addObserver(this);

		initView();
		setData(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OrderLogic.getInstance().removeObserver(this);
	}

	void initView() {
		ivLeft = (ImageView) findViewById(R.id.iv_title_left);
		ivLeft.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.text_title_center);
		tvTitle.setText(getString(R.string.facility_confirm).toUpperCase());

		rlBottom = (RelativeLayout) findViewById(R.id.ll_book_bottom);

		if (type == 2) {
			rlBottom.setVisibility(View.GONE);
		}

		tvDate = (TextView) findViewById(R.id.tv_book_detail_date_value);
		tvName = (TextView) findViewById(R.id.tv_book_detail_name_value);
		tvProperty = (TextView) findViewById(R.id.tv_book_detail_property_value);
		tvUnit = (TextView) findViewById(R.id.tv_book_detail_unit_value);
		tvFacility = (TextView) findViewById(R.id.tv_book_detail_facility_value);
		tvTime = (TextView) findViewById(R.id.tv_book_detail_time_value);
		tvFee = (TextView) findViewById(R.id.tv_book_detail_fee_value);
		tvPhone = (TextView) findViewById(R.id.tv_book_detail_contact_value);
	}

	void setData(BookFacilityItem item) {
		tvName.setText(item.getName());
		tvProperty.setText(item.getCommunityName());
		tvUnit.setText(item.getHousesId() + "-" + item.getHousesName());
		tvFacility.setText(item.getSiteName());
		tvDate.setText(item.getOrderDate());
		tvTime.setText(item.getOrderTimeDesc());
		tvFee.setText("0");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_left:
			finish();
			break;
			
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.btn_invite_friend:
			Uri smsToUri = Uri.parse("smsto:");

			Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

			startActivity(intent);
			break;

		case R.id.btn_edit:

			/*
			 * Intent intent = new Intent(context, BookSelectActivity.class);
			 * Bundle bundle = new Bundle(); bundle.putSerializable("site",
			 * item); bundle.putBoolean("isEdit", true);
			 * intent.putExtras(bundle); startActivity(intent);
			 */
			OrderLogic.getInstance().onOrder(facility2Order());
			HaloToast.showNewWaitDialog(context, false, getString(R.string.loading));
			break;

		default:
			break;
		}
	}

	@Override
	public void onOrderResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		if (resultCode != 0) {
			Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
		} else {
			if (!TextUtils.isEmpty(message)) {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
			}

			finish();
		}

	}

	OrderDetailItem facility2Order() {
		OrderDetailItem detailItem = new OrderDetailItem();
		detailItem.setId(item.getId());
		detailItem.setSiteId(item.getSiteId());
		detailItem.setSiteName(item.getSiteName());
		detailItem.setCompanyId(item.getCompanyId());
		detailItem.setCommunityId(item.getCommunityId());
		detailItem.setHousesId(item.getHousesId());
		detailItem.setState(2);
		detailItem.setOrderDate(item.getOrderDate());

		return detailItem;
	}

}

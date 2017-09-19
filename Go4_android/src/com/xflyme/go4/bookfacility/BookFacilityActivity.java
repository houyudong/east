package com.xflyme.go4.bookfacility;

import java.util.ArrayList;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.AppBookFacilityEntity;
import com.xflyme.go4.entity.ServerBookFacilityEntity;
import com.xflyme.go4.entity.BookFacilityItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookFacilityActivity extends Activity
		implements OnClickListener, OnChildClickListener, BookFacilityListObserver {

	private Context context;
	private ImageView ivLeft;
	private TextView tvTitle;
	private ExpandableListView lvFacility;
	private BookFacilityAdapter facilityAdapter;
	private ArrayList<AppBookFacilityEntity> faciLities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookfacility);
		context = BookFacilityActivity.this;

		BookFacilityListLogic.getInstance().addObserver(this);

		initView();
		BookFacilityListLogic.getInstance().onGetBookFacilityList(1, 100);
	}

	void initView() {
		ivLeft = (ImageView) findViewById(R.id.iv_title_left);
		ivLeft.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.text_title_center);
		tvTitle.setText(getString(R.string.facility_itle).toUpperCase());

		lvFacility = (ExpandableListView) findViewById(R.id.lv_bookfacility);
		facilityAdapter = new BookFacilityAdapter(context);
		lvFacility.setAdapter(facilityAdapter);
		lvFacility.setOnChildClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BookFacilityListLogic.getInstance().removeObserver(this);
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

		default:
			break;
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		Intent intent = new Intent(context, BookSelectActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("site", faciLities.get(groupPosition).getItems().get(childPosition));
		intent.putExtras(bundle);
		startActivity(intent);

		return true;
	}

	@Override
	public void onGetBookFacilityListSuccess(ServerBookFacilityEntity bookFacilityEntity) {

		if (bookFacilityEntity == null || bookFacilityEntity.getData() == null
				|| bookFacilityEntity.getData().getSchSitesList() == null) {

			Toast.makeText(context, getString(R.string.str_network_timeout_msg), Toast.LENGTH_SHORT).show();
			return;
		}
		serverFacility2AppFacility(bookFacilityEntity);
		facilityAdapter.refresh(faciLities);

	}

	@Override
	public void onGetBookFacilityListFail(int resultCode, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	void serverFacility2AppFacility(ServerBookFacilityEntity serverBookFacilityEntity) {
		faciLities = new ArrayList<AppBookFacilityEntity>();
		ArrayList<BookFacilityItem> tenniss = new ArrayList<BookFacilityItem>();
		ArrayList<BookFacilityItem> barbecues = new ArrayList<BookFacilityItem>();
		ArrayList<BookFacilityItem> ktvs = new ArrayList<BookFacilityItem>();
		for (int i = 0; i < serverBookFacilityEntity.getData().getSchSitesList().size(); i++) {
			BookFacilityItem item = serverBookFacilityEntity.getData().getSchSitesList().get(i);
			if (item.getType() == 0) {
				tenniss.add(item);
			} else if (item.getType() == 1) {
				barbecues.add(item);
			} else if (item.getType() == 2) {
				ktvs.add(item);
			}
		}
		if (tenniss.size() > 0) {
			AppBookFacilityEntity tennisEntity = new AppBookFacilityEntity();
			tennisEntity.setTitle(tenniss.get(0).getTypeName());
			tennisEntity.setItems(tenniss);
			faciLities.add(tennisEntity);
		}
		if (barbecues.size() > 0) {
			AppBookFacilityEntity barbecueEntity = new AppBookFacilityEntity();
			barbecueEntity.setTitle(barbecues.get(0).getTypeName());
			barbecueEntity.setItems(barbecues);
			faciLities.add(barbecueEntity);
		}
		if (ktvs.size() > 0) {
			AppBookFacilityEntity ktvEntity = new AppBookFacilityEntity();
			ktvEntity.setTitle(ktvs.get(0).getTypeName());
			ktvEntity.setItems(ktvs);
			faciLities.add(ktvEntity);
		}

	}
}

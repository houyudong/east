package com.xflyme.go4.bookfacility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.BookFacilityItem;
import com.xflyme.go4.entity.OrderDetailItem;
import com.xflyme.go4.entity.SiteDetailEntity;
import com.xflyme.go4.entity.UserInfoItem;
import com.xflyme.go4.entity.SiteDetailEntity.SiteDetailDayItem;
import com.xflyme.go4.entity.SiteDetailEntity.SiteDetailMouthItem;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.view.HaloToast;
import com.xflyme.go4.view.NoSlideGridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.xflyme.go4.view.NumberPicker.OnPickListener;

public class BookSelectActivity extends Activity implements OnClickListener,
		OnHourClickListener, SiteDetailObserver {

	private Context context;
	private ImageView ivLeft;
	private TextView tvTitle;
	private RelativeLayout rlLelt;
	private BookFacilityItem facilityItem;
	private NoSlideGridView gridView;
	private NoSlideGridView gridViewTitle;
	private NoSlideGridView gridViewHour;
	private XflymeCalendarAdapter adapter;
	private GridTitleAdapter titleAdapter;
	private GridHourAdapter hourAdapter;
	private ArrayList<Day> days;

	private TextView tvMonth;

	private String[] monthes;
	private int currentMonth = 0;
	private ArrayList<SiteDetailDayItem> hours;
	private Button btnBottom;

	private String dayTime;
	private boolean isEdit = false;
	private int selectCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookselect);
		context = BookSelectActivity.this;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		facilityItem = (BookFacilityItem) bundle.getSerializable("site");
		isEdit = bundle.getBoolean("isEdit");

		SiteDetailLogic.getInstance().addObserver(this);
		getFuture12Months();

		int year;
		int month;

		if (isEdit) {
			dayTime = facilityItem.getOrderDate();
			CLog.e("daytime", dayTime);
			year = Integer.parseInt(dayTime.substring(0, 4));
			month = Integer.parseInt(dayTime.substring(5, 7)) - 1;
		} else {
			year = Integer.parseInt(monthes[0].substring(0, 4));
			month = Integer.parseInt(monthes[0].substring(5)) - 1;
			dayTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}

		getDays(year, month);

		initView();

		SiteDetailLogic.getInstance().onGetSiteDetail(dayTime,
				facilityItem.getId());
		HaloToast
				.showNewWaitDialog(context, false, getString(R.string.loading));

	}

	void initView() {

		btnBottom = (Button) findViewById(R.id.btn_bookselect_bottom);

		tvMonth = (TextView) findViewById(R.id.tv_bookselect_month);
		tvMonth.setText(monthes[0]);
		ivLeft = (ImageView) findViewById(R.id.iv_title_left);
		ivLeft.setOnClickListener(this);

		rlLelt = (RelativeLayout) findViewById(R.id.rl_top_left);
		rlLelt.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.text_title_center);
		tvTitle.setText(facilityItem.getName().toUpperCase());

		gridView = (NoSlideGridView) findViewById(R.id.grid_day);
		adapter = new XflymeCalendarAdapter(context);
		gridView.setDrawSelectorOnTop(false);
		gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new DateOnItemClickListener());

		adapter.refresh(days);

		gridViewTitle = (NoSlideGridView) findViewById(R.id.grid_title);
		titleAdapter = new GridTitleAdapter(context);
		gridViewTitle.setAdapter(titleAdapter);

		gridViewHour = (NoSlideGridView) findViewById(R.id.grid_hour);
		gridViewHour.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
		gridViewHour.setDrawSelectorOnTop(false);
		hourAdapter = new GridHourAdapter(context);
		gridViewHour.setAdapter(hourAdapter);
		hourAdapter.setOnHourClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SiteDetailLogic.getInstance().removeObserver(this);
	}

	public void getDays(int year, int month) {
		days = new ArrayList<Day>();

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) cal.get(Calendar.DAY_OF_WEEK);
		TimeZone tz = TimeZone.getDefault();

		int length;

		// figure size of the array
		if (firstDay == 1) {
			length = lastDay - 1;

		} else {
			length = lastDay + firstDay - 1;
		}

		int j = 0;

		// populate empty days before first real day
		if (firstDay > 1) {
			for (j = 0; j < firstDay; j++) {
				Day d = new Day(context, 0, 0, 0);
				days.add(d);
			}
		}

		// populate days
		int dayNumber = 1;

		if (j > 0 && days.size() > 0 && j != 1) {
			days.remove(j - 1);
		}

		for (int i = j - 1; i < length; i++) {
			Day d = new Day(context, dayNumber, year, month + 1);
			d.setFull(false);

			dayNumber++;
			days.add(d);
			CLog.e("day", d.toString());
		}

	}

	void setDays(ArrayList<SiteDetailMouthItem> dayItems) {
		for (int i = 1; i <= dayItems.size(); i++) {
			/*
			 * if (dayItems.get(dayItems.size() - i).getState() == 0) {
			 * days.get(days.size() - i).setFull(true); }
			 */
			if (i % 2 == 0) {
				days.get(days.size() - i).setFull(true);
			}
		}
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

		case R.id.tv_bookselect_month:
			selectMonth();
			break;

		case R.id.btn_bookselect_bottom:
			Intent intent = new Intent(context, BookConfirmActivity.class);

			UserInfoItem userInfo = PropertyApplication.mUserCache
					.getUserInfo().getData();

			ArrayList<String> ams = new ArrayList<String>();
			ArrayList<String> pms = new ArrayList<String>();
			String time = "";

			for (int i = 0; i < hours.size(); i++) {
				if (hours.get(i).isSelected()) {
					if (hours.get(i).getDayType() == 1) {
						ams.add(hours.get(i).getDayTime());
					} else {
						pms.add(hours.get(i).getDayTime());
					}
					time += " " + hours.get(i).getDayTimeDesc();
				}
			}

			OrderDetailItem item = new OrderDetailItem();
			item.setSiteId(facilityItem.getId());
			item.setSiteName(facilityItem.getName());
			item.setCompanyId(userInfo.getCompanyId());
			item.setCommunityId(userInfo.getCurCommunityId());
			item.setCommunityName(userInfo.getCurCommunityName());
			item.setHousesId(userInfo.getCurHousesId());
			item.setHousesName(userInfo.getCurHousesUnitNo());
			item.setType(facilityItem.getType());
			if (isEdit) {
				item.setState(1);
			} else {
				item.setState(0);
			}

			item.setTel(userInfo.getTelphone());
			item.setOrderDate(dayTime);
			item.setAmOrderTime(ams);
			item.setPmOrderTime(pms);
			item.setOrderTime(time);

			Bundle bundle = new Bundle();
			bundle.putSerializable("orderDetail", item);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	class DateOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Day day = (Day) adapter.getItem(position);
			SiteDetailLogic.getInstance().onGetSiteDetail(day.toString(),
					facilityItem.getId());
			dayTime = day.toString();
			HaloToast.showNewWaitDialog(context, false,
					getString(R.string.loading));
		}

	}

	@Override
	public void onGetSiteDetailSuccess(SiteDetailEntity siteDetailEntity) {
		HaloToast.dismissWaitDialog();
		this.hours = siteDetailEntity.getData().getDaydetail().getDayjson();
		hourAdapter.refresh(siteDetailEntity.getData().getDaydetail()
				.getDayjson());
		
		selectCount = 0;

		// setDays(siteDetailEntity.getData().getDaydetail().getMouthjson());
		for (int i = 0; i < siteDetailEntity.getData().getDaydetail()
				.getDayjson().size(); i++) {
			if (siteDetailEntity.getData().getDaydetail().getDayjson().get(i)
					.getIsCancel() == 1) {
				selectCount = selectCount + 1;
			}
		}

		btnBottom.setSelected(true);
		btnBottom.setEnabled(false);

		adapter.refresh(days);
	}

	@Override
	public void onGetSiteDetailFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

	/**
	 * @Description:选择日期
	 * @author:刘祥飞
	 */
	private void selectMonth() {

		HaloToast.showPicker(context, monthes, currentMonth,
				new OnPickListener() {
					@Override
					public void onDone(int index) {
						tvMonth.setText(monthes[index]);
						currentMonth = index;

						int year = Integer.parseInt(monthes[index].substring(0,
								4));
						int month = Integer.parseInt(monthes[index]
								.substring(5)) - 1;
						getDays(year, month);
						adapter.refresh(days);

						SiteDetailLogic.getInstance().onGetSiteDetail(
								year + "-" + fillZero(month + 1) + "-" + "01",
								facilityItem.getId());
						dayTime = year + "-" + fillZero(month + 1) + "-" + "01";
						HaloToast.showNewWaitDialog(context, false,
								getString(R.string.loading));
					}

					@Override
					public void onCancle() {

					}
				}, getString(R.string.select_month));

	}

	/**
	 * 获取未来十二个月份
	 */
	void getFuture12Months() {

		monthes = new String[12];

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); // 要先+1,才能把本月的算进去</span>
		for (int i = 0; i < 12; i++) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); // 逐次往前推1个月
			monthes[i] = cal.get(Calendar.YEAR) + "-"
					+ fillZero((cal.get(Calendar.MONTH) + 1));
		}

		cal.clear();

	}

	String fillZero(int i) {
		if (i < 10) {
			return "0" + i;
		}
		return String.valueOf(i);
	}

	@Override
	public void onHourClick(int position) {

		if (hours.get(position).isSelected()) {
			hours.get(position).setSelected(false);
			int count = 0;
			for (int i = 0; i < hours.size(); i++) {
				if (hours.get(i).isSelected()) {
					count = count + 1;
				}
			}
			if (count > 0) {
				btnBottom.setEnabled(true);
				btnBottom.setSelected(false);
			} else {
				btnBottom.setSelected(true);
				btnBottom.setEnabled(false);
			}
		} else {
			int count = 0;
			for (int i = 0; i < hours.size(); i++) {
				if (hours.get(i).isSelected()) {
					count = count + 1;
				}
			}
			if (count + selectCount >= 2) {
				return;
			}
			hours.get(position).setSelected(true);
			count = count + 1;
			if (count > 0) {
				btnBottom.setEnabled(true);
				btnBottom.setSelected(false);
			} else {
				btnBottom.setSelected(true);
				btnBottom.setEnabled(false);
			}
		}

		hourAdapter.refresh(hours);
	}

}

package com.xflyme.go4.booking;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.MyBookingEntity;
import com.xflyme.go4.util.CLog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CurrentBookFragment extends Fragment
		implements MyBookingObserver, OnRefreshListener<ListView>, OnItemClickListener {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private MyBookingAdapter bookingAdapter;
	private Context context;
	private boolean isRefreshAction;

	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	private int type = 1;

	MyBookingLogic logic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_booking, container, false);
		context = getActivity();

		Bundle bundle = getArguments();

		type = bundle.getInt("type");

		logic = new MyBookingLogic();
		logic.addObserver(this);
		initView(rootView);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (logic.getObservers().size() == 0) {
			logic.addObserver(this);
		}
		
		isRefreshAction = true;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				refreshListView.setRefreshing();
			}
		}, 500);
	}

	@Override
	public void onPause() {
		super.onPause();
		logic.removeObserver(this);
	}

	void initView(View view) {
		refreshListView = (PullToRefreshListView) view.findViewById(R.id.team_member_list);

		bookingAdapter = new MyBookingAdapter(context);

		listView = refreshListView.getRefreshableView();
		listView.setAdapter(bookingAdapter);
		listView.setSelector(R.color.transparent);
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
		refreshListView.setMode(Mode.BOTH);

		refreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.poll_refresh_down_loadmore));
		refreshListView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel(getString(R.string.poll_refresh_down_refreshing));
		refreshListView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel(getString(R.string.poll_refresh_down_release));
		refreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("");
		refreshListView.setOnRefreshListener(this);

	

	}
	

	@Override
	public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
		if (refreshView.isHeaderShown() || isRefreshAction) {
			isRefreshAction = true;
			logic.onGetMyBooking(pageNo, PAGE_SIZE, type);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			logic.onGetMyBooking(pageNo + 1, PAGE_SIZE, type);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, BookDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", bookingAdapter.getItem(position - 1));
		bundle.putInt("type", type);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onGetMyBookingSuccess(MyBookingEntity bookingEntity) {
		refreshListView.onRefreshComplete();

		if ((bookingEntity == null || bookingEntity.getData() == null
				|| bookingEntity.getData().getSchedulesList() == null) && pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			CLog.e("success", bookingEntity.getData().getSchedulesList().size() + "");
			bookingAdapter.refresh(bookingEntity.getData().getSchedulesList());
			isRefreshAction = false;
			if (PAGE_SIZE > bookingEntity.getData().getSchedulesList().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			bookingAdapter.add(bookingEntity.getData().getSchedulesList());
			if (PAGE_SIZE >= bookingEntity.getData().getSchedulesList().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onGetMyBookingFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

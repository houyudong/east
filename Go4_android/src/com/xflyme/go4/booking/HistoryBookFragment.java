package com.xflyme.go4.booking;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.R;

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
import android.widget.AdapterView.OnItemClickListener;

public class HistoryBookFragment extends Fragment implements OnRefreshListener<ListView>, OnItemClickListener {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private MyBookingAdapter bookingAdapter;
	private Context context;
	private boolean isRefreshAction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_team, container, false);
		context = getActivity();

		initView(rootView);

		return rootView;
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

		isRefreshAction = true;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				refreshListView.setRefreshing();
			}
		}, 500);

	}

	@Override
	public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
		/*
		 * if (refreshView.isHeaderShown() || isRefreshAction) { isRefreshAction
		 * = true; new Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { isRefreshAction = false;
		 * refreshView.onRefreshComplete();
		 * bookingAdapter.refresh(getbookings()); } }, 2000);
		 * 
		 * } else { CLog.e("下拉", "上拉"); isRefreshAction = false; new
		 * Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { refreshView.onRefreshComplete();
		 * bookingAdapter.add(getbookings()); } }, 2000); }
		 */
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, BookDetailActivity.class);
		startActivity(intent);
	}
}

package com.xflyme.go4.activities;

import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.util.CLog;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityListFragment extends Fragment
		implements OnRefreshListener<ListView>, OnItemClickListener, ActivityListObserver {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private ActivityAdapter activityAdapter;
	private Context context;
	private boolean isRefreshAction;

	ActivitiesLogic logic;

	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	private int category = 100;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_team, container, false);
		context = getActivity();

		category = getArguments().getInt("category");

		logic = new ActivitiesLogic();
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
	}

	@Override
	public void onPause() {
		super.onPause();
		logic.removeObserver(this);
	}

	void initView(View view) {
		refreshListView = (PullToRefreshListView) view.findViewById(R.id.team_member_list);

		activityAdapter = new ActivityAdapter(context);

		listView = refreshListView.getRefreshableView();
		listView.setAdapter(activityAdapter);
		listView.setSelector(R.drawable.selector_item_noticeboard);
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
		if (refreshView.isHeaderShown() || isRefreshAction) {
			isRefreshAction = true;
			logic.onGetActivity(pageNo, PAGE_SIZE, category, 0);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			logic.onGetActivity(pageNo + 1, PAGE_SIZE, category, 0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, ActivityDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("activityId", activityAdapter.getItem(position - 1).getActivityId());
		bundle.putInt("category", category);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	public void onActivityListSuccess(ActivityResp activityResp) {
		refreshListView.onRefreshComplete();

		if ((activityResp == null || activityResp.getData() == null
				|| activityResp.getData().getListBusActivity() == null) && pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			activityAdapter.refresh(activityResp.getData().getListBusActivity());
			isRefreshAction = false;
			if (PAGE_SIZE > activityResp.getData().getListBusActivity().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			activityAdapter.add(activityResp.getData().getListBusActivity());
			if (PAGE_SIZE >= activityResp.getData().getListBusActivity().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onActivityListFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

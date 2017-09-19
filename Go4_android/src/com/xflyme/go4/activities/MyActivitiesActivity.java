package com.xflyme.go4.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.util.CLog;

/**
 * My Activities
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class MyActivitiesActivity extends BaseActivity
		implements OnRefreshListener<ListView>, OnItemClickListener, MyActivitiesObserver, OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private ActivityAdapter activityAdapter;
	private Context context;
	private boolean isRefreshAction;

	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myactivities);

		context = MyActivitiesActivity.this;

		MyActivitiesLogic.getInstance().addObserver(this);

		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyActivitiesLogic.getInstance().removeObserver(this);
	}

	void initView() {

		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.myactivities_title));

		refreshListView = (PullToRefreshListView) findViewById(R.id.team_member_list);

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
			MyActivitiesLogic.getInstance().onGetMyFavorites(pageNo, PAGE_SIZE);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			MyActivitiesLogic.getInstance().onGetMyFavorites(pageNo + 1, PAGE_SIZE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(context, ActivityDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("activityId", activityAdapter.getItem(position - 1).getActivityId());
		bundle.putInt("category", 105);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	public void onGetMyFavoritesSuccess(ActivityResp activityResp) {
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
	public void onGetMyFavoritesFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

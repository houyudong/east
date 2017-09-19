package com.xflyme.go4.activities;

import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.xflyme.go4.entity.activity.BusActivity;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.view.HaloToast;

/**
 * My Favorites
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class MyFavoritesActivity extends BaseActivity
		implements OnRefreshListener<ListView>, MyFavoritesObserver, OnClickListener {

	private TextView btnLeft;
	private TextView btnRight;
	private TextView tvTitle;

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private MyFavoritesAdapter favoritesAdapter;
	private Context context;
	private boolean isRefreshAction;

	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	private boolean isEditMode = false;
	private Set<Long> positions;
	private ArrayList<BusActivity> activities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myfavorites);

		context = MyFavoritesActivity.this;
		activities = new ArrayList<BusActivity>();
		MyFavoritesLogic.getInstance().addObserver(this);

		initView(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyFavoritesLogic.getInstance().removeObserver(this);
	}

	void initView(Bundle bundle) {

		btnLeft = (TextView) findViewById(R.id.btn_title_left);
		btnRight = (TextView) findViewById(R.id.btn_title_right);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		btnRight.setOnClickListener(this);
		tvTitle.setText(getString(R.string.myfavorites_title));

		btnRight.setText("Edit");

		refreshListView = (PullToRefreshListView) findViewById(R.id.team_member_list);

		favoritesAdapter = new MyFavoritesAdapter(bundle, context, activities);

		listView = refreshListView.getRefreshableView();

		favoritesAdapter.setAdapterView(listView);
		favoritesAdapter.showActionMode(false);
		favoritesAdapter.setOnItemClickListener(new MyItemClick(favoritesAdapter));
		// listView.setSelector(R.drawable.selector_item_noticeboard);
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
			MyFavoritesLogic.getInstance().onGetMyFavorites(pageNo, PAGE_SIZE);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			MyFavoritesLogic.getInstance().onGetMyFavorites(pageNo + 1, PAGE_SIZE);
		}
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
			activities.clear();
			activities.addAll(activityResp.getData().getListBusActivity());
			favoritesAdapter.notifyDataSetChanged();
			isRefreshAction = false;
			if (PAGE_SIZE > activityResp.getData().getListBusActivity().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			activities.addAll(activityResp.getData().getListBusActivity());
			favoritesAdapter.notifyDataSetChanged();
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
			if (isEditMode) {
				isEditMode = false;
				btnLeft.setBackgroundResource(R.drawable.selector_title_back);
				btnLeft.setText("");
				btnRight.setText("Edit");
				favoritesAdapter.setEditMode(isEditMode);
				favoritesAdapter.notifyDataSetChanged();
				cancleAll(favoritesAdapter);
			} else {
				finish();
			}
			break;
		case R.id.btn_title_right:
			if (isEditMode) {
				int[] ids = getSelectedItems(favoritesAdapter);
				ArrayList<Integer> orgids = new ArrayList<Integer>();
				for (int i = 0; i < ids.length; i++) {
					orgids.add(ids[i]);
				}
				
				if (ids.length > 0) {
					MyFavoritesLogic.getInstance().onAddFavorite(orgids, 2);
					HaloToast.showNewWaitDialog(context, false, getString(R.string.wait));
				}
			} else {
				isEditMode = true;
				btnLeft.setBackgroundResource(R.color.main_color);
				btnLeft.setText("Cancel");
				btnRight.setText("Delete");
				favoritesAdapter.setEditMode(isEditMode);
				favoritesAdapter.notifyDataSetChanged();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 得到已经选中的items
	 * 
	 * @param adapter
	 * @return
	 */
	private int[] getSelectedItems(MyFavoritesAdapter adapter) {
		// 得到选中的items
		Set<Long> selection = adapter.getCheckedItems();
		positions = selection;
		int[] items = new int[selection.size()];
		int i = 0;
		for (long position : selection) {
			items[i++] = adapter.getItem((int) position).getActivityId();

		}

		return items;
	}

	private class MyItemClick implements OnItemClickListener {

		private MyFavoritesAdapter mAdapter;

		public MyItemClick(MyFavoritesAdapter adapter) {
			mAdapter = adapter;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Intent intent = new Intent(context, ActivityDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("activityId", mAdapter.getItem(position - 1).getActivityId());
			bundle.putInt("category", 104);
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}

	private void cancleAll(MyFavoritesAdapter adapter) {
		for (int i = 0; i < adapter.getCount(); ++i) {
			adapter.setItemChecked(i, false);
		}
	}

	@Override
	public void onAddFavoriteResult(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		toast(message);
		if (resultCode == 0) {
			isEditMode = false;
			btnLeft.setBackgroundResource(R.drawable.selector_title_back);
			btnLeft.setText("");
			btnRight.setText("Edit");
			favoritesAdapter.setEditMode(isEditMode);

			for (long position : positions) {
				CLog.e("position", position + "");
				activities.remove((int) position);
			}
			CLog.e("position", activities.size() + "");
			favoritesAdapter.notifyDataSetChanged();
			cancleAll(favoritesAdapter);
		}
	}

}

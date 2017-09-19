package com.xflyme.go4.noticeboard;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.NoticeBoardDetailEntity;
import com.xflyme.go4.entity.NoticeBoardEntity;
import com.xflyme.go4.entity.NoticeBoardItem;
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

public class RecentNoticeFragment extends Fragment implements
		OnItemClickListener, OnRefreshListener<ListView> ,NoticeBoardObserver {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private NoticeBoardAdapter noticeBoardAdapter;
	private Context context;
	private boolean isRefreshAction;
	
	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	private int category = 1;
	NoticeBoardLogic logic;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_recent, container,
				false);
		context = getActivity();
		category = getArguments().getInt("category");
		
		logic = new NoticeBoardLogic();
		logic.addObserver(this);
		

		initView(rootView);

		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(logic.getObservers().size() == 0){
			logic.addObserver(this);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		logic.removeObserver(this);
	}

	void initView(View view) {
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.team_member_list);

		noticeBoardAdapter = new NoticeBoardAdapter(context);

		listView = refreshListView.getRefreshableView();
		listView.setAdapter(noticeBoardAdapter);
		listView.setOnItemClickListener(this);
		listView.setSelector(R.drawable.selector_item_noticeboard);
		registerForContextMenu(listView);
		refreshListView.setMode(Mode.BOTH);

		refreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
				getString(R.string.poll_refresh_down_loadmore));
		refreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				getString(R.string.poll_refresh_down_refreshing));
		refreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
				getString(R.string.poll_refresh_down_release));
		refreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel(
				"");
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
			logic.onGetNoticeBoardList(pageNo, PAGE_SIZE, category, 0);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			logic.onGetNoticeBoardList(pageNo+1, PAGE_SIZE, category, 0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(context, NoticeBoardDetailActivity.class);
		intent.putExtra("activityId", ((NoticeBoardItem)noticeBoardAdapter.getItem(arg2 - 1)).getId());
		intent.putExtra("type", category);
		startActivity(intent);
	}

	@Override
	public void onGetNoticeBoardSuccess(NoticeBoardEntity noticeBoardEntity) {
		refreshListView.onRefreshComplete();

		if ((noticeBoardEntity == null || noticeBoardEntity.getData() == null || noticeBoardEntity.getData().getListBusNotice() == null)
				&& pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			noticeBoardAdapter.refresh(noticeBoardEntity.getData().getListBusNotice());
			isRefreshAction = false;
			if (PAGE_SIZE > noticeBoardEntity.getData().getListBusNotice().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			noticeBoardAdapter.add(noticeBoardEntity.getData().getListBusNotice());
			if (PAGE_SIZE >= noticeBoardEntity.getData().getListBusNotice().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onGetNoticeBoardFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onGetNoticeBoardDetailSuccess(
			NoticeBoardDetailEntity noticeBoardDetailEntity) {
		
	}

	@Override
	public void onGetNoticeBoardDetailFail(int resultCode, String message) {
		// TODO Auto-generated method stub
		
	}
}

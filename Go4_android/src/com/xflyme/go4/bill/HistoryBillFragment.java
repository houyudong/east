package com.xflyme.go4.bill;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.BillEntity;
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

public class HistoryBillFragment extends Fragment implements
		OnRefreshListener<ListView> , BillObserver , OnItemClickListener{

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private HistoryBillAdapter historyBillAdapter;
	private Context context;
	private boolean isRefreshAction;
	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	
	private int type = PropertyConstant.BILL_TYPE_RECENT;
	
	BillLogic logic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_bill_history,
				container, false);
		context = getActivity();
		
		type = getArguments().getInt("type");
		
		logic = new BillLogic();
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
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.recent_bill_list);

		historyBillAdapter = new HistoryBillAdapter(context);

		listView = refreshListView.getRefreshableView();
		listView.setAdapter(historyBillAdapter);
		listView.setSelector(R.color.transparent);
		registerForContextMenu(listView);
		refreshListView.setMode(Mode.BOTH);
		refreshListView.setOnItemClickListener(this);

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
			logic.onGetBill(1, PAGE_SIZE, type);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			logic.onGetBill(pageNo+1, PAGE_SIZE, type);
		}
	}
	

	@Override
	public void onGetBillSuccess(BillEntity billEntity) {
		refreshListView.onRefreshComplete();

		if ((billEntity == null || billEntity.getData() == null)
				&& pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			historyBillAdapter.refresh(billEntity.getData());
			isRefreshAction = false;
			if (PAGE_SIZE > billEntity.getData().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			historyBillAdapter.add(billEntity.getData());
			if (PAGE_SIZE >= billEntity.getData().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onGetBillFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(context,BillDetailActivity.class);
		intent.putExtra("url", historyBillAdapter.getItem(arg2 - 1).getAttachmentUrl());
		startActivity(intent);
	}

}

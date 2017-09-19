package com.xflyme.go4.manager;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.TeamInfoEntity;
import com.xflyme.go4.util.CLog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TeamFragment extends Fragment implements
		OnRefreshListener<ListView> , TeamInfoObserver {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private TeamAdapter teamAdapter;
	private Context context;
	private boolean isRefreshAction;
	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_team, container,
				false);
		context = getActivity();
		
		TeamInfoLogic.getInstance().addObserver(this);

		initView(rootView);

		return rootView;
	}

	void initView(View view) {
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.team_member_list);

		teamAdapter = new TeamAdapter(context);

		listView = refreshListView.getRefreshableView();
		listView.setAdapter(teamAdapter);
		listView.setSelector(R.color.transparent);
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
			TeamInfoLogic.getInstance().onGetTeamInfo(1, PAGE_SIZE);

		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			TeamInfoLogic.getInstance().onGetTeamInfo(pageNo+1, PAGE_SIZE);
		}
	}


	@Override
	public void onGetTeamInfoSuccess(TeamInfoEntity teamInfoEntity) {
		refreshListView.onRefreshComplete();

		if ((teamInfoEntity == null || teamInfoEntity.getData() == null)
				&& pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			teamAdapter.refresh(teamInfoEntity.getData());
			isRefreshAction = false;
			if (PAGE_SIZE > teamInfoEntity.getData().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			teamAdapter.add(teamInfoEntity.getData());
			if (PAGE_SIZE >= teamInfoEntity.getData().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onGetTeamInfoFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

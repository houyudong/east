package com.xflyme.go4.manager;


import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.AboutUsEntity;
import com.xflyme.go4.entity.TeamInfoEntity;
import com.xflyme.go4.entity.TeamMemItem;
import com.xflyme.go4.util.CLog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GuardFragment extends Fragment implements
		OnRefreshListener<ListView> , TeamInfoObserver,AboutUsObserver {

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private GuadeAdapter teamAdapter;
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
		AboutUsLogic.getInstance().addObserver(this);
		initView(rootView);
		AboutUsLogic.getInstance().onGetAboutUs(1);
		return rootView;
	}

	void initView(View view) {
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.team_member_list);

		teamAdapter = new GuadeAdapter(context);

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

		/*if ((teamInfoEntity == null || teamInfoEntity.getData() == null)
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
		}*/
		teamAdapter.refresh(getData());
	}
	
	ArrayList<TeamMemItem> getData(){
		ArrayList<TeamMemItem> items = new ArrayList<TeamMemItem>();
		
		TeamMemItem item1 = new TeamMemItem();
		item1.setUserName("Lim Teck Woo");
		item1.setJobTime("9AM-4PM");
		item1.setJobType("Security Guard A");
		
		TeamMemItem item2 = new TeamMemItem();
		item2.setUserName("Tan Jian Yong");
		item2.setJobTime("9AM-4PM");
		item2.setJobType("Security Guard B");
		
		TeamMemItem item3 = new TeamMemItem();
		item3.setUserName("Andika Sukumar");
		item3.setJobTime("12PM-7PM");
		item3.setJobType("Security Guard C");
		
		TeamMemItem item4 = new TeamMemItem();
		item4.setUserName("sunapulan Srilakshmi");
		item4.setJobTime("12PM-7PM");
		item4.setJobType("Security Guard D");
		
		TeamMemItem item5 = new TeamMemItem();
		item5.setUserName("Kok Boon Yong");
		item5.setJobTime("3PM-10PM");
		item5.setJobType("Security Guard E");
		
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
		
		return items;
	}

	@Override
	public void onGetTeamInfoFail(int resultCode, String message) {
		refreshListView.onRefreshComplete();
		teamAdapter.refresh(getData());
		//Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onGetAboutUsSuccess(AboutUsEntity aboutUsEntity) {
		((ManagermentActivity)getActivity()).setPhone(aboutUsEntity.getData().getEnquiryPhone(),aboutUsEntity.getData().getSecurityPhone());		
	}

	@Override
	public void onGetAboutUsFail(int resultCode, String message) {
		// TODO Auto-generated method stub
		
	}
}

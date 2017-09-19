package com.xflyme.go4.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.ReplyEntity;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.view.HaloToast;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class CommentsActivity extends BaseActivity
		implements OnRefreshListener<ListView>, CommentsObserver, OnClickListener {

	private TextView btnLeft;
	private TextView tvTitle;

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private CommentsAdapter commentsAdapter;
	private Context context;
	private boolean isRefreshAction;

	private static final int PAGE_SIZE = 10;
	private int pageNo = 1;
	private int activityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);

		Intent intent = getIntent();
		activityId = intent.getIntExtra("id", 0);
		context = CommentsActivity.this;
		CommentsLogic.getInstance().addObserver(this);
		initView(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CommentsLogic.getInstance().removeObserver(this);
	}

	void initView(Bundle bundle) {

		btnLeft = (TextView) findViewById(R.id.btn_title_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		tvTitle.setText(getString(R.string.comments_title));
		btnLeft.setOnClickListener(this);

		refreshListView = (PullToRefreshListView) findViewById(R.id.team_member_list);

		commentsAdapter = new CommentsAdapter(context);

		listView = refreshListView.getRefreshableView();

		listView.setAdapter(commentsAdapter);
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
			CommentsLogic.getInstance().onGetReplay(activityId, pageNo, PAGE_SIZE);
		} else {
			CLog.e("下拉", "上拉");
			isRefreshAction = false;
			CommentsLogic.getInstance().onGetReplay(activityId, pageNo + 1, PAGE_SIZE);
		}
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

	@Override
	public void onGetReplySuccess(ReplyEntity replyEntity) {
		refreshListView.onRefreshComplete();

		if ((replyEntity == null || replyEntity.getData() == null
				|| replyEntity.getData().getLsitActivityReply() == null) && pageNo == 1) {
			refreshListView.setMode(Mode.PULL_FROM_START);
			return;
		}

		if (isRefreshAction) {
			commentsAdapter.refresh(replyEntity.getData().getLsitActivityReply());
			listView.setSelection(commentsAdapter.getCount() - 1);

			isRefreshAction = false;
			if (PAGE_SIZE > replyEntity.getData().getLsitActivityReply().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				refreshListView.setMode(Mode.BOTH);
			}
		} else {
			commentsAdapter.add(replyEntity.getData().getLsitActivityReply());
			listView.setSelection(commentsAdapter.getCount() - 1);

			if (PAGE_SIZE >= replyEntity.getData().getLsitActivityReply().size()) {
				refreshListView.setMode(Mode.PULL_FROM_START);
			}
		}
	}

	@Override
	public void onGetReplyFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		toast(message);
	}

	@Override
	public void onReplyResult(int resultCode, String message) {

	}

}

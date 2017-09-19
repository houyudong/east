package com.xflyme.go4.noticeboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.NoticeBoardDetailEntity;
import com.xflyme.go4.entity.NoticeBoardEntity;
import com.xflyme.go4.entity.NoticeBoardItem;
import com.xflyme.go4.entity.VoteEntity;
import com.xflyme.go4.entity.VoteItem;
import com.xflyme.go4.view.HaloToast;
import com.xflyme.go4.view.NoSlideListView;

/**
 * 
 * @Description:Notice Board
 * @author:lxf
 * @see:
 * @since:
 * @Date:2015-06-28
 */
public class NoticeBoardDetailActivity extends Activity implements NoticeBoardObserver, VoteObserver, OnClickListener {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;

	private TextView tvNoticeTitle;
	private TextView tvTime;
	private TextView tvNum;
	private ImageView ivNotice;
	private TextView tvContent;
	private int noticeId;
	private int type;

	private ScrollView scView;

	private Button btnVote;
	private NoSlideListView lvVote;
	private VoteAdapter voteAdapter;
	private View viewBottom;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticeboard_detail);

		context = NoticeBoardDetailActivity.this;

		NoticeBoardLogic.getInstance().addObserver(this);

		noticeId = getIntent().getIntExtra("activityId", 0);
		type = getIntent().getIntExtra("type", 1);
		initView();
		NoticeBoardLogic.getInstance().onGetNoticeBoardDetail(0, 0, type, noticeId);
		VoteLogic.getInstance().addObserver(this);
		HaloToast.showNewWaitDialog(context, false, getString(R.string.loading));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		VoteLogic.getInstance().removeObserver(this);
		NoticeBoardLogic.getInstance().removeObserver(this);
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.noticeboard_title));

		scView = (ScrollView) findViewById(R.id.sc_content);

		tvNoticeTitle = (TextView) findViewById(R.id.tv_noticedetail_title);
		tvContent = (TextView) findViewById(R.id.tv_noticedetail_content);
		tvTime = (TextView) findViewById(R.id.tv_noticedetail_time);
		tvNum = (TextView) findViewById(R.id.tv_noticedetail_num);
		ivNotice = (ImageView) findViewById(R.id.iv_noticedetail);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.btn_vote:
			VoteLogic.getInstance().onVote(noticeId,
					voteAdapter.getItem(lvVote.getCheckedItemPosition()).getChoiceId());
			break;

		default:
			break;
		}
	}

	@Override
	public void onGetNoticeBoardSuccess(NoticeBoardEntity noticeBoardEntity) {

	}

	@Override
	public void onGetNoticeBoardFail(int resultCode, String message) {

	}

	@Override
	public void onGetNoticeBoardDetailSuccess(NoticeBoardDetailEntity noticeBoardDetailEntity) {
		scView.setVisibility(View.VISIBLE);
		NoticeBoardItem item = noticeBoardDetailEntity.getData().getBusNotice();
		tvNoticeTitle.setText(item.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(Long.parseLong(item.getCreateTime()));
		tvTime.setText(sdf.format(date));
		tvContent.setText(item.getContent());
		tvNum.setText(item.getClickCnt() + "");

		if (TextUtils.isEmpty(item.getLinkUrl())) {

			ivNotice.setVisibility(View.GONE);
		} else {
			ivNotice.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(item.getLinkUrl(), ivNotice);
		}

		if (item.getType() == 2 && item.getIsVote() == 1) {
			VoteLogic.getInstance().onGetVote(noticeId);
		} else {
			HaloToast.dismissWaitDialog();
		}

	}

	@Override
	public void onGetNoticeBoardDetailFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onGetVoteSuccess(VoteEntity voteEntity) {
		HaloToast.dismissWaitDialog();
		if (voteEntity.getIsVote() == 2 && voteEntity.getData().getHasNotVote() != null
				&& voteEntity.getData().getHasNotVote().size() > 0) {
			lvVote = (NoSlideListView) findViewById(R.id.lv_vote);
			lvVote.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			btnVote = (Button) findViewById(R.id.btn_vote);
			view = findViewById(R.id.view_top);
			viewBottom = findViewById(R.id.view_bottom);

			lvVote.setVisibility(view.VISIBLE);
			btnVote.setVisibility(view.VISIBLE);
			view.setVisibility(view.VISIBLE);
			viewBottom.setVisibility(view.VISIBLE);

			voteAdapter = new VoteAdapter(context);
			lvVote.setAdapter(voteAdapter);
			voteAdapter.refresh(voteEntity.getData().getHasNotVote());

		}
	}

	@Override
	public void onGetVoteFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
	}

	@Override
	public void onVoteResult(int resultCode, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		if (resultCode == 0) {
			view.setVisibility(view.GONE);
			viewBottom.setVisibility(view.GONE);
			lvVote.setVisibility(view.GONE);
			btnVote.setVisibility(view.GONE);
		}
	}

}

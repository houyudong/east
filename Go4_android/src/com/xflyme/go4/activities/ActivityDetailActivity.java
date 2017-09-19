package com.xflyme.go4.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.ReplayItem;
import com.xflyme.go4.entity.ReplyEntity;
import com.xflyme.go4.entity.UserInfoItem;
import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.entity.activity.BusActivity;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.view.HaloToast;
import com.xflyme.go4.view.NoAutoScrollView;
import com.xflyme.go4.view.NoSlideListView;

/**
 * 
 * @Description:Notice Board
 * @author:lxf
 * @see:
 * @since:
 * @Date:2015-06-28
 */
public class ActivityDetailActivity extends BaseActivity implements
		OnClickListener, MyFavoritesObserver, ActivityListObserver,
		CommentsObserver {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;
	int activityId;
	int category;

	private TextView tvActivityTitle;
	private TextView tvTime;
	private TextView tvNum;
	private TextView tvComments;
	private TextView tvFavor;
	private TextView tvContent;
	private ImageView ivActivity;
	private ImageView ivHeart;
	private NoAutoScrollView scActivity;
	private TextView tvHint;
	private boolean isLove = false;

	private NoSlideListView lvComments;

	private static final int PAGE_SIZE = 3;
	private int pageNo = 1;
	private CommentsAdapter commentsAdapter;

	private EditText etMessage;

	private TextView tvMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_detail);
		context = ActivityDetailActivity.this;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		activityId = bundle.getInt("activityId");
		category = bundle.getInt("category");

		initView();

		ActivitiesLogic.getInstance().addObserver(this);
		ActivitiesLogic.getInstance().onGetActivity(0, 0, 0, activityId);

		CommentsLogic.getInstance().addObserver(this);

		MyFavoritesLogic.getInstance().addObserver(this);

		HaloToast
				.showNewWaitDialog(context, false, getString(R.string.loading));
	}

	@Override
	protected void onResume() {
		super.onResume();
		CommentsLogic.getInstance().addObserver(this);
		CommentsLogic.getInstance().onGetReplay(activityId, pageNo, PAGE_SIZE);
	}

	protected void onPause() {
		super.onPause();
		CommentsLogic.getInstance().removeObserver(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CommentsLogic.getInstance().removeObserver(this);
		ActivitiesLogic.getInstance().removeObserver(this);
		MyFavoritesLogic.getInstance().removeObserver(this);
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		etMessage = (EditText) findViewById(R.id.et_feedback);

		tvMore = (TextView) findViewById(R.id.btn_activitydetail_reply);

		rlLeft.setOnClickListener(this);

		if (category == 100) {
			tvTitle.setText(getString(R.string.activities_baby).toUpperCase());

		} else if (category == 101) {
			tvTitle.setText(getString(R.string.activities_education).toUpperCase());

		} else if (category == 102) {
			tvTitle.setText(getString(R.string.activities_resale).toUpperCase());

		} else if (category == 103) {
			tvTitle.setText(getString(R.string.activities_exercuse).toUpperCase());

		} else if (category == 104) {
			tvTitle.setText(getString(R.string.myfavorites_title).toUpperCase());

		} else if (category == 105) {
			tvTitle.setText(getString(R.string.myactivities_title).toUpperCase());

		} else {

			tvTitle.setText(getString(R.string.activities_title).toUpperCase());
		}

		tvActivityTitle = (TextView) findViewById(R.id.tv_activitydetail_title);
		tvTime = (TextView) findViewById(R.id.tv_activitydetail_time);
		tvNum = (TextView) findViewById(R.id.tv_activitydetail_num);
		tvComments = (TextView) findViewById(R.id.tv_activitydetail_reply);
		tvFavor = (TextView) findViewById(R.id.tv_activitydetail_love);
		tvContent = (TextView) findViewById(R.id.tv_activitydetail_content);
		ivActivity = (ImageView) findViewById(R.id.iv_activitydetail);
		ivHeart = (ImageView) findViewById(R.id.iv_activitydetail_heart);
		scActivity = (NoAutoScrollView) findViewById(R.id.sc_content);
		tvHint = (TextView) findViewById(R.id.tv_activity_detail_hint);

		lvComments = (NoSlideListView) findViewById(R.id.lv_comments);
		commentsAdapter = new CommentsAdapter(context);
		lvComments.setAdapter(commentsAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.btn_feedback:
			String message = etMessage.getText().toString();
			if (TextUtils.isEmpty(message)) {
				Toast.makeText(context, getString(R.string.comment_is_null),
						Toast.LENGTH_SHORT).show();
				return;
			}
			CommentsLogic.getInstance().onReply(message, activityId);
			ReplayItem item = new ReplayItem();
			item.setContent(message);
			UserInfoItem userInfoItem = PropertyApplication.mUserCache
					.getUserInfo().getData();
			item.setUserPic(userInfoItem.getPicUrl());
			item.setUserName(userInfoItem.getUserName());

			ArrayList<ReplayItem> iems = new ArrayList<ReplayItem>();
			iems.add(item);

			commentsAdapter.add(iems);
			etMessage.setText("");
			lvComments.setSelection(commentsAdapter.getCount() - 1);
			tvMore.setVisibility(View.VISIBLE);

			break;

		case R.id.btn_activitydetail_reply:
			Intent intent = new Intent(context, CommentsActivity.class);
			intent.putExtra("id", activityId);
			startActivity(intent);
			break;

		case R.id.iv_activitydetail_heart:
			ArrayList<Integer> orgids = new ArrayList<Integer>();
			orgids.add(activityId);
			if (isLove) {

				MyFavoritesLogic.getInstance().onAddFavorite(orgids, 2);
			} else {
				MyFavoritesLogic.getInstance().onAddFavorite(orgids, 1);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityListSuccess(ActivityResp activityResp) {
		HaloToast.dismissWaitDialog();
		if (activityResp.getData().getBusActivity() == null) {
			scActivity.setVisibility(View.GONE);
			tvHint.setVisibility(View.VISIBLE);
			tvHint.setText(getString(R.string.detail_fail));
			return;
		}
		scActivity.setVisibility(View.VISIBLE);
		BusActivity item = activityResp.getData().getBusActivity();
		tvActivityTitle.setText(item.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date(item.getCreateTime());
		tvTime.setText(sdf.format(date));
		tvContent.setText(item.getContent());
		tvNum.setText(item.getClickCnt() + "");
		tvComments.setText(item.getDiscussCnt() + "");
		tvFavor.setText(item.getGoodCnt() + "");
		if (item.getFavorite() == 1) {
			ivHeart.setImageResource(R.drawable.bg_item_love_checked);
			isLove = true;
		} else {
			ivHeart.setImageResource(R.drawable.bg_item_love);
			isLove = false;
		}

		CLog.e("activity", String.valueOf(item.getIconPicUrl() == null)+"---"+item.getIconPicUrl());
		
		if (TextUtils.isEmpty(item.getIconPicUrl())) {
			ivActivity.setVisibility(View.GONE);
		} else {
			ivActivity.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(item.getIconPicUrl(),
					ivActivity);
		}

	}

	@Override
	public void onActivityListFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		scActivity.setVisibility(View.GONE);
		tvHint.setVisibility(View.VISIBLE);
		tvHint.setText(getString(R.string.detail_fail));
	}

	@Override
	public void onGetMyFavoritesSuccess(ActivityResp activityResp) {

	}

	@Override
	public void onGetMyFavoritesFail(int resultCode, String message) {

	}

	@Override
	public void onAddFavoriteResult(int resultCode, String message) {
		if (resultCode == 0) {
			if (isLove) {
				ivHeart.setImageResource(R.drawable.bg_item_love);
				tvFavor.setText(String.valueOf(Integer.parseInt(tvFavor
						.getText().toString()) - 1));
				isLove = false;
			} else {
				ivHeart.setImageResource(R.drawable.bg_item_love_checked);
				tvFavor.setText(String.valueOf(Integer.parseInt(tvFavor
						.getText().toString()) + 1));
				isLove = true;

			}
		}
	}

	@Override
	public void onGetReplySuccess(ReplyEntity replyEntity) {
		if ((replyEntity == null || replyEntity.getData() == null || replyEntity
				.getData().getLsitActivityReply() == null)) {
			return;
		}

		commentsAdapter.refresh(replyEntity.getData().getLsitActivityReply());
		if (replyEntity.getData().getLsitActivityReply().size() > 0) {
			tvMore.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onGetReplyFail(int resultCode, String message) {
		toast(message);
	}

	@Override
	public void onReplyResult(int resultCode, String message) {

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {

			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();

			if (isShouldHideInput(v, ev)) {
				hideSoft();
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	void hideSoft() {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

}

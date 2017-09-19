package com.xflyme.go4.activities;

import java.util.ArrayList;
import java.util.List;

import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceBaseAdapter;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.activity.BusActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFavoritesAdapter extends MultiChoiceBaseAdapter {

	private List<BusActivity> activitys;
	private Context context;
	private boolean isEditMode = false;

	public MyFavoritesAdapter(Bundle savedInstanceState, Context context, ArrayList<BusActivity> activities) {
		super(savedInstanceState);
		this.activitys = activities;
		this.context = context;
	}

	@Override
	public int getCount() {
		return activitys.size();
	}

	@Override
	public BusActivity getItem(int position) {
		return activitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		private TextView tvTitle;
		private TextView tvDesc;
		private TextView tvTime;
		private TextView tvView;
		private TextView tvReply;
		private TextView tvLove;
		private CheckBox checkBox;
		private ImageView ivArrow;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	protected View getViewImpl(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_favorites, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_activity_title);
			viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_item_activity_desc);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_activity_time);
			viewHolder.tvView = (TextView) convertView.findViewById(R.id.tv_item_activity_view);
			viewHolder.tvReply = (TextView) convertView.findViewById(R.id.tv_item_activity_reply);
			viewHolder.tvLove = (TextView) convertView.findViewById(R.id.tv_item_activity_love);
			viewHolder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_item_activity_right);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(android.R.id.checkbox);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BusActivity activity = activitys.get(position);

		if (isEditMode) {
			viewHolder.ivArrow.setVisibility(View.GONE);
			viewHolder.checkBox.setVisibility(View.VISIBLE);
		} else {
			viewHolder.ivArrow.setVisibility(View.VISIBLE);
			viewHolder.checkBox.setVisibility(View.GONE);

		}

		viewHolder.tvTitle.setText(activity.getName());
		viewHolder.tvDesc.setText(activity.getContent());
		viewHolder.tvTime.setText(activity.getStartTimeStr());
		viewHolder.tvView.setText("" + activity.getClickCnt());
		viewHolder.tvReply.setText("" + activity.getDiscussCnt());
		viewHolder.tvLove.setText("" + activity.getGoodCnt());

		return convertView;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

}

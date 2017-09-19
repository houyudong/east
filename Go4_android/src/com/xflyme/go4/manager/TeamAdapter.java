package com.xflyme.go4.manager;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.TeamMemItem;
import com.xflyme.go4.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TeamAdapter extends BaseAdapter {

	private ArrayList<TeamMemItem> teamMemItems;
	private Context context;

	public TeamAdapter(Context context) {
		teamMemItems = new ArrayList<TeamMemItem>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return teamMemItems.size();
	}

	public void add(ArrayList<TeamMemItem> employeeEntities) {
		this.teamMemItems.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	public void refresh(ArrayList<TeamMemItem> employeeEntities) {
		this.teamMemItems.clear();
		this.teamMemItems.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return teamMemItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_team, null);
			viewHolder = new ViewHolder();
			viewHolder.ivHead = (CircleImageView) convertView
					.findViewById(R.id.iv_item_team_photo);
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_item_team_name);
			viewHolder.tvWorkTime = (TextView) convertView
					.findViewById(R.id.tv_item_team_worktime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		TeamMemItem teamMemItem = teamMemItems.get(position);

		ImageLoader.getInstance().displayImage(teamMemItem.getPicUrl(),
				viewHolder.ivHead);
		viewHolder.tvName.setText(teamMemItem.getUserName() + " - "
				+ teamMemItem.getJobType());
		viewHolder.tvWorkTime.setText(teamMemItem.getJobTime());

		return convertView;
	}

	class ViewHolder {
		public CircleImageView ivHead;
		public TextView tvName;
		public TextView tvWorkTime;
	}

}

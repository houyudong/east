package com.xflyme.go4.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.activity.BusActivity;
import com.xflyme.go4.util.CLog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAdapter extends BaseAdapter {

	private List<BusActivity> activitys;
	private Context context;

	public ActivityAdapter(Context context) {
		activitys = new ArrayList<BusActivity>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return activitys.size();
	}

	public void add(List<BusActivity> employeeEntities) {
		this.activitys.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	public void refresh(List<BusActivity> employeeEntities) {
		this.activitys.clear();
		this.activitys.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	@Override
	public BusActivity getItem(int position) {
		return activitys.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_activity, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_activity_title);
			viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_item_activity_desc);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_activity_time);
			viewHolder.tvView = (TextView) convertView.findViewById(R.id.tv_item_activity_view);
			viewHolder.tvReply = (TextView) convertView.findViewById(R.id.tv_item_activity_reply);
			viewHolder.tvLove = (TextView) convertView.findViewById(R.id.tv_item_activity_love);
			viewHolder.ivLove = (ImageView) convertView.findViewById(R.id.imageView4);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BusActivity activity = activitys.get(position);

		viewHolder.tvTitle.setText(activity.getName());
		viewHolder.tvDesc.setText(activity.getContent());

		Date date = new Date(activity.getStartTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		viewHolder.tvTime.setText(dateFormat.format(date));

		CLog.e("time", activity.getStartTimeStr() + "-" + activity.getStartTime());

		viewHolder.tvView.setText("" + activity.getClickCnt());
		viewHolder.tvReply.setText("" + activity.getDiscussCnt());
		viewHolder.tvLove.setText("" + activity.getGoodCnt());

		if (activity.getFavorite() == 1) {
			viewHolder.ivLove.setImageResource(R.drawable.bg_item_love);
		} else {
			viewHolder.ivLove.setImageResource(R.drawable.bg_item_love);
		}

		return convertView;
	}

	class ViewHolder {
		private TextView tvTitle;
		private TextView tvDesc;
		private TextView tvTime;
		private TextView tvView;
		private TextView tvReply;
		private TextView tvLove;
		private ImageView ivLove;
	}

}

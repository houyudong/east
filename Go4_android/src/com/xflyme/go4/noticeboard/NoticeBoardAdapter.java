package com.xflyme.go4.noticeboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.NoticeBoardItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeBoardAdapter extends BaseAdapter {

	private ArrayList<NoticeBoardItem> items;
	private Context context;

	public NoticeBoardAdapter(Context context) {
		items = new ArrayList<NoticeBoardItem>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void add(ArrayList<NoticeBoardItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void refresh(ArrayList<NoticeBoardItem> items) {
		this.items.clear();
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
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
					R.layout.item_noticeboard, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_item_noticeboard_title);
			viewHolder.tvDesc = (TextView) convertView
					.findViewById(R.id.tv_item_noticeboard_desc);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_item_noticeboard_time);
			viewHolder.tvNum = (TextView) convertView
					.findViewById(R.id.tv_item_noticeboard_num);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		NoticeBoardItem noticeBoardEntity = items.get(position);

		viewHolder.tvTitle.setText(noticeBoardEntity.getTitle());
		viewHolder.tvDesc.setText(noticeBoardEntity.getTitle());
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     Date date= new Date(Long.parseLong(noticeBoardEntity.getStartTime()));
		
		viewHolder.tvTime.setText(sdf.format(date));
		viewHolder.tvNum.setText(noticeBoardEntity.getClickCnt()+"");

		return convertView;
	}

	class ViewHolder {
		public TextView tvTitle;
		public TextView tvDesc;
		public TextView tvTime;
		public TextView tvNum;
	}

}

package com.xflyme.go4.activities;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.ReplayItem;
import com.xflyme.go4.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {

	private List<ReplayItem> items;
	private Context context;

	public CommentsAdapter(Context context) {
		this.context = context;
		items = new ArrayList<ReplayItem>();
	}

	public void refresh(ArrayList<ReplayItem> items) {
		this.items.clear();
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void add(ArrayList<ReplayItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ReplayItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		private TextView tvName;
		private TextView tvContent;
		private CircleImageView ivHead;
		private ImageView ivHeart;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_comments, null);
			viewHolder = new ViewHolder();

			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_comment);
			viewHolder.ivHead = (CircleImageView) convertView.findViewById(R.id.iv_item_comments_photo);
			viewHolder.ivHeart = (ImageView) convertView.findViewById(R.id.iv_heart);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ReplayItem item = items.get(position);

		viewHolder.tvContent.setText(item.getContent());
		viewHolder.tvName.setText(item.getUserName());

		ImageLoader.getInstance().displayImage(item.getUserPic(), viewHolder.ivHead);

		return convertView;
	}

}

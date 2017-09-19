package com.xflyme.go4.activity;

import java.util.ArrayList;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.Relation;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectPropertyAdapter extends BaseAdapter {

	private ArrayList<Relation> relations;
	private Context context;

	public SelectPropertyAdapter(Context context,ArrayList<Relation> relations) {
		this.relations = relations;
		this.context = context;
	}

	@Override
	public int getCount() {
		return relations.size();
	}

	@Override
	public Object getItem(int position) {
		return relations.get(position);
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
					R.layout.item_select_property, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_item_select_property);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Relation relation = relations.get(position);

		viewHolder.tvTitle.setText(relation.getCommunityName()+"#"+relation.getUnitNo());
		
		return convertView;
	}

	class ViewHolder {

		public TextView tvTitle;
		public ImageView ivImageView;

	}

}

package com.xflyme.go4.bookfacility;

import java.util.ArrayList;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.AppBookFacilityEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookFacilityAdapter extends BaseExpandableListAdapter {

	private ArrayList<AppBookFacilityEntity> faciLities;
	private Context context;

	public BookFacilityAdapter(Context context) {
		this.context = context;
		faciLities = new ArrayList<AppBookFacilityEntity>();
	}

	void refresh(ArrayList<AppBookFacilityEntity> facilityEntities) {
		this.faciLities.clear();
		this.faciLities.addAll(facilityEntities);
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return faciLities.get(groupPosition).getItems().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		ChildViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_bookfacility_child, null);
			viewHolder = new ChildViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_bookfacility_site);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}

		viewHolder.tvName.setText(faciLities.get(groupPosition).getItems().get(childPosition).getName());

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return faciLities.get(groupPosition).getItems().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return faciLities.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return faciLities.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		GroupViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_bookfacility_group, null);

			viewHolder = new GroupViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_bookfacility_title);
			viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_item_bookfacility_logo);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (GroupViewHolder) convertView.getTag();
		}

		AppBookFacilityEntity facilityEntity = faciLities.get(groupPosition);

		viewHolder.tvName.setText(facilityEntity.getTitle());

		if (facilityEntity.getItems().get(0).getType() == 0) {
			viewHolder.ivLogo.setImageResource(R.drawable.tennis);
		} else if (facilityEntity.getItems().get(0).getType() == 1) {
			viewHolder.ivLogo.setImageResource(R.drawable.bbq);
		} else if (facilityEntity.getItems().get(0).getType() == 2) {
			viewHolder.ivLogo.setImageResource(R.drawable.ktv);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class GroupViewHolder {
		TextView tvName;
		ImageView ivLogo;
	}

	class ChildViewHolder {
		TextView tvName;
	}

}

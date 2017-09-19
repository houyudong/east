package com.xflyme.go4.bookfacility;

import java.util.ArrayList;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.SiteDetailEntity.SiteDetailDayItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridHourAdapter extends BaseAdapter {
	private Context context;
	private OnHourClickListener onHourClickListener;

	public OnHourClickListener getOnHourClickListener() {
		return onHourClickListener;
	}

	public void setOnHourClickListener(OnHourClickListener onHourClickListener) {
		this.onHourClickListener = onHourClickListener;
	}

	private ArrayList<SiteDetailDayItem> items;

	public GridHourAdapter(Context context) {
		this.context = context;
		items = new ArrayList<SiteDetailDayItem>();
	}

	public void refresh(ArrayList<SiteDetailDayItem> items) {
		this.items.clear();
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_hour, null);
			viewHolder = new ViewHolder();
			viewHolder.tvHour = (TextView) convertView.findViewById(R.id.tv_item_hour_hour);
			viewHolder.tvHour.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onHourClickListener.onHourClick(position);
				}
			});
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SiteDetailDayItem item = items.get(position);

		if (item.getIsCancel() == 1) {
			item.setSelected(true);
		}

		if (item.getIsOrder() == 2 && item.getIsCancel() == 2) {
			viewHolder.tvHour.setEnabled(true);
			viewHolder.tvHour.setBackgroundResource(R.drawable.selector_hour_gridview);
			viewHolder.tvHour.setTextColor(context.getResources().getColor(R.color.hour_text_color));
		} else {
			viewHolder.tvHour.setEnabled(false);
			viewHolder.tvHour.setBackgroundResource(R.drawable.background_hour_notenable);
			viewHolder.tvHour.setTextColor(context.getResources().getColor(R.color.hour_not_enable));
		}
		viewHolder.tvHour.setText(item.getDayTimeDesc());
		viewHolder.tvHour.setSelected(true);

		if (item.isSelected()) {
			viewHolder.tvHour.setSelected(true);
		} else {
			viewHolder.tvHour.setSelected(false);
		}

		return convertView;
	}

	class ViewHolder {
		TextView tvHour;
	}

}

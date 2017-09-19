package com.xflyme.go4.bookfacility;

import java.util.ArrayList;
import com.xflyme.go4.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridTitleAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Day> days;

	public GridTitleAdapter(Context context) {
		this.context = context;
		days = new ArrayList<Day>();
		for (int i = 0; i < 7; i++) {
			days.add(new Day(context, 0, 0, 0));
		}

	}

	public void refresh(ArrayList<Day> days) {
		this.days.clear();
		this.days.addAll(days);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Object getItem(int position) {
		return days.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderTitle viewHolderTitle = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.day_of_week, null);
			viewHolderTitle = new ViewHolderTitle();
			viewHolderTitle.tvTitle = (TextView) convertView
					.findViewById(R.id.textView1);
			convertView.setTag(viewHolderTitle);
		} else {
			viewHolderTitle = (ViewHolderTitle) convertView.getTag();
		}

		if (position == 0) {
			viewHolderTitle.tvTitle.setText(R.string.sunday);
		} else if (position == 1) {
			viewHolderTitle.tvTitle.setText(R.string.monday);
		} else if (position == 2) {
			viewHolderTitle.tvTitle.setText(R.string.tuesday);
		} else if (position == 3) {
			viewHolderTitle.tvTitle.setText(R.string.wednesday);
		} else if (position == 4) {
			viewHolderTitle.tvTitle.setText(R.string.thursday);
		} else if (position == 5) {
			viewHolderTitle.tvTitle.setText(R.string.friday);
		} else if (position == 6) {
			viewHolderTitle.tvTitle.setText(R.string.saturday);
		}

		return convertView;
	}

	class ViewHolderTitle {
		TextView tvTitle;
	}

	class ViewHolderDay {
		TextView tvDay;
		ImageView ivDot;
		View frame;
	}

}

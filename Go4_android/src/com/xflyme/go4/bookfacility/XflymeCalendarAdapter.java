package com.xflyme.go4.bookfacility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.xflyme.go4.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XflymeCalendarAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Day> days;

	public XflymeCalendarAdapter(Context context) {
		this.context = context;
		days = new ArrayList<Day>();

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderDay viewHolderDay = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.day_view, null);
			viewHolderDay = new ViewHolderDay();
			viewHolderDay.tvDay = (TextView) convertView
					.findViewById(R.id.tv_day);
			viewHolderDay.ivDot = (ImageView) convertView
					.findViewById(R.id.imageView6);
			viewHolderDay.frame = convertView.findViewById(R.id.today_frame);
			convertView.setTag(viewHolderDay);

		} else {
			viewHolderDay = (ViewHolderDay) convertView.getTag();
		}

		Calendar cal = Calendar.getInstance(TimeZone.getDefault(),
				Locale.getDefault());
		Day day = days.get(position);
		if (day.getYear() == cal.get(Calendar.YEAR)
				&& day.getMonth() - 1 == cal.get(Calendar.MONTH)
				&& day.getDay() == cal.get(Calendar.DAY_OF_MONTH)) {
			viewHolderDay.frame.setVisibility(View.VISIBLE);
		} else {
			viewHolderDay.frame.setVisibility(View.GONE);
		}

		if (day.isFull()) {
			viewHolderDay.ivDot.setVisibility(View.VISIBLE);
		} else {
			viewHolderDay.ivDot.setVisibility(View.INVISIBLE);

		}

		if (day.getDay() == 0) {
			viewHolderDay.tvDay.setVisibility(View.INVISIBLE);
		} else {
			viewHolderDay.tvDay.setVisibility(View.VISIBLE);
			viewHolderDay.tvDay.setText(String.valueOf(day.getDay()));
		}

		viewHolderDay.tvDay.setSelected(false);

		return convertView;
	}

	class ViewHolderDay {
		TextView tvDay;
		ImageView ivDot;
		View frame;
	}

}

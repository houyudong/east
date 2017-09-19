package com.xflyme.go4.booking;

import java.util.ArrayList;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.BookFacilityItem;
import com.xflyme.go4.util.CLog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyBookingAdapter extends BaseAdapter {

	private ArrayList<BookFacilityItem> bookings;
	private Context context;

	public MyBookingAdapter(Context context) {
		bookings = new ArrayList<BookFacilityItem>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return bookings.size();
	}

	public void add(ArrayList<BookFacilityItem> bookings) {
		this.bookings.addAll(bookings);
		notifyDataSetChanged();
	}

	public void refresh(ArrayList<BookFacilityItem> bookings) {
		this.bookings.clear();
		this.bookings.addAll(bookings);
		CLog.e("adapter", bookings.size() + "");
		notifyDataSetChanged();
	}

	@Override
	public BookFacilityItem getItem(int position) {
		return bookings.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_booking, null);
			viewHolder = new ViewHolder();

			viewHolder.tvProject = (TextView) convertView.findViewById(R.id.tv_item_booking_title);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_item_booking_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BookFacilityItem bookingEntity = bookings.get(position);

		viewHolder.tvProject.setText(bookingEntity.getSiteName());
		viewHolder.tvTime.setText(bookingEntity.getOrderDate() + "/" + bookingEntity.getOrderTimeDesc());

		return convertView;
	}

	class ViewHolder {
		public TextView tvProject;
		public TextView tvTime;
	}

}

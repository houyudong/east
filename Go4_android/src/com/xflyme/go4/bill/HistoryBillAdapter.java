package com.xflyme.go4.bill;

import java.util.ArrayList;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.BillEntity.BillItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryBillAdapter extends BaseAdapter {

	private ArrayList<BillItem> billItems;
	private Context context;

	public HistoryBillAdapter(Context context) {
		billItems = new ArrayList<BillItem>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return billItems.size();
	}

	public void add(ArrayList<BillItem> employeeEntities) {
		this.billItems.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	public void refresh(ArrayList<BillItem> employeeEntities) {
		this.billItems.clear();
		this.billItems.addAll(employeeEntities);
		notifyDataSetChanged();
	}

	@Override
	public BillItem getItem(int position) {
		return billItems.get(position);
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
					R.layout.item_bill_history, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_item_bill_title);
			viewHolder.tvTotalMoney = (TextView) convertView
					.findViewById(R.id.tv_item_bill_money);
			viewHolder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_item_bill_address);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tv_item_bill_date);
			viewHolder.tvMainTitle = (TextView) convertView
					.findViewById(R.id.tv_item_bill_main_title);
			viewHolder.tvMainMoney = (TextView) convertView
					.findViewById(R.id.tv_item_bill_main_price);
			viewHolder.tvSinkTitle = (TextView) convertView
					.findViewById(R.id.tv_item_bill_sink_title);
			viewHolder.tvSinkMoney = (TextView) convertView
					.findViewById(R.id.tv_item_bill_sink_price);
			viewHolder.tvGstTitle = (TextView) convertView
					.findViewById(R.id.tv_item_bill_gst_title);
			viewHolder.tvGstMoney = (TextView) convertView
					.findViewById(R.id.tv_item_bill_gst_price);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

BillItem billItem = billItems.get(position);
		
		String feeName = billItem.getType() == 1 ? context.getString(R.string.fee_maintenance) : context.getString(R.string.fee_parking);

		viewHolder.tvTitle.setText(feeName+"("+billItem.getMaintanceMonth()+")");
		viewHolder.tvAddress.setText(billItem.getCommunityName()+billItem.getHousesUnitNo());
		viewHolder.tvDate.setText(billItem.getMaintanceMonth()+"");
		viewHolder.tvGstTitle.setText(context.getString(R.string.gst_title)+billItem.getTaxRate()+"%");
		viewHolder.tvTotalMoney.setText(String.valueOf(billItem.getTotalFund()));
		viewHolder.tvMainMoney
				.setText(String.valueOf(billItem.getMaintanceFund()));
		viewHolder.tvSinkMoney
				.setText(String.valueOf(billItem.getSinkingFund()));
		viewHolder.tvGstMoney.setText(String.valueOf(billItem.getTax()));

		return convertView;
	}

	class ViewHolder {

		public TextView tvTitle;
		public TextView tvTotalMoney;
		public TextView tvAddress;
		public TextView tvDate;
		public TextView tvMainTitle;
		public TextView tvMainMoney;
		public TextView tvSinkTitle;
		public TextView tvSinkMoney;
		public TextView tvGstTitle;
		public TextView tvGstMoney;

	}

}

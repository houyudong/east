package com.xflyme.go4.noticeboard;

import java.util.ArrayList;

import com.xflyme.go4.entity.VoteItem;
import com.xflyme.go4.view.SingleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VoteAdapter extends BaseAdapter {

	private ArrayList<VoteItem> items;
	private Context context;

	public VoteAdapter(Context context) {
		items = new ArrayList<VoteItem>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void add(ArrayList<VoteItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void refresh(ArrayList<VoteItem> items) {
		this.items.clear();
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public VoteItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SingleView singleView = new SingleView(context);  
        singleView.setTitle( items.get(position).getChoice());  
         return singleView;  
	}

	class ViewHolder {
		public TextView tvText;
	}

}

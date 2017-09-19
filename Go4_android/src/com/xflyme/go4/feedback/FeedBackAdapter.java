package com.xflyme.go4.feedback;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.FeedBackItem;
import com.xflyme.go4.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class FeedBackAdapter extends BaseAdapter {

	private final int TYPE_LEFT = 1;
	private final int TYPE_RIGHT = 0;

	private Context context;
	private ArrayList<FeedBackItem> items;

	public FeedBackAdapter(Context context) {
		this.context = context;
		items = new ArrayList<FeedBackItem>();
	}

	public void add(FeedBackItem item) {
		this.items.add(item);
		notifyDataSetInvalidated();
	}

	public void refresh(ArrayList<FeedBackItem> items) {
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
	public int getItemViewType(int position) {

		return items.get(position).getOperatorType() == 1 ? TYPE_RIGHT
				: TYPE_LEFT;

	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderLeft viewHolderLeft = null;
		ViewHolderRight viewHolderRight = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_LEFT:
				convertView = LayoutInflater.from(context).inflate(R.layout.item_feedback_left, null);
				viewHolderLeft = new ViewHolderLeft();
				viewHolderLeft.tvContent = (TextView) convertView.findViewById(R.id.tv_item_feedback);
				viewHolderLeft.ivHead = (CircleImageView) convertView.findViewById(R.id.iv_item_feedback);
				convertView.setTag(viewHolderLeft);
				break;

			case TYPE_RIGHT:
				convertView = LayoutInflater.from(context).inflate(R.layout.item_feedback_righ, null);
				viewHolderRight = new ViewHolderRight();
				viewHolderRight.tvContent = (TextView) convertView.findViewById(R.id.tv_item_feedback);
				viewHolderRight.ivHead = (CircleImageView) convertView.findViewById(R.id.iv_item_feedback);
				convertView.setTag(viewHolderRight);
				
				break;
				
			}
		}else {
			switch (type) {
			case TYPE_LEFT:
				viewHolderLeft = (ViewHolderLeft) convertView.getTag();
				break;

			case TYPE_RIGHT:
				viewHolderRight = (ViewHolderRight) convertView.getTag();
				break;
				
			}
		}
		
		switch (type) {
		case TYPE_LEFT:
			viewHolderLeft.tvContent.setText(items.get(position).getContent());
			ImageLoader.getInstance().displayImage(items.get(position).getUserPicUrl(), viewHolderLeft.ivHead);
			break;

		case TYPE_RIGHT:
			viewHolderRight.tvContent.setText(items.get(position).getContent());
			ImageLoader.getInstance().displayImage(PropertyApplication.mUserCache.getUserInfo().getData().getPicUrl(), viewHolderRight.ivHead);
			break;
			
		}
		
		return convertView;
	}

	class ViewHolderLeft {
		TextView tvContent;
		CircleImageView ivHead;
	}

	class ViewHolderRight {
		TextView tvContent;
		CircleImageView ivHead;
	}

}

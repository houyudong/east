package com.xflyme.go4.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.R;
import com.xflyme.go4.activities.ActivityDetailActivity;
import com.xflyme.go4.entity.HomePageEntity.HomePageItem;
import com.xflyme.go4.noticeboard.NoticeBoardDetailActivity;

public class ImagePagerAdapter extends PagerAdapter implements OnClickListener {
	private Context mContext;
	private ArrayList<HomePageItem> headList;

	public ImagePagerAdapter(Context context, ArrayList<HomePageItem> headList) {
		this.mContext = context;
		this.headList = headList;
	}

	public void refresh(ArrayList<HomePageItem> newList) {
		if (getSize() != 0) {
			headList.clear();
		}
		headList.addAll(newList);
		this.notifyDataSetChanged();
	}

	public HomePageItem getItem(int position) {
		int size = getSize();
		if (size == 0) {
			return null;
		} else {
			return headList.get(position % getSize());
		}
	}

	public int getSize() {
		return headList.size();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		ViewPager viewPager = (ViewPager) container;
		ImageView imageView = null;
		HomePageItem itemData = getItem(position);
		if (itemData != null) {
			imageView = new ImageView(mContext);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageResource(R.drawable.ic_launcher);
			ImageLoader.getInstance().displayImage(itemData.getPhoto(), imageView);
			imageView.setTag(itemData);
			imageView.setOnClickListener(this);
			viewPager.addView(imageView);
		}
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void onClick(View v) {
		HomePageItem itemData = (HomePageItem) v.getTag();
		if (itemData.getTargetType() == 1) {// 标志活动

			Intent intent = new Intent(mContext, ActivityDetailActivity.class);

			Bundle bundle = new Bundle();
			bundle.putInt("activityId", Integer.parseInt(itemData.getTargetId()));
			intent.putExtras(bundle);

			mContext.startActivity(intent);
		} else if (itemData.getTargetType() == 2) {// 公告
			Intent intent = new Intent(mContext, NoticeBoardDetailActivity.class);
			intent.putExtra("activityId", Integer.parseInt(itemData.getTargetId()));
			mContext.startActivity(intent);
		}
	}
}

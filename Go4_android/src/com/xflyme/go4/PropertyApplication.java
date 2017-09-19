package com.xflyme.go4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.xutil.ThreadUtil;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xflyme.go4.cache.UserCache;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.util.CLog.LogPriority;

public class PropertyApplication extends Application {

	public static UserCache mUserCache = null;
	// public static APPCache mAPPCache = null;
	private static Context mContext;

	public static Context getContext() {
		return mContext;
	}

	/**
	 * 记录所有打开的activity，在退出中关闭
	 */
	private List<Activity> mainActivity = new ArrayList<Activity>();

	public List<Activity> MainActivity() {
		return mainActivity;
	}

	public void addActivity(Activity act) {
		mainActivity.add(act);
	}

	public void finishAll() {
		for (Activity act : mainActivity) {
			if (!act.isFinishing()) {
				act.finish();
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = PropertyApplication.this;
		CLog.setLogable(PropertyConstant.IS_DEVELOPMODE);
		CLog.setLogLevel(LogPriority.VERBOSE);

		ThreadUtil.init(3, 2);

		mUserCache = UserCache.getInstance();
		// mAPPCache = APPCache.getInstance();

		initImageLoader();
	}

	/**
	 * 全局初始化ImageLoader
	 */
	private void initImageLoader() {
		File cacheDir = new File(PropertyConstant.FILE_PATH + "images");
		DisplayImageOptions defaultOption = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.load_before_small).showImageForEmptyUri(R.drawable.load_before_small)
				.showImageOnFail(R.drawable.load_before_small).bitmapConfig(Config.RGB_565)
				.resetViewBeforeLoading(false).cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mContext)
				.denyCacheImageMultipleSizesInMemory().threadPoolSize(3)
				.memoryCache(new LruMemoryCache(3 * 1024 * 1024))
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).memoryCacheSize(3 * 1024 * 1024)
				.memoryCacheSizePercentage(13).diskCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()).defaultDisplayImageOptions(defaultOption)
				.imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)).build();
		ImageLoader.getInstance().init(configuration);
	}

}

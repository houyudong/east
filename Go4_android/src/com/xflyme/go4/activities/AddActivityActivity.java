package com.xflyme.go4.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.xutil.task.ForeTask;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.util.CLog;
import com.xflyme.go4.view.CircleImageView;
import com.xflyme.go4.view.HaloToast;
import com.xflyme.go4.view.ListViewPicker;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class AddActivityActivity extends BaseActivity implements
		OnClickListener ,AddActivityObserver {
	
	private final int SELECT_PIC_BY_TACK_PHOTO = 1111;
	private final int SELECT_PIC_BY_PICK_PHOTO = 2222;
	
	private File tempFile;

	private Context context;
	private RelativeLayout rlCategory;
	private EditText etTitle;
	private EditText etContent;
	private ImageView imageView;
	private TextView tvCategory;
	private CircleImageView ivHead;
	private String[] categories = new String[] { "Baby", "Education","Resale","Exercuse" };
	private int[] categoryIds = new int[] { 100, 101,102,103 };
	private int position = -1;
	private Uri uri;
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_activity);

		context = AddActivityActivity.this;
		AddActivityLogic.getInstance().addObserver(this);
		initView();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AddActivityLogic.getInstance().removeObserver(this);
	}

	void initView() {
		rlCategory = (RelativeLayout) findViewById(R.id.rl_add_category);
		tvCategory = (TextView) findViewById(R.id.tv_category);
		ivHead = (CircleImageView) findViewById(R.id.iv_head);
		etTitle = (EditText) findViewById(R.id.et_title);
		etContent = (EditText) findViewById(R.id.et_content);
		imageView = (ImageView) findViewById(R.id.iv_image);
		
		UserInfoEntity userInfo = PropertyApplication.mUserCache.getUserInfo();
		ImageLoader.getInstance().displayImage(userInfo.getData().getPicUrl(), ivHead);
		
		String photoName = getPhotoFileName();
		tempFile = new File(PropertyConstant.FILE_PATH, photoName);
	}
	
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {

			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();

			if (isShouldHideInput(v, ev)) {
				hideSoft();
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	void hideSoft() {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			finish();
			break;
			
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.rl_add_category:
			selectCategory();
			break;
			
		case R.id.iv_image:
			selectImage();
			break;
			
		case R.id.btn_title_right:
			if (position == -1) {
				
				toast(getString(R.string.category_is_null));
				return;
			} 
			
			if (TextUtils.isEmpty(etTitle.getText().toString())) {
				
				toast(getString(R.string.title_is_null));
				return;
			} 
			
			if (TextUtils.isEmpty(etContent.getText().toString())) {
				
				toast(getString(R.string.content_is_null));
				return;
			} 
			String path = "";
			if (type == 1) {
				path = tempFile.getPath();
			}else if (type == 2) {
				if (uri != null) {
					path = uriToString();
				}
			}
			
			AddActivityLogic.getInstance().onAddActivity(categoryIds[position], etTitle.getText().toString(), etContent.getText().toString(), new String[]{path});
			HaloToast.showNewWaitDialog(context, false, getString(R.string.loading));
			break;

		default:
			break;
		}
	}
	
	private String uriToString(){

		//查询，返回cursor
		Cursor cursor = getContentResolver().query(uri, null,   
		null, null, null); 
		 
		//第一行第二列保存路径strRingPath
		cursor.moveToFirst();   
		String path = cursor.getString(1);  
		cursor.close();
		return path;
	}
	
	private void selectImage() {
		final ListViewPicker picker = new ListViewPicker(new String[] { "拍一张", "从图库选取" }, this);
		picker.setCancelListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				picker.dismiss();
			}
		});
		picker.setOnSelectListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View listItemView, int position, long id) {
				if (position == 0) {
					takePhoto();
				} else {
					pickPhoto();
				}
				picker.dismiss();
			}
		});
		picker.show();
	}
	
	private void selectCategory() {
		final ListViewPicker picker = new ListViewPicker(categories, this);
		picker.setCancelListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				picker.dismiss();
			}
		});
		picker.setOnSelectListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View listItemView, int position, long id) {
				setCategory(position);
				picker.dismiss();
			}
		});
		picker.show();
	}
	
	void setCategory(int position){
		tvCategory.setText(categories[position]);
		this.position = position;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PIC_BY_TACK_PHOTO:// 当选择拍照时调用
			//setPicToView(data);
			type = 1;
			imageView.setImageURI(Uri.fromFile(tempFile));
			uri = Uri.fromFile(tempFile);
			break;

		case SELECT_PIC_BY_PICK_PHOTO:// 当选择从本地获取图片时
			// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null) {
				type = 2;
				//setPicToView(data);
				imageView.setImageURI(data.getData());
				uri = data.getData();
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	
	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
		} else {
			Toast.makeText(this, R.string.memory_card_not_exist, Toast.LENGTH_LONG).show();
		}
	}

	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	@Override
	public void onAddActivityResult(int errorCode, String msg) {
		HaloToast.dismissWaitDialog();
		if (errorCode == 0) {
			toast("Success");
			finish();
		}else {
			toast("Fail");
			
		}
	}

}

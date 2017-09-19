package com.xflyme.go4.me;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.activity.LoginActivity;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.view.CircleImageView;
import com.xflyme.go4.view.HaloToast;
import com.xflyme.go4.view.ListViewPicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.xutil.task.ForeTask;

/**
 * Edit Me Infos
 *
 * @author ares
 * @date 2016-03-02
 * @Copyright: Copyright (c) 2016 SIGN. All rights reserved
 */
public class MeUpdateActivity extends BaseActivity implements OnClickListener, UserInfoObserver {

	private Context context;

	private RelativeLayout rlLeft;
	private TextView titleCenter;

	private final int SELECT_PIC_BY_TACK_PHOTO = 1111;
	private final int SELECT_PIC_BY_PICK_PHOTO = 2222;
	private final int PHOTO_REQUEST_CUT = 3333;

	private File tempFile;
	private File tempFileCrop;

	private CircleImageView ivHead;
	private EditText etName;
	private EditText etPhone;
	private EditText etMail;
	private TextView etBirthday;

	private int mYear = 1990;
	private int mMonth = 0;
	private int mDay = 1;

	private boolean isRelogin = false;
	String oldemail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_update);

		context = MeUpdateActivity.this;
		UserInfoLogic.getInstance().addObserver(this);
		initView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		UserInfoEntity userInfo = PropertyApplication.mUserCache.getUserInfo();
		etName.setText(userInfo.getData().getUserName());
		etPhone.setText(userInfo.getData().getTelphone());
		etMail.setText(userInfo.getData().getEmail());
		etBirthday.setText(userInfo.getData().getBirthday());

		oldemail = userInfo.getData().getEmail();

		ImageLoader.getInstance().displayImage(userInfo.getData().getPicUrl(), ivHead);

		String birth = userInfo.getData().getBirthday();
		if (!TextUtils.isEmpty(birth)) {
			String[] birthArr = birth.split("-");
			this.mYear = Integer.parseInt(birthArr[0]);
			this.mMonth = Integer.parseInt(birthArr[1]) - 1;
			this.mDay = Integer.parseInt(birthArr[2]);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UserInfoLogic.getInstance().removeObserver(this);
		
		/*UserInfoEntity userInfo = PropertyApplication.mUserCache.getUserInfo();
		etName.setText(userInfo.getData().getUserName());
		etPhone.setText(userInfo.getData().getTelphone());
		etMail.setText(userInfo.getData().getEmail());
		etBirthday.setText(userInfo.getData().getBirthday());

		oldemail = userInfo.getData().getEmail();

		ImageLoader.getInstance().displayImage(userInfo.getData().getPicUrl(), ivHead);

		String birth = userInfo.getData().getBirthday();
		CLog.e("birthday", birth);
		if (!TextUtils.isEmpty(birth)) {
			String[] birthArr = birth.split("-");
			this.mYear = Integer.parseInt(birthArr[0]);
			this.mMonth = Integer.parseInt(birthArr[1]) - 1;
			this.mDay = Integer.parseInt(birthArr[2]);
		}*/
	}

	void initView() {

		ivHead = (CircleImageView) findViewById(R.id.iv_me_head);
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		titleCenter = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);

		titleCenter.setText(getString(R.string.me_title));

		String photoName = getPhotoFileName();
		tempFile = new File(PropertyConstant.FILE_PATH, photoName);
		tempFileCrop = new File(PropertyConstant.FILE_PATH, photoName + "2");

		etName = (EditText) findViewById(R.id.et_me_update_name);
		etPhone = (EditText) findViewById(R.id.et_me_update_phone);
		etMail = (EditText) findViewById(R.id.et_me_update_email);
		etBirthday = (TextView) findViewById(R.id.et_me_update_birthday);

		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_me_info:
			selectUserHead();
			break;

		case R.id.et_me_update_birthday:
			new DatePickerDialog(context, mDateSetListener, mYear, mMonth, mDay).show();
			break;

		case R.id.btn_me_update:
			saveUserInfo();
			break;
			
		case R.id.rl_top_left:
			finish();
			break;

		default:
			break;
		}
	}

	private void saveUserInfo() {

		String name = etName.getText().toString().trim();
		String birth = etBirthday.getText().toString();
		String email = etMail.getText().toString();
		String phone = etPhone.getText().toString();

		String regExEmail = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Matcher matcherEmail = Pattern.compile(regExEmail).matcher(email);
		String regExChina = "[\\u4e00-\\u9fa5]+";
		Matcher matcherEmailChina = Pattern.compile(regExChina).matcher(email);

		if (!TextUtils.isEmpty(email) && (!matcherEmail.matches() || matcherEmailChina.find())) {
			toast(getString(R.string.email_format_err));
			return;
		} else if (TextUtils.isEmpty(name)) {
			toast(getString(R.string.name_is_null));
			return;
		} else if (TextUtils.isEmpty(phone)) {
			toast(getString(R.string.phone_is_null));
			return;
		}

		if (!oldemail.equals(email)) {
			isRelogin = true;
		}

		UserInfoLogic.getInstance().updateUserInfo(birth, phone, email, name, new String[] { tempFileCrop.getPath() });
		HaloToast.showNewWaitDialog(context, false, "请稍候...");
	}

	/**
	 * 
	 * 日期控件的事件
	 */

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Date select = new Date(year, monthOfYear, dayOfMonth);
			Date now = new Date();
			now.setYear(now.getYear() + 1900);
			if (select.getTime() > now.getTime()) {
				Toast.makeText(context, R.string.not_record_future, Toast.LENGTH_SHORT).show();
				return;
			}
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private void updateDisplay() {
		etBirthday.setText(
				new StringBuilder().append(mYear + "-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
						.append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}

	private void selectUserHead() {
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

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("output", Uri.fromFile(tempFileCrop));
		intent.putExtra("return-data", false);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {

		new ForeTask(true) {
			Bitmap photo = BitmapFactory.decodeFile(tempFileCrop.getPath());

			@Override
			public void onFore() {
				ivHead.setImageBitmap(photo);
			}
		};

		/*
		 * Bundle bundle = picdata.getExtras(); if (bundle != null) { String
		 * path = LoveHealthConstant.FILE_PATH + "tmpHead.jpg"; Bitmap photo =
		 * bundle.getParcelable("data"); header.setImageBitmap(photo);
		 * imageBase64 = ReadImgToBinary.bitmapToBase64(photo); }
		 */
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PIC_BY_TACK_PHOTO:// 当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile));
			break;

		case SELECT_PIC_BY_PICK_PHOTO:// 当选择从本地获取图片时
			// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;

		case PHOTO_REQUEST_CUT:// 返回的结果
			if (data != null) {
				setPicToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onUpdteUserInfoResult(int errorCode, String message) {
		HaloToast.dismissWaitDialog();
		if (errorCode == 0) {
			if (TextUtils.isEmpty(message)) {
				toast("Success");
			} else {
				toast(message);
			}
			if (isRelogin) {
				PropertyApplication.mUserCache.setLogined(false);
				PropertyApplication.mUserCache.setToken("");

				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);

				PropertyApplication app = (PropertyApplication) MeUpdateActivity.this.getApplication();
				app.finishAll();

				finish();
			}
		} else {
			if (TextUtils.isEmpty(message)) {
				toast("Fail");
			} else {
				toast(message);
			}
		}
	}
}

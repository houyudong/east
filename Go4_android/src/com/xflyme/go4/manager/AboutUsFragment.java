package com.xflyme.go4.manager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.AboutUsEntity;
import com.xflyme.go4.manager.AboutUsLogic;
import com.xflyme.go4.manager.AboutUsObserver;
import com.xflyme.go4.view.HaloToast;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsFragment extends Fragment implements AboutUsObserver , OnClickListener {
	
	private TextView btnCando;
	private TextView btnManagerment;
	private int type = PropertyConstant.TEAM_TYPE_CANDO;
	private Context context;
	
	private TextView tvTitle;
	private TextView tvContent;
	private ImageView ivLogo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container,false);
		
		context = getActivity();
		AboutUsLogic.getInstance().addObserver(this);
		initView(rootView);
		AboutUsLogic.getInstance().onGetAboutUs(type);
		
		return rootView;
	}
	
	void initView(View v){
		btnCando = (TextView) v.findViewById(R.id.tv_about_cando);
		btnManagerment = (TextView) v.findViewById(R.id.tv_about_managerment);
		btnCando.setSelected(true);
		btnCando.setEnabled(false);
		btnCando.setOnClickListener(this);
		btnManagerment.setOnClickListener(this);
		
		tvTitle = (TextView) v.findViewById(R.id.tv_about_title);
		tvContent = (TextView) v.findViewById(R.id.tv_about_content);
		ivLogo = (ImageView) v.findViewById(R.id.iv_about_pic);
	}

	@Override
	public void onGetAboutUsSuccess(AboutUsEntity aboutUsEntity) {
		HaloToast.dismissWaitDialog();
		tvTitle.setText(aboutUsEntity.getData().getName());
		tvContent.setText(aboutUsEntity.getData().getIntroduction());
		ImageLoader.getInstance().displayImage(aboutUsEntity.getData().getLogoUrl(), ivLogo);
		((ManagermentActivity)getActivity()).setPhone(aboutUsEntity.getData().getEnquiryPhone(),aboutUsEntity.getData().getSecurityPhone());
		
	}

	@Override
	public void onGetAboutUsFail(int resultCode, String message) {
		HaloToast.dismissWaitDialog();
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AboutUsLogic.getInstance().removeObserver(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_about_cando:
			type = PropertyConstant.TEAM_TYPE_CANDO;
			btnCando.setSelected(true);
			btnCando.setEnabled(false);
			btnManagerment.setSelected(false);
			btnManagerment.setEnabled(true);
			AboutUsLogic.getInstance().onGetAboutUs(type);
			HaloToast.showNewWaitDialog(context, false, context.getString(R.string.loading));
			
			break;

		case R.id.tv_about_managerment:
			type = PropertyConstant.TEAM_TYPE_MANAGERMENT;
			btnCando.setSelected(false);
			btnCando.setEnabled(true);
			btnManagerment.setSelected(true);
			btnManagerment.setEnabled(false);
			AboutUsLogic.getInstance().onGetAboutUs(type);
			HaloToast.showNewWaitDialog(context, false, context.getString(R.string.loading));
			
			break;
			
		default:
			break;
		}
	}
}

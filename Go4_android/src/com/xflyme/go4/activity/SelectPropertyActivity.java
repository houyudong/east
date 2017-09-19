package com.xflyme.go4.activity;

import java.util.ArrayList;

import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.PropertyApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.xflyme.go4.R;
import com.xflyme.go4.entity.Relation;
import com.xflyme.go4.entity.UserInfoEntity;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class SelectPropertyActivity extends BaseActivity implements OnClickListener , OnItemClickListener{
	
	private Context context;
	private ListView lvSelect;
	private SelectPropertyAdapter adapter;
	private UserInfoEntity userInfoEntity;

	private int position = -1;
	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_property);
		
		context = SelectPropertyActivity.this;

		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		userInfoEntity = (UserInfoEntity) bundle.getSerializable("userInfoEntity");
		
		lvSelect = (ListView) findViewById(R.id.lv_select_property);
		
		
		adapter = new SelectPropertyAdapter(context,userInfoEntity.getData().getRelations());
		lvSelect.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		lvSelect.setAdapter(adapter);
		
		lvSelect.setOnItemClickListener(this);
		
		btnNext = (Button) findViewById(R.id.btn_select_next);
		btnNext.setSelected(true);
		btnNext.setEnabled(false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onClick(View v) {
		PropertyApplication.mUserCache.setHouseId(userInfoEntity.getData().getRelations().get(position).getHouseId());
		PropertyApplication.mUserCache.setCommunityId(userInfoEntity.getData().getRelations().get(position).getCommunityId());
		Intent intent = new Intent(context,MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		position = arg2;
		btnNext.setEnabled(true);
		btnNext.setSelected(false);
	}
	
	


}

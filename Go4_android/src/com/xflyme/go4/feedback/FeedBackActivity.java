package com.xflyme.go4.feedback;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xflyme.go4.BaseActivity;
import com.xflyme.go4.R;
import com.xflyme.go4.entity.FeedBackEntity;
import com.xflyme.go4.entity.FeedBackItem;

/**
 * 
 * @Description:应用主页面Activity,包含首页、资讯、工具、社区、设置
 * @author:zzj
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-12-5
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener, FeedBackObserver {

	private RelativeLayout rlLeft;
	private TextView tvTitle;
	private Context context;
	private ListView lvFeedback;
	private EditText etMessage;
	private FeedBackAdapter feedbackAdapter;

	private static final int PAGE_SIZE = 1000;
	private static final int PAGE_NO = 1;

	private int feedbackId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_back);

		context = FeedBackActivity.this;
		FeedBackLogic.getInstance().addObserver(this);
		initView();
		FeedBackLogic.getInstance().onGetFeedBack(PAGE_NO, PAGE_SIZE, feedbackId);
	}

	void initView() {
		rlLeft = (RelativeLayout) findViewById(R.id.rl_top_left);
		tvTitle = (TextView) findViewById(R.id.text_title_center);

		rlLeft.setOnClickListener(this);
		tvTitle.setText(getString(R.string.feed_back_title).toUpperCase());

		etMessage = (EditText) findViewById(R.id.et_feedback);
		lvFeedback = (ListView) findViewById(R.id.lv_feedback);
		feedbackAdapter = new FeedBackAdapter(context);
		lvFeedback.setAdapter(feedbackAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top_left:
			finish();
			break;

		case R.id.btn_feedback:
			String message = etMessage.getText().toString();
			if (TextUtils.isEmpty(message)) {
				Toast.makeText(context, getString(R.string.password_is_null), Toast.LENGTH_SHORT).show();
				return;
			}
			FeedBackLogic.getInstance().onSaveFeedBack(message, feedbackId);
			FeedBackItem feedBackItem = new FeedBackItem(message, 1);
			feedbackAdapter.add(feedBackItem);
			etMessage.setText("");
			lvFeedback.setSelection(feedbackAdapter.getCount() - 1);
			break;

		default:
			break;
		}
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
	protected void onDestroy() {
		super.onDestroy();
		FeedBackLogic.getInstance().removeObserver(this);
	}

	@Override
	public void onGetFeedBackSuccess(FeedBackEntity feedBackEntity) {
		feedbackId = feedBackEntity.getData().getFeedbackId();
		feedbackAdapter.refresh(feedBackEntity.getData().getReplyList());
		lvFeedback.setSelection(feedbackAdapter.getCount() - 1);
	}

	@Override
	public void onGetFeedBackFail(int resultCode, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSaveFeedBackResult(int resultCode, String message) {

	}

}

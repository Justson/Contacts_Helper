package com.qypt.just_syn_asis_version1_0.activity;

import com.qypt.just_syn_asis_version1_0.model.EventBussMessage;
import com.qypt.just_syn_asis_version1_0.model.TaskBean;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;

import de.greenrobot.event.EventBus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Administrator justson
 * 
 */
public class AccountActivity extends SynActivity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private TextView account_phone_phone_textview;
	private TextView account_name_name_textview;
	private Button out_account_button;
	public static final int ACCOUNTACTIVITY = 1099;
	private MyHandler mMyHandler;
	private String name;

	@Override
	protected void initView() {
		EventBus.getDefault().register(this);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_account);
		ImageView comeback_account = (ImageView) findViewById(R.id.comeback_account);
		comeback_account.setOnClickListener(this);
		account_phone_phone_textview = (TextView) findViewById(R.id.account_phone_phone_textview);
		account_name_name_textview = (TextView) findViewById(R.id.account_name_name_textview);
		out_account_button = (Button) findViewById(R.id.out_account_button);
		out_account_button.setOnClickListener(this);
		mMyHandler = new MyHandler(Looper.getMainLooper());
		LinearLayout operation_account_linearlayout = (LinearLayout) findViewById(R.id.operation_account_linearlayout);
		operation_account_linearlayout.setOnClickListener(this);
	}

	@Override
	protected void initViewData() {

		Intent mIntent = getIntent();
		String phone = mIntent.getStringExtra("phone");
		name = mIntent.getStringExtra("name");
		account_name_name_textview.setText(name);
		account_phone_phone_textview.setText(phone);
	}

	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.comeback_account:

			this.finish();
			break;
		case R.id.out_account_button:
			drop_Out_account();
			break;

		case R.id.operation_account_linearlayout:
			Intent mIntent = new Intent(this, OperationActivity.class);
			mIntent.putExtra("userName",name );
			startActivity(mIntent);
			break;

		}

	}

	/**
	 * 提示用户， 确定便退出
	 */
	private void drop_Out_account() {
		Log.i("Info", "show dialog");
		AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this, 2);
		mAlertDialog.setTitle("提示");
		mAlertDialog.setMessage("确定退出账号吗?");
		mAlertDialog.setNegativeButton("取消", null);
		mAlertDialog.setPositiveButton("确定", this);
		mAlertDialog.show();

	}

	/**
	 * 跳转到登录界面
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		DialogUtils.moreColorProgress(this);

		Message mMessage = mMyHandler.obtainMessage(0x99);
		mMyHandler.sendMessageDelayed(mMessage, 2000);

	}

	private void NotifyMainActivity() {

		EventBussMessage.Builder builder = new EventBussMessage.Builder();
		builder.setSubscriber(ACCOUNTACTIVITY);
		builder.setMessage("用户退出了啦~");
		EventBussMessage mEventBussMessage = builder.build();
		EventBus.getDefault().post(mEventBussMessage);
	}

	/**
	 * 用handler做一个延迟的效果
	 * 
	 * @author Administrator
	 * 
	 */
	final class MyHandler extends Handler {

		public MyHandler(Looper mLooper) {
			super(mLooper);
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x99) {
				clearRecord();
				NotifyMainActivity();// 通知主界面，退出账号了
				Intent mIntent = new Intent(AccountActivity.this,
						LoginActivity.class);
				startActivityForResult(mIntent,0);
				DialogUtils.moreColorDismiss();
				AccountActivity.this.finish();
			}
		}
	}

	/**
	 * 清楚sharePreference里面的文件
	 */
	private void clearRecord() {

		SharedPreferences mSharedPreferences = this.getSharedPreferences(
				"account", this.MODE_PRIVATE);
		Editor mEditor = mSharedPreferences.edit();
		mEditor.clear();
		mEditor.commit();
	}

	public void onEventMainThread(EventBussMessage mEventBussMessage) {

	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}

package com.qypt.just_syn_asis_version1_0.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.qypt.just_syn_asis_version1_0.model.RegisterMessage;
import com.qypt.just_syn_asis_version1_0.presenter.RegisterPresenter;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
import com.qypt.just_syn_asis_version1_0.view.RegisterView;

public class RegisterActivity extends Activity implements OnClickListener,
		RegisterView {

	private EditText phone_register_edittext;
	private EditText username_register_edittext;
	private EditText password_register_edittext;
	private EditText vatify_register_edittext;
	private Button register_createaccount_button;
	private RegisterPresenter mRegisterPresenter;
	private String name;
	private String password;
	private String phone;
	private String code;
	private TextView getVatify_register_textview;
	private static final String KEY = "115917849980f";
	private static final String App_Secret = "d8fac5503af4b54e54610a41ac431e9b";
	private EventHandler mEventHandler;
	private MyCountDownTime myCountDownTime;
	private static final int SUCESS_REGISTER=1;
	private static final int FAIL_REGISTER=0;
	private ProgressBar mProgressBar;
	private ProgressDialog mProgressDialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_register);
		mRegisterPresenter = new RegisterPresenter();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0x33)
				{
					RegisterActivity.this.finish();
				}
			}
		};
		mRegisterPresenter.attachView(this);
		SMSSDK.initSDK(this, KEY, App_Secret);
		initEventHandler();
		SMSSDK.registerEventHandler(mEventHandler);
		initView();
	}

	/**
	 * 初始化sms回调对象
	 */
	private void initEventHandler() {

		mEventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				super.afterEvent(event, result, data);
				
				if(result==SMSSDK.RESULT_COMPLETE&&event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
				{
					/**
					 *  发送到主线处理
					 */
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						
						@Override
						public void run() {
							goToServiceRegisterAccount();
						}
					});
				}
				
			}

			
		};
	}

	//去服务器注册
	private void goToServiceRegisterAccount() {
		
		mRegisterPresenter.dispatchTast(name, phone, password);
		
	}
	
	private void initView() {

		phone_register_edittext = (EditText) findViewById(R.id.phone_register_edittext);
		username_register_edittext = (EditText) findViewById(R.id.username_register_edittext);
		password_register_edittext = (EditText) findViewById(R.id.password_register_edittext);
		vatify_register_edittext = (EditText) findViewById(R.id.vatify_register_edittext);
		register_createaccount_button = (Button) findViewById(R.id.register_createaccount_button);
		register_createaccount_button.setOnClickListener(this);
		getVatify_register_textview = (TextView) findViewById(R.id.getVatify_register_textview);
		getVatify_register_textview.setOnClickListener(this);
		
		Button lefe_createaccnout_button=(Button) this.findViewById(R.id.lefe_createaccnout_button);
		lefe_createaccnout_button.setOnClickListener(this);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage("正在注册......");
		mProgressDialog.setCancelable(false);

		myCountDownTime = new MyCountDownTime(60000, 500);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lefe_createaccnout_button){
			this.finish();
			return;
		}

		if (v.getId() == R.id.register_createaccount_button) {
			boolean tag = processUserInputData();
			if(tag)
			{
				submitVatifyToMob("+86",phone,code);
			}
		}
		//点击获取验证码
		if (v.getId() == R.id.getVatify_register_textview) {

			if(!NetWorkHepler.netWorkIsReady(this))
			{
				Toast.makeText(getApplicationContext(), "没有网络哦!", Toast.LENGTH_LONG).show();
				return;
			}
				
			if(phone_register_edittext.getText().toString().equals("")||phone_register_edittext.getText().toString().length()!=11)
			{
				showDialogTipsUser(3);
				return;
			}
			
			getVatify_register_textview.setTextColor(Color.parseColor("#123456"));
			myCountDownTime.start();
			sendMessageToUser(phone_register_edittext.getText().toString().trim());
		}

	}

	/**
	 *  提交验证码给mob服务器匹配
	 * @param string
	 * @param phone2
	 * @param code2
	 */
	private void submitVatifyToMob(String country, String phone_, String code_) {
		
		SMSSDK.submitVerificationCode(country, phone_, code_);
	}

	/**
	 * 给用户发送验证码
	 * @param trim
	 */
	private void sendMessageToUser(String phone) {
		
		SMSSDK.getVerificationCode("+86", phone);
		
	}

	public void initUserMessage() {
		name = username_register_edittext.getText().toString().trim();
		password = password_register_edittext.getText().toString().trim();
		phone = phone_register_edittext.getText().toString().trim();
		code = vatify_register_edittext.getText().toString().trim();
	}

	/**
	 * 判空
	 * 
	 * @return
	 */
	private boolean processUserInputData() {

		initUserMessage();

		if (name == null || name.equals("")) {
			int x = 1;
			showDialogTipsUser(1);
			return false;
		}
		if (password == null || password.equals("")) {
			showDialogTipsUser(2);
			return false;
		}
		if (phone == null || phone.equals("")) {
			showDialogTipsUser(3);
			return false;
		}
		if (password != null && password.length() < 6) {
			showDialogTipsUser(4);
			return false;
		}
		if (code == null || code.equals("")) {
			showDialogTipsUser(5);
			return false;
		}
		return true;
	}

	/**
	 * 显示窗口提示用户输入不正确
	 * 
	 * @param i
	 */
	@SuppressLint("NewApi") private void showDialogTipsUser(int i) {

		AlertDialog mAlertDialog = new AlertDialog.Builder(
				this,2).create();
		LayoutInflater mLayoutInflater = this.getLayoutInflater();
		View view = mLayoutInflater.inflate(R.layout.dialog_tips, null);
		TextView content_dialog_textview = (TextView) view
				.findViewById(R.id.content_dialog_textview);
		if (i == 1) {
			content_dialog_textview.setText("用户名不能为空哦！");
		}
		if (i == 2) {
			content_dialog_textview.setText("密码不能为空哦");
		}
		if (i == 3) {
			content_dialog_textview.setText("电话号码不正确！");
		}
		if (i == 4) {
			content_dialog_textview.setText("密码长度不能小于6位");
		}
		if (i == 5) {
			content_dialog_textview.setText("验证码不能为空");
		}
		mAlertDialog.setView(view);
		WindowManager.LayoutParams lp = mAlertDialog.getWindow()
				.getAttributes();
		lp.width = 200;
		lp.height = 120;
		mAlertDialog.getWindow().setAttributes(lp);
		mAlertDialog.show();
	}

	@Override
	public void setProgressBarShow() {

		mProgressDialog.show();
	}

	@Override
	public void setProgressBarhint() {

		mProgressDialog.dismiss();
	}

	@Override
	public <T> void setData(T t) {
		 
		String result=(String) t;
		
		final RegisterMessage mRegisterMessage=JsonUtils.getEntityDataFromJson(result, RegisterMessage.class);
		if(mRegisterMessage==null)
			return;
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				registerResult(mRegisterMessage);
			}
		});
		
		
		
	}

	private void registerResult(RegisterMessage mRegisterMessage) {
		
		int code=mRegisterMessage.getCode();
		if(code==SUCESS_REGISTER)
		{
			Toast.makeText(getApplicationContext(), "成功注册!", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(0x33);
			
		}else if(code==FAIL_REGISTER)
		{
			Toast.makeText(getApplicationContext(), mRegisterMessage.getResult(), Toast.LENGTH_SHORT).show();
		}
		
		
	}

	/**
	 * 计算重获验证码的倒计时
	 */
	 class MyCountDownTime extends CountDownTimer
	 {

		public MyCountDownTime(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		//每秒回调
		@Override
		public void onTick(long millisUntilFinished) {
			
			getVatify_register_textview.setText(millisUntilFinished/1000+"s重获验证码");
		}
		//完成计时回调
		@Override
		public void onFinish() {
			getVatify_register_textview.setText("获取验证码");
			getVatify_register_textview.setTextColor(Color.parseColor("#333333"));
		}
		 
	 }
	 
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			overridePendingTransition(0, R.anim.top_out_fade_out);
		}
	 
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//注销短信成功后的回调
		SMSSDK.unregisterEventHandler(mEventHandler);
		if(mRegisterPresenter!=null)
		{
			mRegisterPresenter.dispatchView(this);
		}
	}
	
}

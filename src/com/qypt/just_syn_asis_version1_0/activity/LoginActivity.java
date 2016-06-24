package com.qypt.just_syn_asis_version1_0.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.model.ResultMessage;
import com.qypt.just_syn_asis_version1_0.presenter.RegisterPresenter;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.view.RegisterView;
/**
 * 
 * @author Administrator justson
 *
 */
public class LoginActivity extends Activity implements OnClickListener,
		RegisterView {

	private EditText phone_login_edittext;
	private EditText password_login_edittext;
	private TextView register_login_textview;
	private Button login_login_button;
	private String phone;
	private String password;
	private RegisterPresenter mRegisterPresenter;
	private DialogUtils mDialogUtils;
	private Handler handler;
	private static final int LOGIN_SUCESS=1;
	private static final int LOGIN_FAIL=0;
	private boolean DIALOGISSHOWED=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		overridePendingTransition(R.anim.rotate_scale, 0);
		initView();
		initHandler();

	}

	private void initHandler() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
			}
		};
	}

	private void initView() {

		phone_login_edittext = (EditText) findViewById(R.id.phone_login_edittext);
		password_login_edittext = (EditText) findViewById(R.id.password_login_edittext);
		register_login_textview = (TextView) findViewById(R.id.register_login_textview);
		login_login_button = (Button) findViewById(R.id.login_login_button);
		register_login_textview.setOnClickListener(this);
		login_login_button.setOnClickListener(this);
		TextView index_login_textview=(TextView) this.findViewById(R.id.index_login_textview);
		index_login_textview.setOnClickListener(this);

		mRegisterPresenter = new RegisterPresenter();
		mRegisterPresenter.attachView(this);
 
		mDialogUtils = new DialogUtils();
		
		phone_login_edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				int leng=s.toString().length();
				if(leng!=11)
				{
					login_login_button.setEnabled(false);
					login_login_button.setTextColor(Color.parseColor("#50f1f2f3"));
				}
				else
				{
					login_login_button.setEnabled(true);
					login_login_button.setTextColor(Color.parseColor("#ffffff"));
				}
				
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.index_login_textview){
			this.finish();
			return ;
		}

		if (v.getId() == R.id.register_login_textview) {
			Intent mIntent = new Intent(this, RegisterActivity.class);
			startActivityForResult(mIntent, 10);

		}
		if (v.getId() == R.id.login_login_button) {
			if (whether_legal_account_password()) {
				Log.i("Info", "dispath_login");
				mRegisterPresenter.dispatchTast_login("", phone, password);
				
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRegisterPresenter.dispatchView(this);
	}

	/**
	 * 判断账号和密码是否合法
	 */
	private boolean whether_legal_account_password() {

		phone = phone_login_edittext.getText().toString().trim();
		password = password_login_edittext.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			DialogUtils.alertDialog_(this, "电话号码不正确");
			return false;
		}
		if (password == null || password.equals("")) {
			DialogUtils.alertDialog_(this, "密码输入有误!");
			return false;
		}
		if (phone.length() != 11) {
			DialogUtils.alertDialog_(this, "电话号码长度输入有误!");
			// dialog
			return false;
		}
		if (password.length() < 6) {
			DialogUtils.alertDialog_(this, "密码长度不能小于六位哦!");
			return false;
		}

		return true;
	}

	@Override
	public void setProgressBarShow() {

		mDialogUtils.showDialogToProgress(this, "正在登陆...");
	}

	@Override
	public void setProgressBarhint() {

		mDialogUtils.dismissDialogToProgress();
	}

	@Override
	public <T> void setData(T t) {

		String json = (String) t;
		if (t == null || t.equals(""))
			return;
		final ResultMessage mResultMessage = JsonUtils.getEntityDataFromJson(
				json, ResultMessage.class);

		handler.post(new Runnable() {

			@Override
			public void run() {

				processResult(mResultMessage);
			}

		});

	}

	/**
	 * 处理登陆结果
	 * 
	 * @param mResultMessage
	 */
	private void processResult(ResultMessage mResultMessage) {

		int code=mResultMessage.getCode();
		
		if(code==LOGIN_SUCESS)
		{
			String userName=mResultMessage.getUserName();
			String phone=mResultMessage.getPhone();
			Intent mIntent=this.getIntent();
			mIntent.setAction("android.intent.action.MAIN");
			mIntent.putExtra("userName", userName);
			mIntent.putExtra("phone", phone);
			mIntent.putExtra("could", mResultMessage.getCouldNumber());
			this.setResult(1, mIntent);
			this.finish();
		}
		else if(code==LOGIN_FAIL)
		{
			DialogUtils.alertDialog_(this, mResultMessage.getResult());
		}
		
	}

}

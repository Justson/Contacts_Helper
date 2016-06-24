package com.qypt.just_syn_asis_version1_0.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.model.FeedbackTaskBean;
import com.qypt.just_syn_asis_version1_0.model.ResultMessage;
import com.qypt.just_syn_asis_version1_0.presenter.FeedBackPresenter;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
import com.qypt.just_syn_asis_version1_0.view.MainView;
/**
 * 
 * @author Administrator justson
 *
 */
public class FeedbackActivity extends SynActivity implements OnClickListener ,MainView{

	private EditText content_feedback_edittext;
	private EditText title_feedback_edittext;
	private FeedBackPresenter mFeedBackPresenter;
	private String username;
	private TextView text;


	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_feedback);
		text = (TextView) findViewById(R.id.text);
		
		ImageView image=(ImageView) findViewById(R.id.left);
		image.setOnClickListener(this);
		content_feedback_edittext = (EditText) findViewById(R.id.content_feedback_edittext);
		title_feedback_edittext = (EditText) findViewById(R.id.title_feedback_edittext);
		final Button feedback_feedback_button=(Button) findViewById(R.id.feedback_feedback_button);
		feedback_feedback_button.setOnClickListener(this);
		
		title_feedback_edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(s.length()>0)
				{
					feedback_feedback_button.setEnabled(true);
					feedback_feedback_button.setTextColor(Color.WHITE);
				}
				else if(s.length()==0)
				{
					feedback_feedback_button.setEnabled(false);
					feedback_feedback_button.setTextColor(Color.parseColor("#50f1f2f3"));
				}
				
			}
		});
		
	}

	@Override
	protected void initViewData() {
		text.setText("我要反馈");
		mFeedBackPresenter = new FeedBackPresenter();
		mFeedBackPresenter.attachView(this);
		Intent mIntent=getIntent();
		username = mIntent.getStringExtra("username");
	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		case R.id.left:
			
			this.finish();
			break;
		case R.id.feedback_feedback_button:
			
			Log.i("Info", "反馈开始 ");
			if(judgeConditionisok())
			{
				sendFeedbackMessageToService();
			}
			
			break;
	 
		}
		
	}

	/**
	 * 发送消息到服务器
	 */
	private void sendFeedbackMessageToService() {
		
		FeedbackTaskBean mTaskBean=new FeedbackTaskBean();
		mTaskBean.setContent(content_feedback_edittext.getText().toString());
		mTaskBean.setTitle(title_feedback_edittext.getText().toString());
		mTaskBean.setName(username);
		mFeedBackPresenter.dispatchTask(mTaskBean);
		
	}

	//检查下网络是否可用
	private boolean judgeConditionisok() {
		
		if(!NetWorkHepler.netWorkIsReady(this))
		{
			DialogUtils.alertDialog_(this, "网络不可用...");
			return false;
		}
	 
		if(content_feedback_edittext.getText().toString().trim().equals(""))
		{
			DialogUtils.alertDialog_(this, "内容不能为空哦~");
			return false;
		}
		
		return true;
	}

	@Override
	public void showProgressBar() {
		
	}

	@Override
	public void hintProGrossBar() {
		
	}

	@Override
	public <T> void setData(T t) {
		
		String json=(String) t;
		if(json!=null&&!json.equals(""))
		{
			//解析json
			ResultMessage mResultMessage=JsonUtils.getEntityDataFromJson(json, ResultMessage.class);
			if(mResultMessage==null)
				return;
			
			if(mResultMessage.getCode()==App.SUECEECODE)
			{
				Toast.makeText(getApplicationContext(), "反馈成功,感谢您的反馈~", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			
		}
		
	}

	@Override
	public void downLoad(String result) {
		
	}
	
	@Override
	protected void onDestroy() {
		mFeedBackPresenter.dispatchView(this);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}
}

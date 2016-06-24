package com.qypt.just_syn_asis_version1_0.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 * @author Administrator  justson
 *
 */
public class DialogActivity extends SynActivity implements OnClickListener {

	private EditText name_dialogactivity_edittext;
	private EditText account_dialogactivity_edittext;
	private TextView confir_dialogactivity_textview;
	public static final int DIALOGACTIVITYCODE=5;
	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_dialog);
		name_dialogactivity_edittext = (EditText) findViewById(R.id.name_dialogactivity_edittext);
		account_dialogactivity_edittext = (EditText) findViewById(R.id.account_dialogactivity_edittext);
		confir_dialogactivity_textview = (TextView) findViewById(R.id.confir_dialogactivity_textview);
		confir_dialogactivity_textview.setOnClickListener(this);
	}

	@Override
	protected void initViewData() {
		
	}

	@Override
	public void onClick(View v) {
		
		//判断数据是否为空，不为空就获取数据然后会到收集微信卡界面
		if(v.getId()==R.id.confir_dialogactivity_textview)
		{
			String strName=name_dialogactivity_edittext.getText().toString();
			String strAccount=account_dialogactivity_edittext.getText().toString();
			if(strName.equals("")||strAccount.equals(""))
				return;
			Intent mIntent=getIntent();
			mIntent.putExtra("strName", strName);
			String position=mIntent.getStringExtra("position");
			mIntent.putExtra("position", position);
			mIntent.putExtra("strAccount", strAccount);
			this.setResult(DIALOGACTIVITYCODE, mIntent);
			this.finish();
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
		
	}

}

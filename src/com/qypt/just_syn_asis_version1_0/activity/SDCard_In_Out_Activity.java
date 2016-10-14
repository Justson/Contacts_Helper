package com.qypt.just_syn_asis_version1_0.activity;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SDCard_In_Out_Activity extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_sd_in_out);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		ImageView combackImageView = (ImageView) findViewById(R.id.comeback_inout_imageview);
		TextView inTextView = (TextView) findViewById(R.id.in_inout_textview);
		TextView outTextView = (TextView) findViewById(R.id.out_inout_textview);
		inTextView.setOnClickListener(this);
		outTextView.setOnClickListener(this);
		combackImageView.setOnClickListener(this);

	}
	//onclick event
	@Override
	public void onClick(View v) {

		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(getApplicationContext(), "内存卡不存在!", Toast.LENGTH_LONG).show();
			return;
		}
			
		
		switch (v.getId()) {
		case R.id.in_inout_textview:

			Intent mIntent=new Intent(this,ImportContactActivity.class);
			startActivity(mIntent);
			break;
		case R.id.out_inout_textview:

			Intent mIntent_=new Intent(this,OutContactActivity.class);
			startActivity(mIntent_);
			break;
		case R.id.comeback_inout_imageview:
			this.finish();
			break;

		}

	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}
	
	
 

}

package com.qypt.just_syn_asis_version1_0.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;
/**
 * 
 * @author Administrator justson   5/8
 *about
 */
public class AboutActivity extends SynActivity implements OnClickListener {

	private TextView text;
	private ImageView qq;
	private ImageView allow;
	private ImageView email;
	private ImageView icon_about;
	private LinearLayout email_linearlayout;
	private LinearLayout protocol_about;

	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		this.setContentView(R.layout.activity_about);
		ImageView left=(ImageView) this.findViewById(R.id.left);
		text = (TextView) this.findViewById(R.id.text);
		left.setOnClickListener(this);
		qq = (ImageView) findViewById(R.id.qq_about);
		allow = (ImageView) findViewById(R.id.allow_about);
		email = (ImageView) findViewById(R.id.email_about);
		icon_about = (ImageView) findViewById(R.id.icon_about);
		email_linearlayout = (LinearLayout) findViewById(R.id.email_linearlayout);
		email_linearlayout.setOnClickListener(this);
		protocol_about = (LinearLayout) findViewById(R.id.protocol_about);
		protocol_about.setOnClickListener(this);
		
		
	}

	@Override
	protected void initViewData() {
		
		text.setText("关于");
		initBackground();
	}
/**
 * 初始化背景
 */
	private void initBackground() {
		
		qq.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 30, 30, R.drawable.qq));
		allow.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 30, 30, R.drawable.allow_protocal));
		email.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 30, 30, R.drawable.email_protocal));
		icon_about.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 80, 80, R.drawable.icon_c));
	}

	@Override
	public void onClick(View v) {
		
		
		if(v.getId()==R.id.left){
			this.finish();
		}
		if(v.getId()==R.id.email_linearlayout){
			startSystemEmail();
		}if(v.getId()==R.id.protocol_about){
			Intent mIntent=new Intent(this,ProtocalActivity.class);
			startActivity(mIntent);
		}
		
	}
	/**
	 * 启动系统email
	 */
	private void startSystemEmail() {
		
		Intent mIntent=new Intent();
		mIntent.setAction(Intent.ACTION_SENDTO);
		mIntent.setData(Uri.parse("mailto:way.ping.li@gmail.com"));
		startActivity(mIntent);
		
		  
	}

	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

}

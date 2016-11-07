package com.qypt.just_syn_asis_version1_0.activity;

import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * 
 * @author Administrator justson
 * setting 
 */
public class SettingActivity extends Activity implements OnClickListener {

	private RelativeLayout remind_syn_linearlayout;
	private LinearLayout more_syn_linearlayout;
	private LinearLayout question_syn_linearlayout;
	private LinearLayout guanyu_syn_linearlayout;
	private ImageView about_imageview;
	private ImageView setting_imageview;
	private ImageView remind_setting_imageview;
	private ImageView more_setting_imageview;
	private ImageView question_setting_imageview;
	private ImageView yin_setting_imageview;
	private boolean isCheck = true;
	private ImageView image_setting_linearlayout;
	private ImageView syn_setting_imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_setting);
		initView();
		initBackground();
	}

	/**
	 * 初始化背景处理背景图
	 */
	private void initBackground() {

		about_imageview.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 30,
				30, R.drawable.guanyu));
		setting_imageview.setImageBitmap(BitmapUtils.getBitmapbyConfig(this,
				30, 30, R.drawable.setting));
		remind_setting_imageview.setImageBitmap(BitmapUtils.getBitmapbyConfig(
				this, 30, 30, R.drawable.remind));
		more_setting_imageview.setImageBitmap(BitmapUtils.getBitmapbyConfig(
				this, 30, 30, R.drawable.more_language));
		question_setting_imageview.setImageBitmap(BitmapUtils
				.getBitmapbyConfig(this, 30, 30, R.drawable.question));
		syn_setting_imageview.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 30, 30, R.drawable.setting));
	}

	private void initView() {

		LinearLayout setting_syn_linearlayout = (LinearLayout) findViewById(R.id.setting_syn_linearlayout);
		setting_syn_linearlayout.setOnClickListener(this);

		remind_syn_linearlayout = (RelativeLayout) findViewById(R.id.remind_syn_linearlayout);
		remind_syn_linearlayout.setOnClickListener(this);

		more_syn_linearlayout = (LinearLayout) findViewById(R.id.more_syn_linearlayout);
		more_syn_linearlayout.setOnClickListener(this);

		question_syn_linearlayout = (LinearLayout) findViewById(R.id.question_syn_linearlayout);
		question_syn_linearlayout.setOnClickListener(this);

		guanyu_syn_linearlayout = (LinearLayout) findViewById(R.id.guanyu_syn_linearlayout);
		guanyu_syn_linearlayout.setOnClickListener(this);

		about_imageview = (ImageView) findViewById(R.id.about_setting_imageview);
		setting_imageview = (ImageView) findViewById(R.id.question_setting_imageview);
		remind_setting_imageview = (ImageView) findViewById(R.id.remind_setting_imageview);
		more_setting_imageview = (ImageView) findViewById(R.id.more_setting_imageview);
		question_setting_imageview = (ImageView) findViewById(R.id.question_setting_imageview);
		yin_setting_imageview = (ImageView) findViewById(R.id.yin_setting_imageview);
		syn_setting_imageview = (ImageView) findViewById(R.id.syn_setting_imageview);
		
		image_setting_linearlayout = (ImageView) findViewById(R.id.image_setting_linearlayout);
		image_setting_linearlayout.setImageBitmap(BitmapUtils.getBitmapbyConfig(this, 20, 20, R.drawable.lefe));
		image_setting_linearlayout.setOnClickListener(this);

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
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.setting_syn_linearlayout:
			Log.i("Info", "onclick");
			Intent mIntent = new Intent(this, ContentActivity.class);
			startActivity(mIntent);
			break;
		case R.id.remind_syn_linearlayout:
			if (isCheck) {
				yin_setting_imageview.setImageBitmap(BitmapUtils
						.getBitmapbyConfig(this, 20, 20, R.drawable.yin_bule));
				isCheck = !isCheck;
			} else {
				yin_setting_imageview.setImageBitmap(BitmapUtils
						.getBitmapbyConfig(this, 20, 20, R.drawable.yin_));
				isCheck = !isCheck;

			}

			break;

		case R.id.more_syn_linearlayout:
			Intent mIntent_=new Intent(this,MoreLanguageActivity.class);
			startActivity(mIntent_);
			Log.i("Info", "onclick");
			break;
		case R.id.question_syn_linearlayout:
			Log.i("Info", "onclick");
			Intent questionIntent=new Intent(this,RobotActivity.class);
			startActivity(questionIntent);
			break;
		case R.id.guanyu_syn_linearlayout:
			Intent aboutIntent=new Intent(this,AboutActivity.class);
			startActivity(aboutIntent);
			break;
		case R.id.image_setting_linearlayout:
			this.finish();
			break;

		}

	}
}

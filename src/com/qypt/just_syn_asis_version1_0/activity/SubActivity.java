package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.adapter.MyBaseAdapter;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;
import com.qypt.just_syn_asis_version1_0.custom_view.SlidingMenu;
import com.qypt.just_syn_asis_version1_0.presenter.MainPresenter;
import com.qypt.just_syn_asis_version1_0.sqlite.SynSisSQLiteOpenHelper;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
/**
 * 
 * @author Administrator justson cenxiaozhong
 *
 */
public class SubActivity extends SynActivity{

	protected SlidingMenu mSlidingMenu;
	protected Map<String, Object> map = null;
	protected List<Map<String, Object>> list = null;
	protected ImageButton roundImageButton;
	protected MainPresenter mainPresenter;
	protected TextView local;
	protected static final int UPLOADING_STATE = 1;
	protected static int CURRENT_STATE = 0;
	protected static int DOWNLOAD_STATE = 2;
	protected TextView cloud;
	protected boolean isLogin = false;
	protected String userName = "";
	protected TextView mainActivity_time_textview;
	protected boolean excuted = false;
	protected int number;//联系人个数
	protected BroadcastReceiver mBroadcastReceiver;
	protected SynSisSQLiteOpenHelper mSisSQLiteOpenHelper;
	protected SharedPreferences mSharedPreferences;
	protected RelativeLayout unlogin_menu_relativelayout;
	protected ImageView header_menu_imageview;
	protected RelativeLayout logined_menu_relativelayout;
	protected TextView menu_number;
	protected TextView menu_name;
	protected static final int COMBINECONTACTACTIVITY = 1; // 用静态常量，不用枚举 性能更加高
	protected ListView myListView;
	protected Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		//
		
	}

	@Override
	protected void initView() {
		
		button = (Button) findViewById(R.id.button);
//		button.setOnClickListener(this);
		mSlidingMenu = (SlidingMenu) findViewById(R.id.mSlidingMenu);
		myListView = (ListView) findViewById(R.id.listView);
		list = initData();
		Button moon_Button = (Button) findViewById(R.id.moon_Button);
		Button doc_suicase_Button = (Button) findViewById(R.id.doc_suicase_Button);
		roundImageButton = (ImageButton) findViewById(R.id.roundImageButton);
		ImageButton content_phone = (ImageButton) findViewById(R.id.content_phone);
		ImageButton yunduan_ = (ImageButton) findViewById(R.id.yunduan_);
		ImageView menu_image_header = (ImageView) findViewById(R.id.menu_image_header);
		RelativeLayout content_RelativeLayout = (RelativeLayout) findViewById(R.id.content_RelativeLayout);
//		roundImageButton.setOnClickListener(this);
		mainActivity_time_textview = (TextView) findViewById(R.id.mainActivity_time_textview);

		initBackground(button, moon_Button, doc_suicase_Button,
				roundImageButton, content_phone, yunduan_, menu_image_header,
				content_RelativeLayout);

		local = (TextView) findViewById(R.id.local);
		cloud = (TextView) findViewById(R.id.cloud);
		if (list != null && !list.isEmpty()) {
			MyBaseAdapter adapter = new MyBaseAdapter(this, list);
			myListView.setAdapter(adapter);
		}
//		myListView.setOnItemClickListener(this);

		mSisSQLiteOpenHelper = new SynSisSQLiteOpenHelper(this, null);

		unlogin_menu_relativelayout = (RelativeLayout) findViewById(R.id.unlogin_menu_relativelayout);
		header_menu_imageview = (ImageView) findViewById(R.id.header_menu_imageview);
//		header_menu_imageview.setOnClickListener(this);
		logined_menu_relativelayout = (RelativeLayout) findViewById(R.id.logined_menu_relativelayout);

		menu_number = (TextView) findViewById(R.id.menu_number);
		menu_name = (TextView) findViewById(R.id.menu_name);
	}

	@SuppressWarnings("deprecation")
	private void initBackground(Button button, Button moon_Button,
			Button doc_suicase_Button, ImageButton roundImageButton,
			ImageButton content_phone, ImageButton yunduan_,
			ImageView menu_image_header, RelativeLayout content_RelativeLayout) {

		button.setBackgroundDrawable(new BitmapDrawable(BitmapUtils
				.getBitmapbyConfig(this, 30, 30, R.drawable.morew)));
		// moon_Button.setBackgroundDrawable(new BitmapDrawable(BitmapUtils
		// .getBitmapbyConfig(this, 28, 28, R.drawable.moon)));
		// doc_suicase_Button.setBackgroundDrawable(new
		// BitmapDrawable(BitmapUtils
		// .getBitmapbyConfig(this, 30, 30, R.drawable.protectw)));
		roundImageButton.setBackgroundDrawable(new BitmapDrawable(BitmapUtils
				.getBitmapbyConfig(this, 80, 80, R.drawable.syn_button)));
		content_phone.setBackgroundDrawable(new BitmapDrawable(BitmapUtils
				.getBitmapbyConfig(this, 35, 45, R.drawable.phone)));
		yunduan_.setBackgroundDrawable(new BitmapDrawable(BitmapUtils
				.getBitmapbyConfig(this, 35, 40, R.drawable.yun_duan)));
		menu_image_header.setBackgroundDrawable(new BitmapDrawable((BitmapUtils
				.getRoundBitmap(BitmapUtils.getBitmapbyConfig(this, 80, 80,
						R.drawable.meinv26)))));
		content_RelativeLayout.setBackgroundDrawable(new BitmapDrawable(
				BitmapUtils.getBitmapbyConfig(this, 768, 1024,
						R.drawable.content_bg_next)));

		Log.i("Info", "init background");
	}
	
	/**
	 * 初始化界面的数据， 判断有误登陆， 登陆了则吧用户和手机号码显示出来
	 */
	protected void initViewData() {

		int number = ContactUtils.getContactNumber(this);
		Log.i("Info", "number:" + number);
		local.setText(number + "");

		mSharedPreferences = this.getSharedPreferences("account",
				this.MODE_PRIVATE);
		String name = mSharedPreferences.getString("userName", "-1");
		String phone = mSharedPreferences.getString("phone", "-1");
		String strCloud=mSharedPreferences.getString("cloud", "0");
		cloud.setText(strCloud);
		if (!name.equals("-1")) {
			isLogin = true;
			userName = name;
			App.NAME=name;
			SQLiteDatabase mSqLiteDatabase = mSisSQLiteOpenHelper
					.getReadableDatabase();
			Cursor cursor = mSqLiteDatabase.rawQuery(
					"select *  from Syn_Sis_record where userName=?",
					new String[] { userName });
			if (cursor.moveToLast()) {
				String cloudNumber = cursor.getString(cursor
						.getColumnIndex("cloudNumber"));
				String lastTime = cursor.getString(cursor
						.getColumnIndex("time"));

				if (cloudNumber == null && !cloudNumber.equals(""))
					return;
				cloud.setText(cloudNumber.trim());
				mainActivity_time_textview.setText(lastTime);
			}
			cursor.close();

			unlogin_menu_relativelayout.setVisibility(View.GONE);
			logined_menu_relativelayout.setVisibility(View.VISIBLE);
			menu_name.setText(name);
			menu_number.setText(phone);
		}

	}
	
	/**
	 * 获取list item的数据
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> initData() {

		Log.i("Info", "init list");
		int image_[] = new int[] { R.drawable.contact_manager,
				R.drawable.photo, R.drawable.syn, R.drawable.find_back_contact,
				R.drawable.setting, R.drawable.message };
		String[] text_ = new String[] { "通讯录管理", "照片备份", "同步联系人", "找回联系人",
				"设置", "反馈" };

		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}

		for (int i = 0; i < image_.length; i++) {
			map = new HashMap<String, Object>();
			map.put("image", image_[i]);
			map.put("text", text_[i]);
			list.add(map);

		}

		return list;
	}

	// 开始动画
	protected void startViewAnim(View v) {

		Animation mAnimation = AnimationUtils.loadAnimation(this,
				R.anim.imageview_anim_syn);
		Interpolator mInterpolator = new LinearInterpolator();// 设置动画匀速旋转
		mAnimation.setInterpolator(mInterpolator);
		v.startAnimation(mAnimation);
		Log.i("Info", "start View Anim");
	}

	protected void stopViewAnim(View v) {
		Log.i("Info", "stop view anim");
		if (v == null)
			return;
		v.clearAnimation();
	}
	protected void showToastToTipsUser() {

		Toast mToast = new Toast(getApplicationContext());
		LayoutInflater mLayoutInflater = this.getLayoutInflater();
		View view = mLayoutInflater.inflate(R.layout.toast_tips, null);
		mToast.setView(view);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}
 

	
	/**
	 * 滑动到内容页面
	 */
	protected void smoothToContentPager() {
		if (mSlidingMenu != null) {
			mSlidingMenu.contentPager();

		}
	}
 
}

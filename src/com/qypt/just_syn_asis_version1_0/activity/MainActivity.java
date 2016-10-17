package com.qypt.just_syn_asis_version1_0.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import javax.xml.datatype.Duration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.qypt.just_syn_asis_version1_0.model.EventBussMessage;
import com.qypt.just_syn_asis_version1_0.model.UpLoadBean;
import com.qypt.just_syn_asis_version1_0.presenter.MainPresenter;
import com.qypt.just_syn_asis_version1_0.sqlite.SynSisSQLiteOpenHelper;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
import com.qypt.just_syn_asis_version1_0.utils.TimerUtils;
import com.qypt.just_syn_asis_version1_0.view.MainView;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author Administrator justson
 * 	用了模板模式
 */
public class MainActivity extends SubActivity implements MainView,
		OnClickListener, OnItemClickListener {

	
	public static final int MAINACTIVITY=5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		EventBus.getDefault().register(this);// 注册订阅者
		mainPresenter = new MainPresenter(MainActivity.this);
		// 初始化广播和注册广播
		IntentFilter mIntentFilter = new IntentFilter();
		handleBroadcast();
		mIntentFilter
				.addAction("com.qypt.just.syn_sis_main_contact_handle_finish");
		registerReceiver(mBroadcastReceiver, mIntentFilter);

		setListenerForWidget();
	}

	private void setListenerForWidget() {
		
		myListView.setOnItemClickListener(this);
		button.setOnClickListener(this);
		roundImageButton.setOnClickListener(this);
		header_menu_imageview.setOnClickListener(this);
		unlogin_menu_relativelayout.setOnClickListener(this);
		logined_menu_relativelayout.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		number = ContactUtils.getContactNumber(this);
		local.setText(number + "");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button:
			mSlidingMenu.switchButton();
			break;
		case R.id.roundImageButton:
			clickRoundImageButton();

			break;
		case R.id.unlogin_menu_relativelayout:
			if(!isLogin)
				passOnLoginActivity();
			break;
		case R.id.logined_menu_relativelayout:
			
			if(!isLogin)
			{
				passOnLoginActivity();
				return;
			}
			
			
			smoothToContentPager();
			Intent loginIntent=new Intent(this,AccountActivity.class);
			loginIntent.putExtra("name", userName);
			loginIntent.putExtra("phone", menu_number.getText().toString().trim());
			startActivityForResult(loginIntent, 66);
			
			break;

		}

	}

	/**
	 * 点击ClickRoudImageButton
	 */
	private void clickRoundImageButton() {

		if (!isLogin) {
			/**
			 * 这里做跳转登陆页面
			 */

			passOnLoginActivity();
			return;
		}
		// 检查网络是否可用
		if (!NetWorkHepler.netWorkIsReady(getApplicationContext())) {
			showToastToTipsUser();
			return;
		}

		if (excuted)
			return;
		if (roundImageButton != null) {
			boolean tag = isUpLoad(local.getText().toString().trim(), cloud
					.getText().toString().trim());
			if (tag) {
				processUpLoadContact();
			} else {
				/**
				 * 
				 */
				mainPresenter.LoaddinngData(userName);
			}

			excuted = true;

		}
	}

	/**
	 * 处理上传联系人
	 */
	private void processUpLoadContact() {
		List<Map<String, Object>> list = ContactUtils.getAllContactList(this);
		String filePath = null;
		if (list == null)
			return;
		number = list.size();
		try {
			String path = writeDataToFile(list);
			long currentTime = System.currentTimeMillis();
			filePath = path + File.separator + userName + "" + currentTime
					+ ".txt";
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File(filePath)));
			oos.writeObject(list);
			oos.close();
			if (list != null) {
				list.clear();
				list = null;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			mainPresenter.upLoadData(userName, filePath, number);
			CURRENT_STATE = UPLOADING_STATE;
		}
	}

	/**
	 * 转到登录界面
	 */
	private void passOnLoginActivity() {

		Intent mIntent = new Intent(this, LoginActivity.class);
		startActivityForResult(mIntent, 0);

	}

	// 登陆界面回跳
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == 1) {
			String userName_ = data.getStringExtra("userName");
			String phone = data.getStringExtra("phone");
			String strCould=data.getStringExtra("could");
			if(strCould==null||strCould.trim().equals(""))
				strCould="0";

			Log.i("Info", "保存用户");
			// 吧数据保存起来 下次免密码登陆
			Editor mEditor = mSharedPreferences.edit();
			if (mEditor == null)
				return;
			mEditor.putString("userName", userName_);
			mEditor.putString("phone", phone);
			mEditor.putString("cloud", strCould);
			mEditor.commit();

			isLogin = true;
			userName = userName_;
			App.NAME=userName_;
			setUerData(userName, phone,strCould);
		}

	}

	// 为用户设置信息
	private void setUerData(String userName_, String phone,String strCould) {
		Log.i("Info", "登录");
		cloud.setText(strCould.trim());
		menu_name.setText(userName_);
		menu_number.setText(phone);
		logined_menu_relativelayout.setVisibility(View.VISIBLE);
		unlogin_menu_relativelayout.setVisibility(View.GONE);

	}



	/**
	 * 把list联系人写入一个文件中，返回文件路径
	 * 
	 * @param list2
	 * @return
	 * @throws IOException
	 */
	private String writeDataToFile(List<Map<String, Object>> list_)
			throws IOException {

		if (list_ == null || list.isEmpty())
			return null;
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getCanonicalPath()
					+ File.separator + "syn";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			path = Environment.getDownloadCacheDirectory().getAbsolutePath();
		}

		return path;
	}

	/*
	 * 判断是上传还是下载
	 */
	private boolean isUpLoad(String local_number, String cloud_number) {

		long local = Long.parseLong(local_number);
		long cloud = Long.parseLong(cloud_number);
		if (cloud > local) {
			return false;
		} else if (cloud == local) {
			return false;
		}

		return true;
	}



	/**
	 * listview的点击事件处理 "通讯录管理", "照片备份", "短信和通话记录备份", "找回联系人", "设置", "反馈"
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		
		switch (position) {
		case 0:
			smoothToContentPager();
			Intent mIntent = new Intent(this, ContactManageActivity.class);
			startActivityForResult(mIntent, 0);

			break;
		case 1:
			Toast.makeText(this, "此功能暂没开放", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			mSlidingMenu.switchButton();
			break;
		case 3:
			smoothToContentPager();
			if (!isLogin) {
				passOnLoginActivity();
				return;
			}

			Intent mIntent_ = new Intent(this, RetrieveActivity.class);
			startActivityForResult(mIntent_, 3);
			break;

		case 4:
			smoothToContentPager();
			Intent mIntentSetting = new Intent(this, SettingActivity.class);
			startActivityForResult(mIntentSetting, 4);
			break;
			
		case 5:
			smoothToContentPager();
			Intent mIntentFeedback = new Intent(this, FeedbackActivity.class);
			mIntentFeedback.putExtra("username", userName);
			startActivityForResult(mIntentFeedback, 5);
			break;

		

		}
			

	}

	@Override
	public void showProgressBar() {
		// TODO Auto-generated method stub
		startViewAnim(roundImageButton);

	}

	@Override
	public void hintProGrossBar() {
		stopViewAnim(roundImageButton);
		excuted = false;
	}

	@Override
	public <T> void setData(T t) {

		UpLoadBean mUpLoadBean = (UpLoadBean) t;
		if (Integer.parseInt(mUpLoadBean.getCode()) == 1) {
			mainActivity_time_textview.setText(mUpLoadBean.getUpDateTime());

			if (CURRENT_STATE == UPLOADING_STATE) {
				local.setText(mUpLoadBean.getNumber().toString().trim());
				cloud.setText(mUpLoadBean.getNumber().toString().trim());
				updataDataBase(mUpLoadBean);
			}
			excuted = false;
			CURRENT_STATE = 0;
		}

	}

	/**
	 * 更新数据库
	 * 
	 * @param mUpLoadBean
	 */
	private void updataDataBase(UpLoadBean mUpLoadBean) {
		String time = TimerUtils.getDate("yyyy-MM-dd");
		SQLiteDatabase mDatabase = mSisSQLiteOpenHelper.getReadableDatabase();
		mDatabase.execSQL("insert into Syn_Sis_record values(?,?,?,?,?)",
				new String[] { null, userName, time,
						local.getText().toString().trim(),
						mUpLoadBean.getNumber().trim() });
		mDatabase.close();

	}

	@Override
	public void downLoad(String result) {

		try {
			processResultContact(result);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将文件转换成list<Map<String,Object> 然后写入联系人列表
	 * 
	 * @param result
	 * @throws IOException
	 * @throws StreamCorruptedException
	 * @throws ClassNotFoundException
	 */
	List<Map<String, Object>> list_ = null;

	private void processResultContact(String result)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {

		File file = new File(result);
		InputStream is = new FileInputStream(file);

		if (is != null) {
			ObjectInputStream oos = new ObjectInputStream(is);
			list_ = (List<Map<String, Object>>) oos.readObject();
		}

		if (list_ != null) {
			number = list_.size();// 为联系人的大小赋值
			excuted = false; // 执行状态改为false
			if (number > 0)
				ContactUtils.deleteAllContact(this);

			Log.i("Info", "list___:" + list.toString());
			// 启动子线程去插入联系人
			new Thread() {
				public void run() {
					ContactUtils.handleContact(MainActivity.this, list_,MAINACTIVITY);
				};
			}.start();

		}

	}

	/**
	 * 接受到表示已经同步成功了， 把相应的东西设置上，把数据插入数据库
	 */
	private void handleBroadcast() {
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				stopViewAnim(roundImageButton);
				String time = TimerUtils.getDate("yyyy-MM-dd");
				mainActivity_time_textview.setText(time.trim());
				local.setText(number + "");
				cloud.setText(number + "");
				SQLiteDatabase mDatabase = mSisSQLiteOpenHelper
						.getReadableDatabase();
				mDatabase
						.execSQL(
								"insert into Syn_Sis_record values(?,?,?,?,?)",
								new String[] { null, userName, time,
										local.getText().toString().trim(),
										number + "" });
				mDatabase.close();
				Toast.makeText(context, "同步成功！", Toast.LENGTH_LONG).show();
			}
		};
	}

	/**
	 * EventBus 的回调， 在主线程
	 * @param mEventBussMessage
	 */
	public void onEventMainThread(EventBussMessage mEventBussMessage) {
		if (mEventBussMessage == null)
			return;
		if (mEventBussMessage.getSubscriber() == COMBINECONTACTACTIVITY)// 表示合并联系人成功，
		{
			if(isLogin==false||!NetWorkHepler.netWorkIsReady(this))
				return;
			processUpLoadContact();
		}
		if(mEventBussMessage.getSubscriber()==AccountActivity.ACCOUNTACTIVITY){
			//表示用户退出账号了
			restoreUnLoginState();
		}
	}

	/**
	 * 还原成没登录状态
	 */
	private void restoreUnLoginState() {
		userName="";
		menu_number.setText("");
		isLogin=false;
		cloud.setText("0");
		logined_menu_relativelayout.setVisibility(View.GONE);
		unlogin_menu_relativelayout.setVisibility(View.VISIBLE);
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 反注册广播
		if (mBroadcastReceiver != null)
			unregisterReceiver(mBroadcastReceiver);
		EventBus.getDefault().unregister(this);
	}

}

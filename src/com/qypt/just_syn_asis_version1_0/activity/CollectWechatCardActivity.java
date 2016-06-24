package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.adapter.SubGridViewAdapter;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.model.WechatBean;
import com.qypt.just_syn_asis_version1_0.sqlite.WechatCardSQLiteOpenHelper;
/**
 * 
 * @author Administrator justson  2016 /5 /5
 *
 */
public class CollectWechatCardActivity extends SynActivity implements OnClickListener {

	private TextView text;
	private int mScreenWidth;
	private int mScreenHeight;
	private LayoutInflater mLayoutInflater;
	private GridView myGridView;
	private WechatCardSQLiteOpenHelper mWechatCardSQLiteOpenHelper;
	private List<WechatBean> list;
	private SubGridViewAdapter mSubGridViewAdapter;

	@Override
	protected void initView() {
		
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		this.setContentView(R.layout.activity_collect_wetchat_card);
		TextView add_collect_textview=(TextView) findViewById(R.id.add_collect_textview);
		add_collect_textview.setOnClickListener(this);
		text = (TextView) findViewById(R.id.text);
		myGridView = (GridView) findViewById(R.id.myGridView);
		ImageView image=(ImageView) findViewById(R.id.left);
		image.setOnClickListener(this);
	}

	/**
	 * 初始化控件和相应的值
	 */
	@Override
	protected void initViewData() {
		text.setText("微信名片");
		DisplayMetrics dm=this.getResources().getDisplayMetrics();
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
		
		mWechatCardSQLiteOpenHelper = WechatCardSQLiteOpenHelper.getInstance(App.getAppContext());
		SQLiteDatabase  db=mWechatCardSQLiteOpenHelper.getReadableDatabase();
		
		list = new ArrayList<WechatBean>();
		
		WechatBean mWechatBean=null;
		Cursor cursor=db.rawQuery("select* from wechat_card_backup", null);
		if(cursor.moveToNext())
		{
			do {
				mWechatBean=new WechatBean();
				mWechatBean.setName(cursor.getString(1));
				mWechatBean.setAccount(cursor.getString(2));
				list.add(mWechatBean);
				mWechatBean=null;
				
			} while (cursor.moveToNext());
			cursor.close();
		}
		mSubGridViewAdapter = new SubGridViewAdapter(this, list);
			myGridView.setAdapter(mSubGridViewAdapter);
//			startActivityForResult(intent, requestCode)
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			this.finish();
			break;

		case R.id.add_collect_textview:
			addView();
			break;
		}
	}

	/**
	 * 创建卡片
	 */
	private void addView() {
		
          WechatBean mWechatBean=new WechatBean();
          mWechatBean.setAccount("****");
          mWechatBean.setName("***");
          mWechatBean.setNewAccount(true);
          list.add(mWechatBean);
		
          myGridView.requestFocus();
          myGridView.setSelection(list.size()-1);
          mWechatBean=null;
	}
	/**
	 *  用户录入的资料返回， 写入数据库
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode==SubGridViewAdapter.SUBGRIDVIEW&&resultCode==DialogActivity.DIALOGACTIVITYCODE)
		{
			String strName=data.getStringExtra("strName");
			String strAccount=data.getStringExtra("strAccount");
			String  position=data.getStringExtra("position");
			Log.i("Info", "list.size:"+list.size());
			Log.i("Info", "position:"+position);
			WechatBean mWechatBean=list.get(Integer.parseInt(position));
			mWechatBean.setAccount(strAccount);
			mWechatBean.setName(strName);
			mSubGridViewAdapter.notifyDataSetChanged();
			
			insertToDatabase(strName,strAccount);
			
		}
		
	}

	/**
	 * 吧相应的名字和微信号插入数据库
	 * @param strName
	 * @param strAccount
	 */
	private void insertToDatabase(String strName, String strAccount) {
		
		SQLiteDatabase db=mWechatCardSQLiteOpenHelper.getReadableDatabase();
		db.execSQL("insert into wechat_card_backup values(?,?,?)", new String[]{null,strName,strAccount});
		db.close();
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
		if(list!=null)
		{
			list.clear();
			list=null;
		}
	}

}

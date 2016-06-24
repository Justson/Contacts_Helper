package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.adapter.OprationAdapter;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.model.SynSQLiteBean;
import com.qypt.just_syn_asis_version1_0.sqlite.SynSisSQLiteOpenHelper;

/**
 * 
 * @author Administrator justson
 * 
 */
public class OperationActivity extends SynActivity implements OnClickListener, OnItemLongClickListener {

	private ImageView image;
	private ListView listview;
	private List<SynSQLiteBean> list = new ArrayList<SynSQLiteBean>();
	private String userName;
	private OprationAdapter mOprationAdapter;

	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_operation);
		image = (ImageView) this.findViewById(R.id.left);
		image.setOnClickListener(this);
		TextView text = (TextView) findViewById(R.id.text);
		text.setText("操作");
		listview = (ListView) this.findViewById(R.id.mylistview_operation);

		
		
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initViewData() {

		Intent mIntent = this.getIntent();
		userName = mIntent.getStringExtra("userName");
		if (userName == null || userName.equals(""))
			return;
		SynSisSQLiteOpenHelper mSynSisSQLiteOpenHelper = SynSisSQLiteOpenHelper
				.getInstance(App.getAppContext());
		if (mSynSisSQLiteOpenHelper == null)
			return;
		SQLiteDatabase db = mSynSisSQLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from Syn_Sis_record where userName=?",
				new String[] { userName });
		SynSQLiteBean mSqLiteBean = null;
		if (cursor.moveToFirst()) {
			do {
				mSqLiteBean = new SynSQLiteBean();
				mSqLiteBean.setUserName(cursor.getString(1));
				mSqLiteBean.setTime(cursor.getString(2));
				mSqLiteBean.setLocalNumber(cursor.getString(3));
				mSqLiteBean.setCloudNumber(cursor.getString(4));
				list.add(mSqLiteBean);
				mSqLiteBean=null;
			} while (cursor.moveToNext());
		}
		if(cursor!=null)
			cursor.close();//用完数据库关闭
		
		
		Log.i("Info", "list:"+list);
		mOprationAdapter = new OprationAdapter(this, list);
		listview.setAdapter(mOprationAdapter);

		listview.setOnItemLongClickListener(this);//长按删除操作记录
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}

	@Override
	public void onClick(View v) {

		
		if(v.getId()==R.id.left){
			this.finish();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		
		tipsUser(position);
		
		return false;
	}

	private void tipsUser(final int position) {
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this,2);
		builder.setTitle("警告");
		builder.setMessage("确定删除操作记录？");
		builder.setNegativeButton("删除所有记录", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				deleteAllData();
				
			}
		});
		
		builder.setCancelable(true);
		builder.setPositiveButton("删除该条目", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				deletePointData(position);
			}
		});
		builder.show();
	}

	/**
	 * 删除所有操作记录;
	 */
	protected void deleteAllData() {
	
		SynSisSQLiteOpenHelper mSisSQLiteOpenHelper=SynSisSQLiteOpenHelper.getInstance(App.getAppContext());
		SQLiteDatabase db=mSisSQLiteOpenHelper.getReadableDatabase();
		 db.execSQL("delete from Syn_Sis_record where userName=?", new String[]{userName});
		 db.close();
		list.clear();
		mOprationAdapter.notifyDataSetChanged();
		Toast.makeText(App.getAppContext(), "已经删除所有记录", 500);
		
	}

	/**
	 * 删除指定条目操作记录
	 * @param position
	 */
	private void deletePointData(int position) {
		
		SynSisSQLiteOpenHelper mSisSQLiteOpenHelper=SynSisSQLiteOpenHelper.getInstance(App.getAppContext());
		SQLiteDatabase db=mSisSQLiteOpenHelper.getReadableDatabase();
		 db.execSQL("delete from Syn_Sis_record where userName=?", new String[]{list.get(position).getUserName().trim()});
		 db.close();
		list.remove(position);
		mOprationAdapter.notifyDataSetChanged();
		Toast.makeText(App.getAppContext(), "已经删除单条目记录", 500);
		
	}

}

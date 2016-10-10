package com.qypt.just_syn_asis_version1_0.sqlite;

import com.qypt.just_syn_asis_version1_0.app.App;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @author Administrator justson
 *WechatCardSQLiteOpenHelper
 */
public class WechatCardSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION=1;
	private static final String NAME="WECHAT_CARD_SQLITE";
	public static  WechatCardSQLiteOpenHelper mWechatCardSQLiteOpenHelper;
	public WechatCardSQLiteOpenHelper(Context context) {
		super(context,NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql="create table wechat_card_backup(id integer primary key autoincrement ,name,wechat_account)";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public  static WechatCardSQLiteOpenHelper getInstance(Context context)
	{
		if(mWechatCardSQLiteOpenHelper==null)
		{
			synchronized(WechatCardSQLiteOpenHelper.class)
			{
				if(mWechatCardSQLiteOpenHelper==null)
				mWechatCardSQLiteOpenHelper=new WechatCardSQLiteOpenHelper(context);
				context=null;
			}
		}
		return mWechatCardSQLiteOpenHelper;
	}

}

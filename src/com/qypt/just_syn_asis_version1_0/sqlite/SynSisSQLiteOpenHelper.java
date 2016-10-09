package com.qypt.just_syn_asis_version1_0.sqlite;

import com.qypt.just_syn_asis_version1_0.app.App;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//SynSisSQLiteOpenHelper 数据库
public class SynSisSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int version = 1;
	private static final String name = "Syn_Sis";
	public static SynSisSQLiteOpenHelper mSisSQLiteOpenHelper;

	public SynSisSQLiteOpenHelper(Context context, CursorFactory factory) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "create table Syn_Sis_record (id integer primary key autoincrement,userName,time,localNumber,cloudNumber)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static SynSisSQLiteOpenHelper getInstance(Context context) {
		if (mSisSQLiteOpenHelper == null) {
			synchronized (SynSisSQLiteOpenHelper.class) {

				if (mSisSQLiteOpenHelper == null) {
					mSisSQLiteOpenHelper = new SynSisSQLiteOpenHelper(context,
							null);
				}

			}

		}

		return mSisSQLiteOpenHelper;
	}

}

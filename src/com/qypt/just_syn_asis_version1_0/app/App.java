package com.qypt.just_syn_asis_version1_0.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
/**
 * 
 * @author Administrator justson(cen xiao zhong)
 *
 */
public class App extends Application {

	public static String NAME;
	
	public static final int SUECEECODE=1;
	
	public static App mApp;
	/**
	 * 获取全局变量context
	 * @return
	 */
	public static   Context getAppContext()
	{
		return mApp;
	}
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApp=(App) getApplicationContext();
	}
}

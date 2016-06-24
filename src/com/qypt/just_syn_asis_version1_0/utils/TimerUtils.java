package com.qypt.just_syn_asis_version1_0.utils;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
/**
 * 
 * @author Administrator justson
 *
 */
public class TimerUtils {

	
	
	private static SimpleDateFormat mDateFormat;

	public static String getDate(String format)
	{
		if(format==null||format.equals(""))
			throw new RuntimeException("time format is error must be eg: yy-MM-dd");
		
		long date=System.currentTimeMillis();
		if(mDateFormat==null){
			mDateFormat = new SimpleDateFormat(format);
		}
	
		
		
		return mDateFormat.format(date);
	}
	
	/**
	 * 输入一串时间，得到格式化的事件  为yy-MM-dd
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat") public static String getDateToForm(String time)
	{
		if(time==null)
			return null;
 
		
		return new SimpleDateFormat("yyyy-MM-dd").format(Long.parseLong(time));
	}
	
	
	
}

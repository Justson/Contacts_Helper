package com.qypt.just_syn_asis_version1_0.utils;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.*;
import com.qypt.just_syn_asis_version1_0.model.DataBaseBean;

//autor  justson
public class JsonUtils {

	
	//把json字符串转化为对象
	public static <T> T getEntityDataFromJson(String json,Class<T>cla)
	{
		T t=null;
		Gson mGson=new Gson();
		try {
			t=mGson.fromJson(json, cla);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return t;
		
	}
	
	public static <T> List<T> getListEntityDataFromJson(String json,Class<T>cla)
	{
		List<T>list=null;
		Gson mGson=null;
		Log.i("Info", "jsons:"+json);
		try {
			
			mGson=new Gson();
			list=mGson.fromJson(json,new TypeToken<List<DataBaseBean>>(){}.getType());
			
		} catch (JsonSyntaxException e) {
			// TODO: handle exception
		}finally{
			mGson=null;
			
		}
		return list;
	} 
	
	
	
}

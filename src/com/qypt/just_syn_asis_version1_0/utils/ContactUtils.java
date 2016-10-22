package com.qypt.just_syn_asis_version1_0.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;

import com.qypt.just_syn_asis_version1_0.activity.MainActivity;
import com.qypt.just_syn_asis_version1_0.model.Person;

/**
 * 获取系统的联系人通过list<map<String,Object>的形式返回
 * 
 * 获取联系人数目
 * @author Administrator justson
 * 
 */
public class ContactUtils {

	public static List<Map<String, Object>> getAllContactList(Context context) {

		List<Map<String, Object>> list = null;
		ContentResolver mContentResolver = context.getContentResolver();
		Cursor cursor = mContentResolver.query(Phone.CONTENT_URI, new String[] {
				Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			do {

				map = new HashMap<String, Object>();
				String name = cursor.getString(0);
				String number = cursor.getString(1);
				map.put("name", name);
				map.put("number", number);
				list.add(map);

			} while (cursor.moveToNext());
		}
		cursor.close();

		return list;
	}

	/**
	 * 插入联系人
	 * @param context
	 * @param list
	 */
	public static void handleContact(Context context,
			List<Map<String, Object>> list,int type) {
		ContentResolver mContentResolver = context.getContentResolver();
		
		for (int i = 0; i < list.size(); i++) {
			
			/**
			 * 生成新的联系人的ID
			 */
			Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
			ContentValues mContentValues = new ContentValues();
			Long contactId = ContentUris.parseId(mContentResolver.insert(uri,
					mContentValues));
			// 插入联系人姓名
			mContentValues.clear();
			uri = Uri.parse("content://com.android.contacts/data");
			mContentValues.put("raw_contact_id", contactId);
			mContentValues.put("mimetype", "vnd.android.cursor.item/name");
			mContentValues.put("data2", list.get(i).get("name").toString()
					.trim());
			mContentResolver.insert(uri, mContentValues);

//			Log.i("Info", "name:"+ list.get(i).get("name").toString());
//			Log.i("Info", "phone:"+ list.get(i).get("number").toString());
			// 插入联系人电话号码
			mContentValues.clear();
			mContentValues.put("raw_contact_id", contactId);
			mContentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
			mContentValues.put("data2", "2");
			mContentValues.put("data1", list.get(i).get("number").toString()
					.trim());
			mContentResolver.insert(uri, mContentValues);

			uri=null;
			mContentValues=null;
		}

		if(type==MainActivity.MAINACTIVITY)
		{
			Intent mIntent = new Intent();
			mIntent.setAction("com.qypt.just.syn_sis_main_contact_handle_finish");
			context.sendBroadcast(mIntent);
			Log.i("Info", "开始发送广播");
		}
		
		mContentResolver=null;
	}

	/**
	 * 获取联系人的个数
	 */
	public static int getContactNumber(Context context) {
		ContentResolver mContentResolver = context.getContentResolver();
		Cursor cursor = mContentResolver.query(Phone.CONTENT_URI,
				new String[] {Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		int number = 0;
		number = cursor.getCount();
		cursor.close();
		return number;
	}
	
	/** 
	 * 删除所有联系人
	 */
	public static boolean deleteAllContact(Context context)
	{
		if(context==null)
			throw new RuntimeException("context is null,please check you params");
		ContentResolver mContentResolver=context.getContentResolver();
		mContentResolver.delete(ContactsContract.RawContacts.CONTENT_URI, null, null);
		Cursor cursor = mContentResolver.query(Phone.CONTENT_URI, new String[] {
				Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		boolean tag=cursor.getCount()>0?false:true;
		cursor.close();
		return tag;
	}
	
	/**
	 * 获取SIM卡联系人
	 */
	public static<T> List<Map<String,T>> getSimCardContact(Context context)
	{
		List<Map<String,T>>list=null;
		Map<String,T>map=null;
		Uri uri = Uri.parse("content://icc/adn");  
		ContentResolver mContentResolver=context.getContentResolver();
		Cursor cursor=mContentResolver.query(uri, new String[]{Phone.DISPLAY_NAME,Phone.NUMBER}, null, null, null);
		if(cursor.getCount()>0)
			list=new ArrayList<Map<String,T>>();
		while(cursor.moveToNext())
		{
			String name=cursor.getString(0);
			if(name==null)
				continue;
			map=new HashMap<String, T>();
			map.put("name", (T) name);
			map.put("number", (T) (cursor.getString(1)==null?"":cursor.getString(1)));
			
			list.add(map);
			
		}
		if(cursor!=null){
			cursor.close();
		}
		
		return list;
		
	}
	/**
	 * 删除联系人
	 */
	public static boolean deletePointContact(List<Person>list,Context context)
	{
		
		boolean tag=false;
		if(list==null||list.isEmpty())
			return tag;
		
		ContentResolver mContentResolver=context.getContentResolver();
		ListIterator<Person> mInterator=list.listIterator();
		while(mInterator.hasNext())
		{
			Person person=mInterator.next();
			String name=person.getNameRe();//获取重复联系人的名字
			Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
			Cursor cursor=mContentResolver.query(uri, new String[]{Data._ID}, null, null, null);//根据联系人的名字获取联系人的ID
			if(cursor.moveToFirst())
			{
				String id=cursor.getString(0);
				if(id==null)
					return false;
				mContentResolver.delete(uri, "display_name=?", new String[]{name});//删除raw_contact表中的联系人
				
				uri=Uri.parse("content://com.android.contacts/data");
				mContentResolver.delete(uri, "raw_contact_id=?", new String[]{id}); //用id删除data表中联系人
			
				
			}
			if(cursor!=null){
				cursor.close();
			}
		}
		
		return true;
		
	}

}

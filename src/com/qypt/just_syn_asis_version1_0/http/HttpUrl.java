package com.qypt.just_syn_asis_version1_0.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

/**
 * 
 * 通过url访问的数据
 * 
 * @author Administrator justson
 * url
 */
public class HttpUrl {

	public static String postAcessUrl(String url, String params) {
		if (url == null || url.equals("") || params == null
				|| params.equals(""))
			return null;
		Log.i("Info", "获取~");
		URL mUrl = null;
		InputStream is = null;
		OutputStream os = null;
		StringBuffer sb = null;
		BufferedReader reader = null;
		try {
			mUrl = new URL(url);
			HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl
					.openConnection();
			mHttpURLConnection.setRequestMethod("POST");
			mHttpURLConnection.setConnectTimeout(5000);
			mHttpURLConnection.setDoInput(true);
			mHttpURLConnection.setDoOutput(true);

			mHttpURLConnection.setRequestProperty("Content-Type",
					"application/json");
			mHttpURLConnection.setRequestProperty("Accept", "application/json");
			mHttpURLConnection.setRequestProperty("Authorization", "token");
			mHttpURLConnection.setRequestProperty("tag", "htc_new");
			mHttpURLConnection.connect();

			Log.i("Info", "连接~");
			os = mHttpURLConnection.getOutputStream();
			os.write(params.getBytes("utf-8"));

			is = mHttpURLConnection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			sb = new StringBuffer();
			int len;
			String str = null;
			/**
			 * 读取失败， 乱码， 原因 未明
			 */
//			byte[] by = new byte[1024];
//			
//			 while((len=is.read(by))!=-1){
//			
//			 str=new String(by,"utf-8");
//			 sb.append(str);
//			 Log.i("Info", "str:"+str);
//			 str=null;
//			 }
			while ((str = reader.readLine()) != null) {
				Log.i("Info", "str:"+str);
				sb.append(str);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/**
			 * 这里处理一些流的释放和内存回收
			 */
			if (is != null)
				try {
					is.close();
					if (os != null)
						os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (mUrl != null)
				mUrl = null;

			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			// 通知GC回收一下
			System.gc();
		}

		return sb == null ? "" : sb.toString();
	}

}

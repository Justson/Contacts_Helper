package com.qypt.just_syn_asis_version1_0.model;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.qypt.just_syn_asis_version1_0.activity.RobotActivity;
import com.qypt.just_syn_asis_version1_0.http.HttpUrl;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;
/**
 * 
 * @author Administrator justson
 *
 */
public class RobotModel {

	
	private Gson mGson;
	private AsyncTaskWorker mAsyncTaskWorker;


	public RobotModel(){
		mGson = new Gson();
		
	}
	
	/**
	 *  把字符串封装成一个bean
	 * @param strMessage
	 * @return
	 */
	public RobotResultBean sendMessageToTuling(String strMessage)
	{
		
		if(strMessage==null||strMessage.equals(""))
			return null;
		MessageBean mMessageBean=new MessageBean();
		mMessageBean.setInfo(strMessage);
		mMessageBean.setKey("b1713efc987945038cbe54c10f87c55a");
		mMessageBean.setUserid("justson");
		String json=mGson.toJson(mMessageBean);
		
		RobotResultBean mRobotResultBean=null;
		String resultJson=null;
		
		mAsyncTaskWorker = new AsyncTaskWorker();
		try {
			resultJson=mAsyncTaskWorker.execute(json).get();
			if(resultJson!=null)
			{
				mRobotResultBean=JsonUtils.getEntityDataFromJson(resultJson, RobotResultBean.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return mRobotResultBean;
	}
	
	/**
	 * 
	 * 开启异步任务 访问网络
	 * @author Administrator justson  
	 *
	 */
	   class AsyncTaskWorker extends AsyncTask<String, Void, String>{

		
		@Override
		protected String doInBackground(String... params) {
			
			 
			return HttpUrl.postAcessUrl(UrlUtils.TULINGROBOT, params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
		
		
	}
	
	
	
}

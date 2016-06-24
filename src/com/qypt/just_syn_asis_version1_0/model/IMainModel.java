package com.qypt.just_syn_asis_version1_0.model;

import org.apache.http.client.utils.URIUtils;


import android.os.AsyncTask;
import android.util.Log;

import com.qypt.just_syn_asis_version1_0.http.HttpManager;
import com.qypt.just_syn_asis_version1_0.http.HttpManager.OkHttpCallBack;
import com.qypt.just_syn_asis_version1_0.presenter.MainPresenter;
import com.qypt.just_syn_asis_version1_0.presenter.MyPresenter;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;
import com.qypt.just_syn_asis_version1_0.view.MainView;

/**
 * 
 * @author Administrator justson
 *
 */
public class IMainModel  implements IModel{
	private String result=null;
	private MainPresenter mainPresenter;
	public IMainModel(MainPresenter mainPresenter)
	{
		this.mainPresenter=mainPresenter;
	}
	
	/**
	 * 下载文件
	 * @param userName
	 */
	public void downData(String userName)
	{

		HttpManager.getInstance().asnyDwonLaodingForOut(userName,UrlUtils.DOWNDATA, new OkHttpCallBack() {

			@Override
			public void handleSucess(Object t) {
				result=t.toString();
				
				if(result==null)
				{
					mainPresenter.loadingFail();
				}
				else
				{
					mainPresenter.loaddingSucess(result);
				}
				
			}

			@Override
			public void handleFail(Object t) {
				result=null;
				mainPresenter.loadingFail();
			}
		});
	}
	
	/**
	 * 上传文件
	 * @param filePath
	 * @param userName
	 * @param number
	 */
	public void upLoadData(String filePath,String userName,int number)
	{
		
		
		HttpManager.getInstance().asnyUpLoadingForOut(filePath, UrlUtils.UPDATA, new OkHttpCallBack<String>() {

			@Override
			public void handleSucess(String t) {
					
				 if(t==null||t.equals(""))
					 return;
				 UpLoadBean mUpLoadBean=JsonUtils.getEntityDataFromJson(t, UpLoadBean.class);
				 mainPresenter.upLoadSucess(mUpLoadBean);
			}

			@Override
			public void handleFail(String t) {
				
			}
		}, userName,number);
		
	}
	
	
	//
	class AsynTaskWorkLoadding extends AsyncTask<String, Void, String>
	{

		private String result="ss";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			
			
			
			
			
			Log.i("Info", "result:"+result);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
		 
			Log.i("Info", "result:"+result);
			
			
		}
	}
	
}

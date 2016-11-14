package com.qypt.just_syn_asis_version1_0.model;

import android.util.Log;

import com.qypt.just_syn_asis_version1_0.http.HttpManager;
import com.qypt.just_syn_asis_version1_0.http.HttpManager.OkHttpCallBack;
import com.qypt.just_syn_asis_version1_0.presenter.RetrievePresenter;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;
/**
 * 
 * @author Administrator justson
 *  cxz
 */
public class RetrieveModel implements IModel, OkHttpCallBack<String> {

	
	private RetrievePresenter mRetrievePresenter;
	public RetrieveModel(RetrievePresenter mRetrievePresenter)
	{
		this.mRetrievePresenter=mRetrievePresenter;
	}
	/**
	 * 访问网络获取数据
	 */
	public void getData(TaskBean mTaskBean)
	{
		HttpManager mHttpManager=HttpManager.getInstance();
		mHttpManager.asynSubmitMessageToService(mTaskBean, UrlUtils.CONTACTMENORY, this, "contactmemory");
	}
	@Override
	public void handleSucess(String t) {
		
		mRetrievePresenter.getDataSucess(t);
	}
	@Override
	public void handleFail(String t) {

	}
	
}

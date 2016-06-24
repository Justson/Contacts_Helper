package com.qypt.just_syn_asis_version1_0.model;

import android.util.Log;

import com.qypt.just_syn_asis_version1_0.http.HttpManager;
import com.qypt.just_syn_asis_version1_0.http.HttpManager.OkHttpCallBack;
import com.qypt.just_syn_asis_version1_0.presenter.FeedBackPresenter;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;

public class FeedbackMode implements IModel,OkHttpCallBack<String> {

	private FeedBackPresenter mFeedBackPresenter;
	public FeedbackMode(FeedBackPresenter mFeedBackPresenter)
	{
		this.mFeedBackPresenter=mFeedBackPresenter;
		
	}
	public void sendFeedbackMessage(TaskBean mTaskBean)
	{
		if(mTaskBean==null)
			return;
		
		Log.i("Info", "发送消息");
		HttpManager mHttpManager=HttpManager.getInstance();
		mHttpManager.asynSubmitMessageToService(mTaskBean, UrlUtils.FEEDBACK, this, "feedback");
		
	}

	@Override
	public void handleSucess(String t) {
		
		Log.i("Info", "成功");
		mFeedBackPresenter.handleSucess(t);
	}

	@Override
	public void handleFail(String t) {
		mFeedBackPresenter.handleFail(t);
	}
	
}

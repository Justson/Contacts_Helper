package com.qypt.just_syn_asis_version1_0.presenter;

import com.qypt.just_syn_asis_version1_0.model.FeedbackMode;
import com.qypt.just_syn_asis_version1_0.model.TaskBean;
import com.qypt.just_syn_asis_version1_0.view.MainView;

public class FeedBackPresenter implements FeedbackModelPresenter,MyPresenter<MainView> {

	
	 
	
	private MainView mainView;
	private FeedbackMode mFeedbackMode;

	@Override
	public void attachView(MainView t) {
		
		mainView = t;
		mFeedbackMode = new FeedbackMode(this);
	}

	@Override
	public void dispatchView(MainView t) {
		
		mainView=null;
	}

	
	public void dispatchTask(TaskBean mTaskBean)
	{
		mainView.showProgressBar();
		mFeedbackMode.sendFeedbackMessage(mTaskBean);
	}

	@Override
	public <T> void handleSucess(T t) {
		if(mainView!=null)
		{
			mainView.hintProGrossBar();
			mainView.setData(t);
		}
	}

	@Override
	public <T> void handleFail(T t) {
		
		mainView.hintProGrossBar();
	}
 

 

}

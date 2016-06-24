package com.qypt.just_syn_asis_version1_0.presenter;

import com.qypt.just_syn_asis_version1_0.model.RegisterMode;
import com.qypt.just_syn_asis_version1_0.model.RetrieveModel;
import com.qypt.just_syn_asis_version1_0.model.TaskBean;
import com.qypt.just_syn_asis_version1_0.view.MainView;
/**
 * 
 * @author Administrator justson
 *
 */
public class RetrievePresenter  implements MyPresenter<MainView> ,RetrieveModelPresenter {


	
	private RetrieveModel mRetrieveModel;
	private MainView mMainView;

	public RetrievePresenter()
	{
		
		mRetrieveModel = new RetrieveModel(this);
	}

	
	public <T  extends  TaskBean>void dispatchTask(T t)
	{
		mMainView.showProgressBar();
 		mRetrieveModel.getData(t);
	}
	
	@Override
	public void attachView(MainView t) {
		mMainView = t;
	}

	@Override
	public void dispatchView(MainView t) {
		mMainView=null;
	}

	@Override
	public <T> void getDataSucess(T t) {
		if(mMainView==null)
			return;
		mMainView.setData(t);
		mMainView.hintProGrossBar();
		
	}

	@Override
	public void getDataFail() {
		if(mMainView==null)
			return;
		mMainView.hintProGrossBar();
	}

}

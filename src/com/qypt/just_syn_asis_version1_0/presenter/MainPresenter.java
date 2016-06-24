package com.qypt.just_syn_asis_version1_0.presenter;

import android.util.Log;

import com.qypt.just_syn_asis_version1_0.model.IMainModel;
import com.qypt.just_syn_asis_version1_0.model.UpLoadBean;

import com.qypt.just_syn_asis_version1_0.view.MainView;

/**
 * model--presenter---view
 * 
 * @author Administrator justson
 * 
 */
public class MainPresenter implements IModePresenter, MyPresenter<MainView> {

	private MainView mainView;
	private IMainModel mIMainModel;

	public MainPresenter(MainView mainView) {
		this.mainView = mainView;
		mIMainModel = new IMainModel(this);
	}

	/**
	 * 下载后的回调
	 * @param userName
	 */
	@Override
	public void loaddingSucess(String result) {
//		mainView.hintProGrossBar();
		mainView.downLoad(result);
		Log.i("Info", "下载成功");
	}

	public <T extends UpLoadBean> void upLoadSucess(T t) {
		mainView.hintProGrossBar();
		mainView.setData(t);
	}

	@Override
	public void loadingFail() {

		mainView.hintProGrossBar();
		Log.i("Info", "下载失败");

	}


	public void LoaddinngData(String userName) {
		Log.i("Info", "开始动画");
		mainView.showProgressBar();
		mIMainModel.downData(userName);
	}

	/**
	 * 上传成功回调
	 * 
	 * @param userName
	 * @param filePath
	 */
	public void upLoadData(String userName, String filePath,int number) {
		mainView.showProgressBar();
		mIMainModel.upLoadData(filePath, userName,number);
	}

	@Override
	public void attachView(MainView t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispatchView(MainView t) {
		// TODO Auto-generated method stub
		this.mainView = null;
	}

}

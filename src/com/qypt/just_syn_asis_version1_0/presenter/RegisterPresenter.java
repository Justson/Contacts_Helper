package com.qypt.just_syn_asis_version1_0.presenter;

import java.io.IOException;

import android.util.Log;

import com.qypt.just_syn_asis_version1_0.model.RegisterMode;
import com.qypt.just_syn_asis_version1_0.view.MainView;
import com.qypt.just_syn_asis_version1_0.view.RegisterView;
/**
 * 
 * @author Administrator justson
 * RegisterPresenter 
 */
public class RegisterPresenter implements MyPresenter<RegisterView> ,IModePresenter {

	private RegisterView mRegisterView;
	private RegisterMode mRegisterMode;

	public RegisterPresenter(){
		mRegisterMode = new RegisterMode(this);
	}
	
	//分发任务
	public void dispatchTast(String userName,String phone,String password)
	{
		mRegisterView.setProgressBarShow();
		try {
			mRegisterMode.toServiceRegisterAccount(userName, password, phone);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//分发任务
	public void dispatchTast_login(String userName,String phone,String password)
	{
		mRegisterView.setProgressBarShow();
		try {
			Log.i("Info", "toServiceMatchAccount");
			mRegisterMode.toServiceMatchAccount(userName, password, phone);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void attachView(RegisterView mRegisterView) {
		this.mRegisterView=mRegisterView;
	}

	@Override
	public void dispatchView(RegisterView t) {
		this.mRegisterView=null;
	}

	@Override
	public void loaddingSucess(String result) {
		
		if(result==null||mRegisterView==null)
			return;
		mRegisterView.setData(result);
		mRegisterView.setProgressBarhint();
		
	}

	@Override
	public void loadingFail() {
		
	}

}

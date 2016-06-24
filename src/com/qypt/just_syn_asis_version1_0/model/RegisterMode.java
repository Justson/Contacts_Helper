package com.qypt.just_syn_asis_version1_0.model;

import java.io.IOException;

import android.util.Log;

import com.qypt.just_syn_asis_version1_0.http.HttpManager;
import com.qypt.just_syn_asis_version1_0.http.HttpManager.OkHttpCallBack;
import com.qypt.just_syn_asis_version1_0.presenter.RegisterPresenter;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;

public class RegisterMode implements IModel {

	private RegisterPresenter mRegisterPresenter;

	public RegisterMode(RegisterPresenter mRegisterPresenter) {

		this.mRegisterPresenter = mRegisterPresenter;
	}

	public void toServiceRegisterAccount(String userName,String password,String phone) throws IOException
	{
		
		Log.i("Info", "register");
		HttpManager.getInstance().synLogin_(userName, password, phone, UrlUtils.REGISTER, new OkHttpCallBack<String>() {
			
			@Override
			public void handleSucess(String json) {
				
				mRegisterPresenter.loaddingSucess(json);
				
			}
			
			@Override
			public void handleFail(String t) {
				mRegisterPresenter.loaddingSucess(null);
			}
		});
	}
	
	public void toServiceMatchAccount(String userName,String password,String phone) throws IOException
	{
		
		Log.i("Info", "match");
		HttpManager.getInstance().synLogin_(userName, password, phone, UrlUtils.LOGIN, new OkHttpCallBack<String>() {
			
			@Override
			public void handleSucess(String json) {
				
				mRegisterPresenter.loaddingSucess(json);
				
			}
			
			@Override
			public void handleFail(String t) {
				mRegisterPresenter.loaddingSucess(null);
			}
		});
	}
	
}

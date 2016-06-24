package com.qypt.just_syn_asis_version1_0.view;

public interface MainView {

	void showProgressBar();
	void hintProGrossBar();
	<T> void setData(T t);
	void downLoad(String result);
}

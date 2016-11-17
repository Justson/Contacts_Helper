package com.qypt.just_syn_asis_version1_0.presenter;
/**
 * 
 * @author Administrator  justson
 * 
 * 次Presenter 主要用来连接View
 * cxz
 */
public interface MyPresenter<T> {
    //attach
	void attachView(T t);
	void dispatchView(T t);
	
	
}

package com.qypt.just_syn_asis_version1_0.presenter;
/**
 * 
 * @author Administrator justson
 *mvp --p
 */
public interface FeedbackModelPresenter {

	<T>void  handleSucess(T t);
	<T>void handleFail(T t);
}

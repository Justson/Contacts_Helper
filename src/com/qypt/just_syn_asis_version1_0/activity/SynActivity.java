package com.qypt.just_syn_asis_version1_0.activity;

import android.app.Activity;
import android.test.ActivityTestCase;
/**
 * 
 * @author Administrator justson
 * 基类
 */
public abstract class SynActivity extends Activity {

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initViewData();
		
	};

	
	protected abstract void initView();
	protected abstract void initViewData();
	
}

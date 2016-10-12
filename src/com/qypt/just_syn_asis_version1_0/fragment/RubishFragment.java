package com.qypt.just_syn_asis_version1_0.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
 //RubishFragment laji
public class RubishFragment  extends Fragment{

	
	private View view;
	private LinearLayout no_net_rubbish_linearlayout;
	private RelativeLayout net_rubbish_relativelayout;
	private TextView rubish_rubish;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_rubbish, null);
		initView();
		initdata();
		return view;
	}

	private void initdata() {
		
		if(!NetWorkHepler.netWorkIsReady(App.getAppContext()))
		{
			net_rubbish_relativelayout.setVisibility(View.GONE);
			no_net_rubbish_linearlayout.setVisibility(View.VISIBLE);
			rubish_rubish.setVisibility(View.GONE);
		}
		else
		{
			net_rubbish_relativelayout.setVisibility(View.GONE);
			no_net_rubbish_linearlayout.setVisibility(View.GONE);
			rubish_rubish.setVisibility(View.VISIBLE);
			
		}
		
	}

	private void initView() {
		
		no_net_rubbish_linearlayout = (LinearLayout) view.findViewById(R.id.no_net_rubbish_linearlayout);
		net_rubbish_relativelayout = (RelativeLayout) view.findViewById(R.id.net_rubbish_relativelayout);
		rubish_rubish = (TextView) view.findViewById(R.id.rubish_rubish);
	}
}

package com.qypt.just_syn_asis_version1_0.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.activity.RetrieveActivity;
import com.qypt.just_syn_asis_version1_0.adapter.RetrieveCommAdapter;
import com.qypt.just_syn_asis_version1_0.model.DataBaseBean;
import com.qypt.just_syn_asis_version1_0.model.EventBussMessage;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;

import de.greenrobot.event.EventBus;
//
public class MemoryFragment extends Fragment {

	private View view;
	private boolean tag;
	private ListView memory_listview;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_memory, null);
		EventBus.getDefault().register(this);//注册EvenBus
		initView();
		initData(tag);
		return view ;
	}

	/**
	 * 加载数据
	 * @param tag_
	 */
	private void initData(boolean tag_) {
		
	}

	/**
	 * 初始化控件Fragment
	 */
	private void initView() {
		
		tag = NetWorkHepler.netWorkIsReady(getActivity());
		
		RelativeLayout mRelativeLayout=(RelativeLayout) view.findViewById(R.id.net_memory_relativelayout);
		LinearLayout mLinearLayout=(LinearLayout) view.findViewById(R.id.no_net_memory_linearlayout);
		
		if(tag==true)
		{
			mRelativeLayout.setVisibility(View.VISIBLE);
			mLinearLayout.setVisibility(View.GONE);
		}
		else
		{
			mRelativeLayout.setVisibility(View.GONE);
			mLinearLayout.setVisibility(View.VISIBLE);
			Log.i("Info", "没有网络");
		}
		memory_listview = (ListView) view.findViewById(R.id.memory_listview);
		
	}
	
	public void onEventMainThread(EventBussMessage mEventBussMessage){
		
		if(mEventBussMessage==null||mEventBussMessage.getSubscriber()!=RetrieveActivity.RETRIEVEACTIVITY)
			return;
		
		List<DataBaseBean>list=(List<DataBaseBean>) mEventBussMessage.getData();
		if(list==null||list.isEmpty())
			return;
		RetrieveCommAdapter mRetrieveCommAdapter=new RetrieveCommAdapter(getActivity(), list);
		memory_listview.setAdapter(mRetrieveCommAdapter);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}

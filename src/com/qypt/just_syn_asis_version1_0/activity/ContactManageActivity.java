package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.qypt.just_syn_asis_version1_0.adapter.MyBaseAdapter;
import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;
/**
 * 
 * @author Administrator justson
 *	c
 */
public class ContactManageActivity extends Activity implements OnLongClickListener, OnItemClickListener, OnClickListener {

	
	private Button leftButton;
	private ListView myListView;
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_contactmanage);
		initView();
		initBackground();
	}

	private void initBackground() {
		
		leftButton.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.getBitmapbyConfig(this, 20, 15, R.drawable.lefe)));
		
	}

	private void initView() {
		
		mIntent=new Intent();

		leftButton = (Button) findViewById(R.id.leftButton);
		leftButton.setOnClickListener(this);
		myListView = (ListView) findViewById(R.id.contact_manage_listview);
		
		List<Map<String,Object>>list=null;
		list=getDataToListView();
		
		MyBaseAdapter adapter=new MyBaseAdapter(this, list);
		myListView.setAdapter(adapter);
		myListView.setOnLongClickListener(this);
		myListView.setOnItemClickListener(this);
		
	}

	/**
	 * 
	 * @return 初始化Item 数据
	 */
	private List<Map<String, Object>> getDataToListView() {

		int []image_=new int[]{R.drawable.conbine_contact,R.drawable.clean_unable_contact,R.drawable.import_card,
				R.drawable.sim_card,R.drawable.laji,R.drawable.share_contact,R.drawable.wechat};
		String[]text_=new String[]{"合并重复联系人","清理失效号码","SD卡导入导出","导入SIM卡联系人","清空通讯录","联系人分享","收集微信名片"};
		List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
		Map<String,Object>map=null;
		for(int i=0;i<image_.length;i++)
		{
			map=new HashMap<String, Object>();
			map.put("image", image_[i]);
			map.put("text", text_[i]);
			list.add(map);
			
		}
		
		
		
		return list;
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}
	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	/**
	 * 跳转界面
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		switch (position) {
		case 0:
			mIntent.setClass(this, CombineContactActivity.class);
			startActivityForResult(mIntent, 0);
			break;
		
		case 1:
			mIntent.setClass(this, ExpiredActivity.class);
			startActivityForResult(mIntent, 1);
			
			break;
		case 2:
			mIntent.setClass(this, SDCard_In_Out_Activity.class);
			startActivityForResult(mIntent, 2);
			
			break;
		case 3:
			mIntent.setClass(this, ImportSimCardActivity.class);
			startActivityForResult(mIntent, 3);
			
			break;
		case 4:
			mIntent.setClass(this, CleanAllContactActivity.class);
			startActivityForResult(mIntent, 4);
			
			break;
		case 5:
			mIntent.setClass(this, ShareActivity.class);
			startActivityForResult(mIntent, 5);
			
			break;
		case 6:
			mIntent.setClass(this, CollectWechatCardActivity.class);
			startActivityForResult(mIntent,6);
			break;

		 
		}
		
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.leftButton){
			this.finish();
		}
	}
	
}

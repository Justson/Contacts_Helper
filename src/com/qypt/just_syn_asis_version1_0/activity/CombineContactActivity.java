package com.qypt.just_syn_asis_version1_0.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.adapter.CombineBaseAdapter;
import com.qypt.just_syn_asis_version1_0.adapter.CombineSucessAdapter;
import com.qypt.just_syn_asis_version1_0.model.EventBussMessage;
import com.qypt.just_syn_asis_version1_0.model.Person;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;

import de.greenrobot.event.EventBus;
/**
 * 
 * @author Administrator justson
 *
 */
public class CombineContactActivity extends Activity implements OnClickListener {

	private TextView no_combine_textview;
	private RelativeLayout have_combine_relativelayout;
	private boolean haveCombineContact = false;
	private List<Person> combine_list;
	private ListView listview;
	private TextView combine_textview;
	private MyHandler myHandler;
	private DialogUtils mDialogUtils;
	 

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_combine_contact);
		//注册eventbus
		EventBus.getDefault().register(this);
		myHandler=new MyHandler(this);
		initView();
		initData();
		initlistview();
		init();
	}

	private void init() {
		mDialogUtils = new DialogUtils(this);
	}

	//初始化listview
	private void initlistview() {

		if (combine_list == null || combine_list.isEmpty()) {
			no_combine_textview.setVisibility(View.VISIBLE);
			have_combine_relativelayout.setVisibility(View.GONE);
			return;
		}

		no_combine_textview.setVisibility(View.GONE);
		have_combine_relativelayout.setVisibility(View.VISIBLE);
		CombineBaseAdapter<Person> mCombineBaseAdapter = new CombineBaseAdapter<Person>(
				this, combine_list);
		listview.setAdapter(mCombineBaseAdapter);

	}

	private void initData() {

		List<Map<String, Object>> list = ContactUtils.getAllContactList(this);
		int count = 1;
		boolean isFirst = true;
		if (list == null || list.isEmpty()) {
			haveCombineContact = false;
			return;
		}
		combine_list = new ArrayList<Person>();
		Person person = null;
		// 用冒泡排序法找到是否有相同的号码
		for (int i = 0; i < list.size(); i++) {
			person = new Person();
			for (int j = i+1; j < list.size() - 1; j++) {
				if (!list.get(i).get("number").toString()
						.equals(list.get(j).get("number").toString()))
					continue;
				if (isFirst) {
					person.setName(list.get(i).get("name").toString());
					person.setPhone(list.get(i).get("number").toString());
					isFirst = false;
				}

				person.setNameRe(list.get(j).get("name").toString());
				person.setPhoneRe(list.get(j).get("number").toString());
				person.setCount(count);
				combine_list.add(person);
				++count;
			}
			person = null;
			count = 1;
			isFirst = true;
		}

	}

	
	//初始化Eventbus
	public void onEventMainThread(EventBussMessage mEventBussMessage)
	{
		
	}
	
	private void initView() {

		listview = (ListView) findViewById(R.id.combine_listview);
		combine_textview = (TextView) findViewById(R.id.combine_textview);
		combine_textview.setOnClickListener(this);
		no_combine_textview = (TextView) findViewById(R.id.no_combine_textview);
		have_combine_relativelayout = (RelativeLayout) findViewById(R.id.have_combine_relativelayout);
		ImageView iv1=(ImageView) findViewById(R.id.iv1);
		iv1.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.combine_textview)
		{
			if(combine_list==null||combine_list.isEmpty())
				return;
			deletePonitContact(combine_list);
			
		}
		if(v.getId()==R.id.iv1){
			this.finish();
		}
		
		
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	//删除相应的联系人
	private void deletePonitContact(final List<Person> combine_list_) {
		
		mDialogUtils.showDialogMessage("正在合并...");
		new Thread(){
			@Override
			public void run() {
				
				boolean tag=ContactUtils.deletePointContact(combine_list_, CombineContactActivity.this);
				if(tag==true)
				{
					myHandler.sendEmptyMessageDelayed(0x33,2000);//删除成功发送一个空消息
					//发送一个消息，通知MainActivity同步联系人
					EventBus.getDefault().post(new EventBussMessage.Builder().setMessage("合并联系人成功!").setSubscriber(1).build());
				}
				else
				{
					myHandler.sendEmptyMessageDelayed(0x66,2000);
				}
			}
		}.start();
		
	}
	
	/**
	 * 内部类handler  用WeakReference 防止activity在finish的时候内存leak
	 */
	class MyHandler extends Handler{
		
		WeakReference<Context>context=null;
		
		public MyHandler (Context context)
		{
			this.context=new WeakReference<Context>(context);
		}
		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what==0x33)
			{
				Toast.makeText(context.get(), "合并成功...", Toast.LENGTH_LONG).show();
				mDialogUtils.dismissDialog_();
				listview.setAdapter(new CombineSucessAdapter<Person>(CombineContactActivity.this, combine_list));
			}
			else if(msg.what==0x66)
			{
				Toast.makeText(context.get(), "合并成功...", Toast.LENGTH_LONG).show();
				mDialogUtils.dismissDialog_();
			}
		}
	}

}

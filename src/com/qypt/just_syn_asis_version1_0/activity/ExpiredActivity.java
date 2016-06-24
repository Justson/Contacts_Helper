package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AlphabetIndexer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.adapter.MyBaseAdapterWithCommAdapter;
import com.qypt.just_syn_asis_version1_0.custom_view.LetterView;
import com.qypt.just_syn_asis_version1_0.model.Person;
import com.qypt.just_syn_asis_version1_0.model.PhoneMessage;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
/**
 * 
 * @author Administrator justson
 *
 */
public class ExpiredActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private ListView myListView;
	private char[] mChar;
	private SectionIndexer mIndexer;
	private LetterView mLetterView;
	private TextView showLetter;
	private List<Person>mList=new ArrayList<Person>();
	private MyBaseAdapterWithCommAdapter mMyBaseAdapterWithCommAdapter;
	private List<PhoneMessage> list_;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_expired);
		initView();
		initListView();
	}

	/**
	 * 初始化listview数据
	 */
	private void initListView() {
		initChar();
		List<PhoneMessage>list=initListViewItemData();
		if(list!=null)
		{
			mMyBaseAdapterWithCommAdapter = new MyBaseAdapterWithCommAdapter(this, list);
			mMyBaseAdapterWithCommAdapter.setIndexer(mIndexer);
			mMyBaseAdapterWithCommAdapter.setList(mList);
			myListView.setAdapter(mMyBaseAdapterWithCommAdapter);
		}
	}

	private void initView() {

		ImageView image = (ImageView) findViewById(R.id.shixiaoibt1);
		image.setOnClickListener(this);
		myListView = (ListView) findViewById(R.id.expired_listview);
		mLetterView = (LetterView) this
				.findViewById(R.id.letterView);
		mLetterView.setOnTouchListener(this);
		showLetter = (TextView) findViewById(R.id.showLetter);
		TextView expired_clear_textview=(TextView) findViewById(R.id.expired_clear_textview);
		expired_clear_textview.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.shixiaoibt1) {
			this.finish();
		}
		if(v.getId()==R.id.expired_clear_textview){
			 Log.i("Info", "开始清理...");
			 if(mList==null)
				 return;
			 Log.i("Info", "开始清理");
			  if(ContactUtils.deletePointContact(mList, this))
			  {
				  handlerList(mList,list_);
				  myListView.setAdapter(mMyBaseAdapterWithCommAdapter);
				  Toast.makeText(this.getApplicationContext(), "清理成功！", Toast.LENGTH_SHORT).show();
			  }
			  else
			  {
				  
				  Log.i("Info", "清理失败");
				  Log.i("Info", "list:"+mList);
			  }
		}
	}

	/**
	 *  相同联系人 删除该条目
	 * @param mList2
	 * @param list_2
	 */
	private void handlerList(List<Person> mList_, List<PhoneMessage> list__) {
		
//		int mListCount=mList_.size();
//		int listCount=list__.size();
		for(int i=0;i<mList_.size();i++)
		{
			for(int j=0;j<list__.size();j++)
			{
				if(mList_.get(i).getNameRe().equals(list__.get(j).getName())
						&&mList_.get(i).getPhoneRe().equals(list__.get(j).getPhone()))
				{
					list__.remove(j);
					break;
				}
			}
		}
		
	}

	private List<PhoneMessage> initListViewItemData() {

		/**
		 * 初始化listView 每一个Item的数据
		 */
		ContentResolver mContentResolver = this.getContentResolver();
		/**
		 * "sort_key" 排序按照A-Z排
		 */
		Cursor cursor = mContentResolver.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { "display_name", "sort_key" ,Phone.NUMBER}, null, null,
				"sort_key");
		list_ = new ArrayList<PhoneMessage>();
		mIndexer = new AlphabetIndexer(cursor, 1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ#");
		if (cursor.moveToFirst()) {
			// Log.i("Info","有数据");
			do {
				PhoneMessage phoneMessage = new PhoneMessage();
				phoneMessage.setName(cursor.getString(0));
				phoneMessage.setKey(createKey(cursor.getString(1)));
				phoneMessage.setPhone(cursor.getString(2));
				list_.add(phoneMessage);
			} while (cursor.moveToNext());

		}

		return list_;
	}

	private String createKey(String key) {
		if (key == null)
			return null;
		// 吧返回来的字符创裁剪，然后转化成大写
		key = key.substring(0, 1).toUpperCase();
		if (key.matches("[A-Z]")) {
			return key;
		}

		return "#";
	}

	/**
	 * 初始化字母表
	 */
	private void initChar() {

		mChar = new char[27];
		for (int i = 0; i < mChar.length; i++) {
			mChar[i] = (char) ('A' + i);
		}
		mChar[mChar.length - 1] = '#';
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getId() == R.id.letterView) {
			int lastX = (int) event.getX();
			int lastY = (int) event.getY();

			int vHeight = v.getHeight();

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int position = (int) lastY * mChar.length / vHeight;
				int sectionPosition = mIndexer.getPositionForSection(position);
				showLetter.setText(mChar[position] + "");
				showLetter.setVisibility(View.VISIBLE);
				myListView.setSelection(sectionPosition);

			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {

				int moveY = (int) event.getY();
				if(moveY<=0)
					return true;
				int position = (int) moveY * mChar.length / vHeight;
				int sectionPosition = mIndexer.getPositionForSection(position);
				showLetter.setText(mChar[position] + "");
				myListView.setSelection(sectionPosition);

			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				showLetter.setVisibility(View.GONE);
			}

		}

		return true;
	}

}

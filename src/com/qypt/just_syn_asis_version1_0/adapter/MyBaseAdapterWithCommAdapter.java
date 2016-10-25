package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.model.Person;
import com.qypt.just_syn_asis_version1_0.model.PhoneMessage;

/**
 * Created by Administrator justson on 2016/4/14. 
 */
public class MyBaseAdapterWithCommAdapter extends CommAdapter<PhoneMessage> {

	private SectionIndexer indexer;
	private List<Person> mList;
	private List<PhoneMessage> list;
	private SparseArray<Boolean> mSparseArray;

	public MyBaseAdapterWithCommAdapter(Context context, List<PhoneMessage> list) {
		super(context, list);
		this.list = list;

		mSparseArray = new SparseArray<Boolean>();
		 
	}

	@Override
	protected void changedDataViewHolder(ViewHolder mViewHolder,
			PhoneMessage phoneMessage, int position) {

		// x
		int section = indexer.getSectionForPosition(position);
		Log.i("Info", "section:" + section);
		LinearLayout mLinearLayout = mViewHolder
				.getView(R.id.navigateLinearLayout);
		if (position == indexer.getPositionForSection(section)) {

			mLinearLayout.setVisibility(View.VISIBLE);
			TextView textView = (TextView) mLinearLayout.getChildAt(0);
			textView.setText(phoneMessage.getKey());
		} else {
			mLinearLayout.setVisibility(View.GONE);
		}
		if (mViewHolder == null)
			return;

		ViewHolder viewHolder = mViewHolder.setData(R.id.item_name_expired,
				phoneMessage.getName());
		mViewHolder.setData(R.id.item_number_expired, phoneMessage.getPhone());
		final CheckBox mCheckBox = mViewHolder.getView(R.id.item_checkbox);
		mCheckBox.setChecked(mSparseArray.get(position) == null ? false
				: mSparseArray.get(position));
		Log.i("Info", "position:" + position);
		mCheckBox.setTag(position);
		mCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mCheckBox.isChecked()) {

					int position = (Integer) mCheckBox.getTag();
					mSparseArray.put(position, true);
					Log.i("Info", "check:" + mSparseArray.get(position));
					Person mPerson = new Person();
					mPerson.setNameRe(list.get(position).getName() + "");
					mPerson.setPhoneRe(list.get(position).getPhone() + "");
					mList.add(mPerson);
					mPerson = null;
//					list.remove(position);
				}else
				{
					mSparseArray.setValueAt((Integer) mCheckBox.getTag(), false);
				}
				
			}
		});
	}

	public void setIndexer(SectionIndexer indexer) {
		this.indexer = indexer;
	}

	public <T> void setList(T t) {
		mList = (List<Person>) t;
	}

	@Override
	public int getLayoutResId() {
		
		return R.layout.listview_item_expired;
	}

}

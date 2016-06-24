package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.model.Person;
/**
 * 
 * @author Administrator justson
 *
 * @param <T>
 */
public class CombineBaseAdapter<T>  extends BaseAdapter{

	private List<T>list;
    private	Context context;
    private final int NUMBER=1;
	
	public CombineBaseAdapter(Context context,List<T>list)
	{
		this.list=list;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		Person person=(Person) list.get(position);
		 
			ViewHolder viewHolder=null;
			if(convertView==null)
			{
				viewHolder=new ViewHolder();
				LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView=mInflater.inflate(R.layout.listview_item_combine, null);
				viewHolder.name=(TextView) convertView.findViewById(R.id.name_item);
				viewHolder.name_=(TextView) convertView.findViewById(R.id.name_item_);
				viewHolder.phone=(TextView) convertView.findViewById(R.id.number_item);
				viewHolder.phone_=(TextView) convertView.findViewById(R.id.number_item_);
				convertView.setTag(viewHolder);
			}
			viewHolder=(ViewHolder) convertView.getTag();
			viewHolder.name.setText(person.getName());
			viewHolder.name_.setText(person.getNameRe());
			viewHolder.phone.setText(person.getPhone());
			viewHolder.phone_.setText(person.getPhoneRe());
	 
		
		
		return convertView;
	}
	
	
	public static class ViewHolder
	{
		
		TextView name,name_,phone,phone_;
		
	}

}

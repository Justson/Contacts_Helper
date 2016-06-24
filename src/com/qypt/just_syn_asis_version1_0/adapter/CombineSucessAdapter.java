package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;
import com.qypt.just_syn_asis_version1_0.model.Person;

public class CombineSucessAdapter<T>  extends BaseAdapter{

	
	private List<T>list;
    private	Context context;
	
	public CombineSucessAdapter(Context context,List<T>list)
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
		
		ViewHolder viewHolder=null;
		Person person=(Person) list.get(position);
		if(convertView==null)
		{
			LayoutInflater mInlayoutInflater=LayoutInflater.from(context);
			convertView=mInlayoutInflater.inflate(R.layout.listview_item_sucess_conbine, null);
			viewHolder=new ViewHolder();
			viewHolder.image=(ImageView) convertView.findViewById(R.id.image_listview_item_sucess);
			viewHolder.name=(TextView) convertView.findViewById(R.id.name_listview_item_sucess);
			viewHolder.phone=(TextView) convertView.findViewById(R.id.phone_listview_item_sucess);
			convertView.setTag(viewHolder);
			
		}
		
		viewHolder=(ViewHolder) convertView.getTag();
		viewHolder.name.setText(person.getName());
		viewHolder.phone.setText(person.getPhone());
		viewHolder.image.setImageBitmap(BitmapUtils.getBitmapbyConfig(context, 20, 20, R.drawable.combine_sucess));
		
		return convertView;
	}
	
	
	public static class ViewHolder
	{
		
		TextView name,name_,phone,phone_;
		ImageView image;
		
	}

	
}

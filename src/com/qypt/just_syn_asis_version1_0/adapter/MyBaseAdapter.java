package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.bitmapUtils.BitmapUtils;
 
/**
 * 
 * @author Administrator justson
 * base
 */
public class MyBaseAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String,Object>>list;
	public MyBaseAdapter(Context context,List<Map<String,Object>>list)
	{
		this.context=context;
		this.list=list;
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

        ViewHolder mViewHolder = null;
        if (convertView == null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.menu_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.menu_Item_imageView = (ImageView) convertView.findViewById(R.id.menu_Item_imageView);
            mViewHolder.menu_Item_Text=(TextView) convertView.findViewById(R.id.menu_Item_Text);
            convertView.setTag(mViewHolder);
        }

        mViewHolder = (ViewHolder) convertView.getTag();
        mViewHolder.menu_Item_Text.setText(list.get(position).get("text")+""); 
        mViewHolder.menu_Item_imageView.setImageBitmap(BitmapUtils.getBitmapbyConfig(context, 45, 45, (Integer)list.get(position).get("image")));

        return convertView;
    }
	
	static class ViewHolder {
		ImageView menu_Item_imageView;
		TextView menu_Item_Text;
	}


}

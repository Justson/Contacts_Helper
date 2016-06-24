package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;
import java.util.Map;

import com.qypt.just_syn_asis_version1_0.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator justson
 *
 */
public class ImportBaseAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public ImportBaseAdapter(List<Map<String, Object>> list, Context context) {
		this.list = list;
		this.context = context;
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater mLayoutInflater = LayoutInflater.from(context);
			convertView = mLayoutInflater.inflate(
					R.layout.listview_in_from_sd, null);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.image_in_imageview);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.time_in_textview);
			viewHolder.number = (TextView) convertView
					.findViewById(R.id.number_in_textview);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.number.setText(list.get(position).get("number") + "");
		viewHolder.time.setText(list.get(position).get("time") + "");

		return convertView;
	}

	public static class ViewHolder {
		ImageView image;
		TextView time, number;
	}
}

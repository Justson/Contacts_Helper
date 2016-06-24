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
import com.qypt.just_syn_asis_version1_0.model.ChattingMessageBean;
import com.qypt.just_syn_asis_version1_0.model.ChattingType;
/**
 * 
 * 实现 多种布局的adapter
 * @author Administrator justson
 *
 * @param <T>
 */
public class ChattingAdapter<T extends ChattingMessageBean> extends BaseAdapter{

	private Context context;
	private List<T>list;
	private LayoutInflater mLayoutInflater;
	public ChattingAdapter(Context context,List<T>list)
	{
		this.context=context;
		this.list=list;
		mLayoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
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
		 ChattingMessageBean mChattingMessageBean=list.get(position);
		 ViewHolder mViewHolder=null;
		 if(convertView==null)
		 {
			 mViewHolder=new ViewHolder();
			 if(getItemViewType(position)==0)//如果为零的话， 聊天为左边布局
			 {
				convertView=mLayoutInflater.inflate(R.layout.listview_item_chat_service, null); 
				mViewHolder.time=(TextView) convertView.findViewById(R.id.chat_item_time_service);
				mViewHolder.message=(TextView) convertView.findViewById(R.id.chat_item_content_service);
				ImageView image=(ImageView) convertView.findViewById(R.id.listview_item_image_service);
				image.setImageBitmap(BitmapUtils.getRoundBitmap(BitmapUtils.getBitmapbyConfig(context, 50, 50, R.drawable.icon_c)));
			 }else//显示右边的布局
			 {
				 convertView=mLayoutInflater.inflate(R.layout.listview_item_chat_client, null);
				 convertView.setEnabled(false);
				 mViewHolder.message=(TextView) convertView.findViewById(R.id.listview_item_content_client_);
				 mViewHolder.time=(TextView) convertView.findViewById(R.id.chat_item_time_client);
				 ImageView image=(ImageView) convertView.findViewById(R.id.listview_item_image_client);
				 image.setImageBitmap(BitmapUtils.getRoundBitmap(BitmapUtils.getBitmapbyConfig(context, 50, 50, R.drawable.meinv26)));
			 }
			 convertView.setTag(mViewHolder);
		 }
		 mViewHolder=(ViewHolder) convertView.getTag();
		 mViewHolder.time.setText(list.get(position).getTime());
		 mViewHolder.message.setText(list.get(position).getMessage());
		 
		 
		return convertView;
	}
	
	/**
	 * 获取view的返回类型， 如果service类型就返回零
	 * 否则返回1
	 */
	@Override
	public int getItemViewType(int position) {
		ChattingMessageBean mChattingMessageBean=list.get(position);
		if(mChattingMessageBean.getType()==ChattingType.SERVICE)
			return 0;
		return 1;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private static final class ViewHolder{
		public TextView time;
		public	TextView  message;
	}

}

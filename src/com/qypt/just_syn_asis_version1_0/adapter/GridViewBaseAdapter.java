package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.qypt.just_syn_asis_version1_0.activity.R;
/**
 * 
 * @author Administrator juston 
 *
 * 进行封装   
 */
public abstract class GridViewBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T>list;
	protected Animation view_in_animation;
	protected Animation view_out_animation;
	protected LayoutInflater mLayoutInflater;
	protected Intent mIntent;
	//c
	public GridViewBaseAdapter(Context context,List<T>list)
	{
		this.context=context;
		this.list=list;
		init();
	}
	/**
	 *  初始化一些动画
	 */
	private void init() {
		//	
		view_in_animation = AnimationUtils.loadAnimation(context, R.anim.view_fade_in_scale_big);
		view_out_animation = AnimationUtils.loadAnimation(context, R.anim.view_fade_out_scale_small);
		
		mLayoutInflater = LayoutInflater.from(context);
		mIntent =new Intent();
	}
	
	/**
	 * 执行View消失的动画
	 * @param v
	 */
	protected void startViewDismissAnimation(View v)
	{
		v.startAnimation(view_out_animation);
	}
	//view进入的动画
	protected void startViewInAnimation(View v)
	{
		v.startAnimation(view_in_animation);
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
	public abstract View getView(int position, View convertView, ViewGroup parent) ;

}

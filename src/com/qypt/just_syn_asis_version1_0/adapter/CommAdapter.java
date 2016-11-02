package com.qypt.just_syn_asis_version1_0.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import java.util.List;

import com.qypt.just_syn_asis_version1_0.activity.R;

/**
 * Created by Administrator  justson on 2016/4/14.
 *  //time 
 * 万能适配器
 */
public abstract class CommAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T>list;
    protected CommAdapter(Context context, List<T>list)
    {
        this.context=context;
        this.list=list;

    }

    @Override
    public int getCount() {
        return list.size();
    }
    // 
    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent)
    {
    	 
        ViewHolder mViewHolder=ViewHolder.getInstance(convertView, getLayoutResId(),context,position);
        changedDataViewHolder(mViewHolder,  getItem(position), position);
        return mViewHolder.getConvertView();
    }

    protected abstract void changedDataViewHolder(ViewHolder mViewHolder, T t,int position);
    public abstract int getLayoutResId();
}

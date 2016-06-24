package com.qypt.just_syn_asis_version1_0.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/14.
 *
 * @author Administrator  justson
 */
public class ViewHolder {

    private View mConvertView;
    private final SparseArray<View> mSparseArray;
    private int position;

    private ViewHolder(Context context, int resId, View convertView,int position) {
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mLayoutInflater.inflate(resId, null);
        mConvertView = convertView;
        mConvertView.setTag(this);

        mSparseArray = new SparseArray<View>();
    }

    /**
     * 获取ViewHolder实例
     *
     * @param convertView
     * @param resId
     * @return
     */
    public static ViewHolder getInstance(View convertView, int resId, Context context,int position) {

        if (convertView == null) {
            Log.i("Info", "convertView:" + convertView);
            ViewHolder mViewHolder=new ViewHolder(context,resId,convertView,position);
            mViewHolder.setPosition(position);
           return  mViewHolder;
        }
        
        ViewHolder mViewHolder=(ViewHolder) convertView.getTag();
        mViewHolder.setPosition(position);
        return mViewHolder;
    }

    public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
     * 用泛型 限定了，返回去接收的只能是View子类， 如果不是则报错
     *
     * @param resId_View
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int resId_View) {
        View view = mSparseArray.get(resId_View);
        if (view == null) {
            view = (View) mConvertView.findViewById(resId_View);
        }

        return (T) view;
    }


    /**
     * 为View设置文本，然后返回ViewHolder
     *
     * @param resID
     * @param text
     * @return
     */
    public ViewHolder setData(int resID, String text) {
        TextView textView = getView(resID);
        textView.setText(text);
        return this;
    }


    public View getConvertView() {
        return this.mConvertView;
    }

}

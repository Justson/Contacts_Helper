package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.activity.DialogActivity;
import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.model.WechatBean;
import com.qypt.just_syn_asis_version1_0.sqlite.WechatCardSQLiteOpenHelper;
/**
 * 
 * @author Administrator justson
 *
 */
public class SubGridViewAdapter  extends GridViewBaseAdapter<WechatBean> implements OnLongClickListener, OnClickListener {

	public static final int SUBGRIDVIEW=1;
	public SubGridViewAdapter(Context context, List<WechatBean> list) {
		super(context, list);
	}
	//getView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		WechatBean mWechatBean=list.get(position);
		boolean tag=mWechatBean.isNewAccount();
		
		ViewHolder mViewHolder=null;
		if(convertView==null)
		{
			convertView=mLayoutInflater.inflate(R.layout.wechat_card, null);
			mViewHolder=new ViewHolder();
			mViewHolder.name=(TextView) convertView.findViewById(R.id.name_wechat_card);
			mViewHolder.account=(TextView) convertView.findViewById(R.id.account_wechat_card);
			convertView.setTag(mViewHolder);
		}
		
		
		mViewHolder=(ViewHolder) convertView.getTag();
		mViewHolder.position=position;
		mViewHolder.name.setText(list.get(position).getName());
		mViewHolder.account.setText(list.get(position).getAccount());
		convertView.setOnLongClickListener(this);
		convertView.setOnClickListener(this);
		
		if(tag)//如果是新创建的账户  开启动画
		{
			startViewInAnimation(convertView);
			mWechatBean.setNewAccount(false);
		}
		return convertView;
	}

	private static class ViewHolder{
		TextView name,account;
		int position;
	}

	/**
	 * 长按view的时候 view消失
	 */
	@Override
	public boolean onLongClick(final View v) {
		
		startViewDismissAnimation(v);
		ViewHolder mViewHolder=(ViewHolder)v.getTag();
		final int position=mViewHolder.position;
		view_out_animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				WechatBean mWechatBean=list.get(position);
				String name=mWechatBean.getName();
				if(!name.contains("*"))
				{
					/**
					 * 获取数据库，把数据库相应的代码删除掉
					 */
					WechatCardSQLiteOpenHelper mWechatCardSQLiteOpenHelper=WechatCardSQLiteOpenHelper.getInstance(App.getAppContext());
					SQLiteDatabase db=mWechatCardSQLiteOpenHelper.getReadableDatabase();
					db.execSQL("delete from wechat_card_backup where name=? and wechat_account=?", new String[]{name,mWechatBean.getAccount()});
					Toast.makeText(App.getAppContext(), "删除微信名片成功!", Toast.LENGTH_SHORT).show();
					db.close();
				}
				
				list.remove(position);
				SubGridViewAdapter.this.notifyDataSetChanged();
				
			}
		});
		
		return true;
	}

	/**
	 * 判断该卡片是否已经录入资料， 没有录入跳转界面录入资料
	 */
	@Override
	public void onClick(View v) {
		
		if(v==null)
			return;
		TextView name=(TextView) v.findViewById(R.id.name_wechat_card);
		String strName=name.getText().toString();
		if(strName.contains("*"))
		{
			ViewHolder mViewHodler=(ViewHolder) v.getTag();
			int position=mViewHodler.position;
			mIntent.putExtra("position", position+"");
			mIntent.setClass(context, DialogActivity.class);
			Activity mAcitivty=(Activity) context;
			mAcitivty.startActivityForResult(mIntent,SUBGRIDVIEW);
		}
		
	}
	
	
}

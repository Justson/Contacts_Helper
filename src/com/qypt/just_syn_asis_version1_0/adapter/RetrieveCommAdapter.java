package com.qypt.just_syn_asis_version1_0.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.security.spec.MGF1ParameterSpec;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.http.HttpManager;
import com.qypt.just_syn_asis_version1_0.http.HttpManager.OkHttpCallBack;
import com.qypt.just_syn_asis_version1_0.model.DataBaseBean;
import com.qypt.just_syn_asis_version1_0.presenter.RetrieveDownPersenter;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
import com.qypt.just_syn_asis_version1_0.utils.UrlUtils;
/**
 * 
 * @author Administrator justson
 *
 */
public class RetrieveCommAdapter extends CommAdapter<DataBaseBean> implements OnClickListener, OkHttpCallBack<String> {

	private Context context;
	private ViewHolder mViewHolder;
	private List<DataBaseBean>list;
	public static final int TRETRIEVECOMMADAPTER=22;
	private MyHandler myHandler;
	public RetrieveCommAdapter(Context context, List<DataBaseBean> list) {
		super(context, list);
		this.context=context;
		this.list=list;
		RetrieveDownPersenter mRetrieveDownPersenter=new RetrieveDownPersenter();
		myHandler=new MyHandler(Looper.getMainLooper());
	}

	@Override
	protected void changedDataViewHolder(ViewHolder mViewHolder,
			DataBaseBean t, int position) {
		TextView textView=mViewHolder.getView(R.id.time_in_textview);
		textView.setText(t.getTime());
		TextView numberView=mViewHolder.getView(R.id.number_in_textview);
		numberView.setText(t.getNumber());
		mViewHolder.getConvertView().setOnClickListener(this);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.listview_in_from_sd;
		
	}

	@Override
	public void onClick(View v) {
		
		
		DialogUtils.moreColorProgress(context);
		ViewHolder mViewHolder=(ViewHolder) v.getTag();
		int position=mViewHolder.getPosition();
		DataBaseBean mDataBaseBean=list.get(position);
		if(mDataBaseBean==null)
			return;
		
		HttpManager mOkHttpManager=HttpManager.getInstance();
		mOkHttpManager.asynPointTargetDownx(UrlUtils.POINTFILEDOWN, mDataBaseBean, "mDataBaseBean", this);
		
	}

	/**
	 * 下载完成后的回调 ， 吧相应的联系插入
	 * @param t
	 */
	@Override
	public void handleSucess(final String t) {
		
		if(t==null||t.equals(""))
			return;
		Log.i("Info", "path:"+t);
		new Thread(){
			@Override
			public void run() {
				 
				List<Map<String,Object>>list=null;
				ObjectInputStream ois=null;
				try {
					ois=new ObjectInputStream(new FileInputStream(new File(t)));
					if(ois==null)
						return;
					list=(List<Map<String, Object>>) ois.readObject();
					ContactUtils.deleteAllContact(context);
					Log.i("Info", "list:"+list);
					if(list!=null)
					ContactUtils.handleContact(context, list, RetrieveCommAdapter.TRETRIEVECOMMADAPTER);
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						if(ois!=null)
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myHandler.sendEmptyMessage(0x33);
				}
				
				
			}
		}.start();
	}
	
	static	class  MyHandler extends Handler{
		public MyHandler(Looper mLooper)
		{
			super(mLooper);
		}
		/**
		 * 接受到消息说明联系人插入完成
		 */
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0x33){
				
				DialogUtils.moreColorDismiss();
				Toast.makeText(App.getAppContext(), "联系人已经更新!", Toast.LENGTH_SHORT).show();
				
			}
		}
	}

	@Override
	public void handleFail(String t) {
		
	}

}

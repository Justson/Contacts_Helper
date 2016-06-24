package com.qypt.just_syn_asis_version1_0.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;

/**
 * 
 * @author Administrator justson
 * 
 */
public class ShareActivity extends SynActivity implements OnClickListener, OnTouchListener {

	
	private  Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what==0x88){
				
				Toast.makeText(App.getAppContext(), "打包完毕...", Toast.LENGTH_SHORT)
				.show();
				DialogUtils.moreColorDismiss();
				String path=(String) msg.obj;
				startSystemEmailToshare(path);
			}
			
		};
	};
	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		this.setContentView(R.layout.activity_share);
		TextView text = (TextView) findViewById(R.id.text);
		text.setText("分享");
		ImageView image = (ImageView) this.findViewById(R.id.left);
		image.setOnClickListener(this);
		Button share_share_button = (Button) findViewById(R.id.share_share_button);
		share_share_button.setOnClickListener(this);
		share_share_button.setOnTouchListener(this);

	}

	@Override
	protected void initViewData() {

	}

	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.left:
			this.finish();
			break;
		case R.id.share_share_button:

			 
					
			upPackShare();
			

			break;

		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	private void upPackShare() {

		
		Log.i("Info", "显示dialog");
//		try {
//			  MyAsyncTask mAsyncTask= new MyAsyncTask();
//			  String path=mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
//			if (path != null) {
//				startSystemEmailToshare(path);
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		DialogUtils.moreColorProgress(ShareActivity.this);
		Toast.makeText(App.getAppContext(), "正在打包，稍等...",Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				List<Map<String, Object>> list = ContactUtils.getAllContactList(App
						.getAppContext());
				
				
				if (list != null && !list.isEmpty()) {
					String path = initPath();
					int number = list.size();
					Long time = System.currentTimeMillis();
					ObjectOutputStream oos = null;
					try {
						oos = new ObjectOutputStream(new FileOutputStream(new File(
								path, number + "_" + time + ".txt")));
						oos.writeObject(list);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (oos != null)
							try {
								oos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}
					Message mMessage=mHandler.obtainMessage(0x88,path);
					mHandler.sendMessageAtFrontOfQueue(mMessage);
					 
				}
				
			}
		}).start();

	}

	/**
	 * 启动系统邮箱去分享联系人
	 * 
	 * @param path
	 */
	private void startSystemEmailToshare(String path) {

		Intent mIntent = new Intent();
		mIntent.setAction(Intent.ACTION_SEND);
		mIntent.setType("application/octet-stream");
		String  str="这是我的联系人，分享给你啦， 请用通讯帮手打开";
		mIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "通讯帮手分享联系人");
		mIntent.putExtra(android.content.Intent.EXTRA_TEXT, str);
		mIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
		startActivity(Intent.createChooser(mIntent, "请选择软件发送邮件"));
		
//		Intent intent = new Intent(Intent.ACTION_SEND); 
//		String[] tos = { "way.ping.li@gmail.com" }; 
//		String[] ccs = { "way.ping.li@gmail.com" }; 
//		String[] bccs = {"way.ping.li@gmail.com"}; 
//		intent.putExtra(Intent.EXTRA_EMAIL, tos); 
//		intent.putExtra(Intent.EXTRA_CC, ccs); 
//		intent.putExtra(Intent.EXTRA_BCC, bccs); 
//		intent.putExtra(Intent.EXTRA_TEXT, "body"); 
//		intent.putExtra(Intent.EXTRA_SUBJECT, "subject"); 
//
//		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path)); 
//		intent.setType("image/*"); 
//		intent.setType("message/rfc882"); 
//		Intent.createChooser(intent, "Choose Email Client"); 
//		startActivity(intent); 

	}

//	class MyAsyncTask extends AsyncTask<Void, Void, String> {
//
//		@Override
//		protected void onPreExecute() {
// 			DialogUtils.moreColorProgress(ShareActivity.this);
//			Toast.makeText(App.getAppContext(), "正在打包，稍等...",
//					Toast.LENGTH_SHORT).show();
//			Log.i("Info", "开始打包");
//			 
//			
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//
//		
//			List<Map<String, Object>> list = ContactUtils.getAllContactList(App
//					.getAppContext());
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (list != null && !list.isEmpty()) {
//				String path = initPath();
//				int number = list.size();
//				Long time = System.currentTimeMillis();
//				ObjectOutputStream oos = null;
//				try {
//					oos = new ObjectOutputStream(new FileOutputStream(new File(
//							path, number + "_" + time + ".txt")));
//					oos.writeObject(list);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					if (oos != null)
//						try {
//							oos.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//				}
//				return path + File.separator + number + "_" + time + ".txt";
//
//			}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			DialogUtils.moreColorDismiss();
//			Toast.makeText(App.getAppContext(), "打包完毕...", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}

	// 获取路径
	private String initPath() {

		String path = null;
		/**
		 * 判断外部储蓄卡是否存在
		 */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String strPath = Environment.getExternalStorageDirectory()
					+ File.separator + "Syn_sis_";
			File file = new File(strPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = file.getAbsolutePath();// 获取路径
			file = null;
		} else {
			path = Environment.getDataDirectory().getAbsolutePath()
					+ File.separator + "Syn_sis_";
		}
		return path;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction()==MotionEvent.ACTION_UP){
			Log.i("Info", "触发 up 事件");
			upPackShare();
			return true;
		}
		return true;
	}

}

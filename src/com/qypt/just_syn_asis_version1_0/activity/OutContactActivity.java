package com.qypt.just_syn_asis_version1_0.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.test.ActivityTestCase;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
import android.app.ActivityManager;
/**
 * 
 * @author Administrator justson
 *
 */
public class OutContactActivity extends Activity implements OnClickListener {
	long allSize=0;
	private TextView overplus_out_textview;
	private TextView number_out_textview;
	private DialogUtils mDialogUtils;
	private String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_out_contact);
		init();
		initView();
		
	}
	
	private void initView() {
		overplus_out_textview = (TextView) findViewById(R.id.overplus_out_textview);
		number_out_textview = (TextView) findViewById(R.id.number_out_textview);
		TextView text=(TextView) findViewById(R.id.text);
		text.setText("导出SD卡");
		Button out_out_button=(Button) findViewById(R.id.out_out_button);
		out_out_button.setOnClickListener(this);
		ImageView left=(ImageView) findViewById(R.id.left);
		left.setOnClickListener(this);
		String size=getFormatNumber((((float)allSize/(float)1024/1024)));
		overplus_out_textview.setText(size+"GB");
		number_out_textview.setText(ContactUtils.getContactNumber(this)+"");
		mDialogUtils = new DialogUtils(this);
	}
	/**
	 * 精确到小数点后两位
	 * @param p
	 * @return
	 */
	public String getFormatNumber(float p)
	{
//		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		BigDecimal bd=new BigDecimal(p);
		return  bd.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue()+"";//format 返回的是字符串
	}

	/**
	 * 初始化，并且获取sdcard 剩余的空间大小
	 */
	private void init() {
		
		
		File path =Environment.getExternalStorageDirectory();
		if(path==null)
			return;
		
		StatFs mStatFs=new StatFs(path.getAbsolutePath());
//		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.BASE)
		allSize=mStatFs.getAvailableBlocks();// 获取sd空间
		
		Log.i("Info", "获取剩余的容量:"+allSize);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.left)
		{
			this.finish();
		}
		if(v.getId()==R.id.out_out_button)
		{
			tips_user();
		}
	}

	/**
	 * 提醒用户
	 */
	private void tips_user() {
		final Dialog mDialog=new Dialog(this,R.style.loading);
		mDialog.setContentView(R.layout.dialog_tips_);
		TextView sure_dialog_textview=(TextView) mDialog.findViewById(R.id.sure_dialog_textview);
		mDialog.show();
		sure_dialog_textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				
				try {
					writeToFile();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		
		});
	}
	
	private void writeToFile() throws FileNotFoundException, IOException {
		
		
		List<Map<String,Object>>list=ContactUtils.getAllContactList(this);
		if(list==null)
			return;
		
		mDialogUtils.showDialogMessage("正在导入...");
		
		
		path=Environment.getExternalStorageDirectory()+File.separator+"Syn_sis_";
		StringBuffer name=new StringBuffer();
		int number=ContactUtils.getContactNumber(this);
		Long time=System.currentTimeMillis();
		name=name.append(number+"_"+time);
		name.append(".txt");
		File file=new File(path, name+"");
	
		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(file));
		os.writeObject(list);
		
		mDialogUtils.dismissDialog_();
		Toast.makeText(getApplicationContext(), "导入成功~", Toast.LENGTH_LONG).show();
		
	}
	
	
}

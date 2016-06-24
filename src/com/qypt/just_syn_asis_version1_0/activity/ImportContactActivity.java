package com.qypt.just_syn_asis_version1_0.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qypt.just_syn_asis_version1_0.adapter.ImportBaseAdapter;
import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils;
import com.qypt.just_syn_asis_version1_0.utils.DialogUtils.OnClickResultCallBack;
import com.qypt.just_syn_asis_version1_0.utils.TimerUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 * @author Administrator  justson
 *
 */
public class ImportContactActivity extends Activity implements OnItemClickListener {

	private String path;
	private List<Map<String, Object>> list;
	private File[] arrFiles;
	private View view_dialog;
	public static final int IMPORTCONTACTACTIVITY=8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_import);
		init();
		initView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}
	private void initView() {
		
		ListView inListView=(ListView) findViewById(R.id.listview_import_listview);
		if(list==null||list.isEmpty())
			return;
		inListView.setAdapter(new ImportBaseAdapter(list, this));
		inListView.setOnItemClickListener(this);
	}

	/**
	 * 获取文件列表
	 */
	private void init() {

		String path_ = initPath();
		File file = new File(path_);
		arrFiles = file.listFiles();

		list = parserFile(arrFiles);
		
		view_dialog = LayoutInflater.from(this).inflate(R.layout.dialog_tips_call, null);

	}

	/**
	 * 解析文件name  因为文件名的命名规则为  number_time解析文件名便可获取到数据
	 * 
	 * @param arrFile
	 * @return
	 */
	private List<Map<String, Object>> parserFile(File[] arrFile) {
		if (arrFile == null)
			return null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (File file : arrFile) {
			if(file.isDirectory())
				continue;
			map = new HashMap<String, Object>();
			String name = file.getName();
			int index = name.indexOf("_");
			String number = name.substring(0, index);
			int index_=name.indexOf(".");
			String time = name.substring(index+1 , index_);
			Log.i("info", "time:"+time);
			time=TimerUtils.getDateToForm(time);
			map.put("number", number);
			map.put("time", time);
			list.add(map);

		}

		Log.i("Info", "list:"+list.toString());
		return list;
	}

	private String initPath() {

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
		}
		return path;
	}

	/**
	 * listview的点击事件，  处理需要导入的联系人
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		
		
		if(arrFiles==null)
			return; 
			
		DialogUtils.CanCallBackDialog(view_dialog,"确定导入手机吗？", this,new OnClickResultCallBack() {
			
			@Override
			public void onResultCallBack() {
				
				readObject(position);
			}
		} );
			
			
		
		
	}

	/**
	 * 读取对象
	 * @param position
	 */
	private void readObject(int position) {
		File file=arrFiles[position];
		ObjectInputStream ois=null;
		List<Map<String,Object>>list=null;
		try {
			ois=new ObjectInputStream(new FileInputStream(file));
			list=(List<Map<String, Object>>) ois.readObject();
			
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
			
			if(ois!=null)
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
			insertContact(list);
			
		}
	}
	
/**
 * 插入联系人
 * @param list2
 */
	private void insertContact(final List<Map<String, Object>> list_) {
		
		if(list_==null)
			return;
		
		new Thread()
		{
			public void run() {
				
				ContactUtils.handleContact(ImportContactActivity.this, list_,IMPORTCONTACTACTIVITY);
			};
		}.start();
		
	}
}

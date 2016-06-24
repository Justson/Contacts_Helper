package com.qypt.just_syn_asis_version1_0.activity;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
public class ImportSimCardActivity extends Activity implements OnClickListener {

	private List<Map<String, Object>> list;
	private ImageView simibt1;
	private TextView import_sim_textview;
	public static final int INPORTSIMCARDACTIVITY = 1088;
	private RecylerViewAdapter mRecylerViewAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.acitvity_sim_card_import);
		initSimCardContact();
		initView();

	}

	/**
	 * 获取联系人
	 */
	private void initSimCardContact() {

		list = ContactUtils.getSimCardContact(this);
		simibt1 = (ImageView) findViewById(R.id.Simibt1);
		simibt1.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

	private void initView() {

		import_sim_textview = (TextView) findViewById(R.id.import_sim_textview);
		import_sim_textview.setOnClickListener(this);

		android.support.v7.widget.RecyclerView mRecyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerview);// 初始化RecyclerView//
																																		// 新特性
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		if (list == null || list.isEmpty()) {
			TextView text_null = (TextView) findViewById(R.id.sim_null_text);
			text_null.setVisibility(View.VISIBLE);
			return;
		}

		mRecylerViewAdapter = new RecylerViewAdapter();
		mRecyclerView.setAdapter(mRecylerViewAdapter);

	}

	/**
	 * 创建RecyclerView的adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class RecylerViewAdapter extends
			RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {

		@Override
		public int getItemCount() {
			return list.size();
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, int position) {

			holder.name.setText(list.get(position).get("name").toString());
			holder.number.setText(list.get(position).get("number").toString());

		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

			MyViewHolder mViewHolder = new MyViewHolder(LayoutInflater.from(
					ImportSimCardActivity.this).inflate(
					R.layout.recyclerview_item, null));
			return mViewHolder;
		}

		class MyViewHolder extends ViewHolder {

			TextView number, name;

			public MyViewHolder(View view) {
				super(view);
				number = (TextView) view
						.findViewById(R.id.recyclerview_item_numbers);
				name = (TextView) view
						.findViewById(R.id.recyclerview_item_name);

			}

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.Simibt1:
			this.finish();

			break;

		case R.id.import_sim_textview:
			importContactBySimCard();
			break;

		}
	}

	private void importContactBySimCard() {

		if (list == null || list.isEmpty()) {
			Toast.makeText(App.getAppContext(), "sim卡没有联系人哦",
					Toast.LENGTH_SHORT).show();
			return;
		}

		new ContactAsyncTask().execute(list);

	}

	/**
	 * 启动异步任务插入联系人
	 */

	public class ContactAsyncTask extends
			AsyncTask<List<Map<String, Object>>, Void, Void> {

		@Override
		protected void onPreExecute() {
			DialogUtils.moreColorProgress(ImportSimCardActivity.this);
			Toast.makeText(App.getAppContext(), "正在导入联系人，请稍等...",
					Toast.LENGTH_LONG).show();
		}

		@Override
		protected Void doInBackground(List<Map<String, Object>>... params) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ContactUtils.handleContact(ImportSimCardActivity.this, list,
					INPORTSIMCARDACTIVITY);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			DialogUtils.moreColorDismiss();
			list.clear();
			mRecylerViewAdapter.notifyDataSetChanged();
			Toast.makeText(App.getAppContext(), "Sim卡联系人导入成功~",
					Toast.LENGTH_SHORT).show();
		}

	}

}

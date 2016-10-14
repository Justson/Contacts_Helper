package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mob.tools.network.NetworkHelper;
import com.qypt.just_syn_asis_version1_0.adapter.ChattingAdapter;
import com.qypt.just_syn_asis_version1_0.model.ChattingMessageBean;
import com.qypt.just_syn_asis_version1_0.model.ChattingType;
import com.qypt.just_syn_asis_version1_0.model.RobotModel;
import com.qypt.just_syn_asis_version1_0.model.RobotResultBean;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
import com.qypt.just_syn_asis_version1_0.utils.TimerUtils;

/**
 * 
 * @author Administrator justson
 * //机器人聊天 用了图灵机器人
 */
public class RobotActivity extends SynActivity implements OnClickListener {

	private Button sendButton;
	private ListView myListView;
	private EditText contentEditText;
	private static final String APIKEY = "b1713efc987945038cbe54c10f87c55a";
	private static final String SCRET = "113c84bde62af337";
	private RobotModel mRobotModel;
	private List<ChattingMessageBean> list = new ArrayList<ChattingMessageBean>();
	private ChattingAdapter<ChattingMessageBean> mChattingAdapter;
	private boolean isUpdata=false;

	@Override
	protected void initView() {
		Log.i("Info", "Thread:"+Thread.currentThread());
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_robot);
		myListView = (ListView) findViewById(R.id.chat_robot);
		contentEditText = (EditText) this.findViewById(R.id.content_robot);
		sendButton = (Button) findViewById(R.id.send_robot);
		sendButton.setOnClickListener(this);
		mRobotModel = new RobotModel();
		ImageView image=(ImageView) findViewById(R.id.left_robot);
		image.setOnClickListener(this);
	}

	@Override
	protected void initViewData() {

		
		list.add(new ChattingMessageBean(TimerUtils.getDate("yyyy-MM-dd hh:mm:ss"), ChattingType.SERVICE, "通信帮手很高兴为您服务"));
		list.add(new ChattingMessageBean(TimerUtils.getDate("yyyy-MM-dd hh:mm:ss"), ChattingType.CLIENT, "您好"));
		mChattingAdapter = new ChattingAdapter<ChattingMessageBean>(this, list);
		myListView.setAdapter(mChattingAdapter);
	}

	// 点击事件的处理
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.send_robot:
			sendMessage();
			break;

		case R.id.left_robot:
			this.finish();

			break;
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}

	/**
	 * 发送消息
	 */
	private void sendMessage() {

		if (contentEditText.getText().toString().equals("")||isUpdata)
		{
			Log.i("Info", "isUpdata:"+isUpdata);
			return;
		}
		if(!NetWorkHepler.netWorkIsReady(this))
		{
			Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
			

		isUpdata=true;
		String strContent = contentEditText.getText().toString();
		list.add(new ChattingMessageBean(TimerUtils.getDate("yyyy-MM-dd hh:mm:ss"), ChattingType.CLIENT, strContent));
		mChattingAdapter.notifyDataSetChanged();
		contentEditText.setText("");
		
		RobotResultBean mRobotResultBean = mRobotModel
				.sendMessageToTuling(strContent);
		if (mRobotResultBean == null) {
			mRobotResultBean = new RobotResultBean();
			mRobotResultBean.setText("服务器正忙...");
		}

		ChattingMessageBean mChattingMessageBean = new ChattingMessageBean();
		mChattingMessageBean.setType(ChattingType.SERVICE);
		mChattingMessageBean.setTime(TimerUtils.getDate("yyyy-MM-dd hh:mm:ss"));
		mChattingMessageBean.setMessage(mRobotResultBean.getText());
		list.add(mChattingMessageBean);
		Log.i("Info", "Thread:"+Thread.currentThread());
		myListView.requestLayout();
		myListView.setSelection(list.size()-1); // 自动跳转到最后一条
		isUpdata=false;
	}

}

package com.qypt.just_syn_asis_version1_0.activity;

import java.util.ArrayList;
import java.util.List;

import com.qypt.just_syn_asis_version1_0.adapter.ContactFragmentPagerAdapter;
import com.qypt.just_syn_asis_version1_0.app.App;
import com.qypt.just_syn_asis_version1_0.fragment.MemoryFragment;
import com.qypt.just_syn_asis_version1_0.fragment.RubishFragment;
import com.qypt.just_syn_asis_version1_0.model.DataBaseBean;
import com.qypt.just_syn_asis_version1_0.model.EventBussMessage;
import com.qypt.just_syn_asis_version1_0.model.RetrieveTaskBean;
import com.qypt.just_syn_asis_version1_0.presenter.RetrievePresenter;
import com.qypt.just_syn_asis_version1_0.utils.JsonUtils;
import com.qypt.just_syn_asis_version1_0.utils.NetWorkHepler;
import com.qypt.just_syn_asis_version1_0.view.MainView;

import de.greenrobot.event.EventBus;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author Administrator justson
 *
 */
public class RetrieveActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener ,MainView{

	private ViewPager mViewPager;
	private int hieght;
	private int width;
	private int imageWidth;
	private ImageView mImageView;
	private TextView find_retrieve_textview;
	private TextView rabish_retreve_textview;
	private RetrievePresenter mRetrievePresenter;
	public static final int RETRIEVEACTIVITY=88;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_retrieve);
		EventBus.getDefault().register(this);
		getDataFromService();
		initView();
		initonSreenWidthWithHeight();
	}

	// 初始化屏幕宽度和高度
	private void initonSreenWidthWithHeight() {

		DisplayMetrics mDisplayMetrics = this.getResources()
				.getDisplayMetrics();
		hieght = mDisplayMetrics.heightPixels;
		width = mDisplayMetrics.widthPixels;
		imageWidth = width / 2;
	}

	/**
	 * 获取数据从服务器
	 */
	private void getDataFromService() {

		if(!NetWorkHepler.netWorkIsReady(this))
			return;
		
		mRetrievePresenter = new RetrievePresenter();
		mRetrievePresenter.attachView(this);

		Log.i("Info", "开始获取数据");
		RetrieveTaskBean mRetrieveTaskBean=new RetrieveTaskBean();
		mRetrieveTaskBean.setName(App.NAME);
		mRetrieveTaskBean.setTask("获取联系人时光机");
		mRetrievePresenter.dispatchTask(mRetrieveTaskBean);
		
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		ImageView image=(ImageView) findViewById(R.id.retrieve_left);
		image.setOnClickListener(this);
		mViewPager = (ViewPager) findViewById(R.id.myViewPager);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		find_retrieve_textview = (TextView) findViewById(R.id.find_retrieve_textview);
		rabish_retreve_textview = (TextView) findViewById(R.id.rabish_retrieve_textview);
		find_retrieve_textview.setOnClickListener(this);
		rabish_retreve_textview.setOnClickListener(this);

		mViewPager.setOnPageChangeListener(this);
		initViewPager();

	}

	private void initViewPager() {

		List<Fragment> list = new ArrayList<Fragment>();
		MemoryFragment mMemoryFragment = new MemoryFragment();
		RubishFragment mRubishFragment = new RubishFragment();
		list.add(mMemoryFragment);
		list.add(mRubishFragment);

		ContactFragmentPagerAdapter mContactFragmentPagerAdapter = new ContactFragmentPagerAdapter(
				this.getSupportFragmentManager(), list);

		mViewPager.setAdapter(mContactFragmentPagerAdapter);

	}

	/**
	 * event 发送过来的消息
	 * @param mEventBussMessage
	 */
	public void onEventMainThread(EventBussMessage mEventBussMessage){
		
	}
	@Override
	protected void onPause() {
		Log.i("Info", "justsontestpause:"+mRetrievePresenter);
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.find_retrieve_textview) {
			mViewPager.setCurrentItem(0);
		} else if (v.getId() == R.id.rabish_retrieve_textview) {
			mViewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.retrieve_left){
			this.finish();
			
		}
	}

	/**
	 * viewpager的滑动事件
	 * 
	 * @param arg0
	 */
	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int px) {
//
//		Log.i("Info", "position:" + position);
//		Log.i("Info", "px:" + px);
//		Log.i("Info", "positionOffset:" + positionOffset);

		if (positionOffset == 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mImageView
				.getLayoutParams();
		lp.leftMargin = (int) (imageWidth * positionOffset);

		mImageView.setLayoutParams(lp);

	}

	@Override
	public void onPageSelected(int position) {

		if (position == 0) {
			rabish_retreve_textview.setTextColor(Color.parseColor("#999999"));
			find_retrieve_textview.setTextColor(Color.parseColor("#3d77c0"));
		} else {
			rabish_retreve_textview.setTextColor(Color.parseColor("#3d77c0"));
			find_retrieve_textview.setTextColor(Color.parseColor("#999999"));
		}

	}

	@Override
	public void showProgressBar() {
		
	}

	@Override
	public void hintProGrossBar() {
		
	}

	@Override
	public <T> void setData(T t) {
		
		 if(t==null)
		 return;
		List<DataBaseBean>list=JsonUtils.getListEntityDataFromJson((String)t, DataBaseBean.class);
		Log.i("Info", "list:"+list);
		if(list!=null&&!list.isEmpty())
		{
			//如果解析出来的json数据不为空的话， EvenBUS发送个消息给fragment们
			EventBussMessage.Builder builder=new EventBussMessage.Builder();
			EventBussMessage mEventBussMessage=builder.setData(list).setSubscriber(RETRIEVEACTIVITY).build();
			EventBus.getDefault().post(mEventBussMessage);
			
		}
	}

	@Override
	public void downLoad(String result) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onStop() {
		Log.i("Info", "justsonteststop:"+mRetrievePresenter);
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		Log.i("Info", "justsontest:"+mRetrievePresenter);
		if(mRetrievePresenter!=null)
		mRetrievePresenter.dispatchView(this);
		super.onDestroy();
		
		EventBus.getDefault().unregister(this);
	}
}

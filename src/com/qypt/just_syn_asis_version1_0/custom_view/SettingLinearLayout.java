package com.qypt.just_syn_asis_version1_0.custom_view;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * @author Administrator justson
 * 
 */
public class SettingLinearLayout extends LinearLayout {

	private Paint mPaint;
	private int radius;
	private View targetView;
	private boolean isPress = false;
	private int downX;
	private int downY;
	private boolean isFirst=true;
	private int addSpeed=5;

	public SettingLinearLayout(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public SettingLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public SettingLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	// 初始化一些画笔，半径一些东西
	private void init(Context context) {

		mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#33333333"));
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		// 半径
		radius = 0;

	}

	/**
	 * 绘画圆
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
			super.dispatchDraw(canvas);
	 
		 if(targetView==null||isPress==false)
			 return;
		
		if(radius>targetView.getWidth()/8)
		{
			radius=radius+(++addSpeed);
		}else
		{
			radius = radius + 4;
		}
		int[]mLocationInScreen=new int[2];
		this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + targetView.getMeasuredWidth();
        int bottom = top + targetView.getMeasuredHeight();
//		int viewleft = targetView.getLeft();
//		int viewRight = targetView.getRight();
//		int viewTop = targetView.getTop();
//		int viewBottom = targetView.getBottom();
//        
//        Log.i("Info", "left:"+left+" top:"+top+" right:"+right+" bottom:"+bottom+"");
//        Log.i("Info", "viewleft:"+viewleft+" viewTop:"+viewTop+" viewRight:"+viewRight+" viewBottom:"+viewBottom+"");
		canvas.clipRect(left, top, right, bottom);
		canvas.drawCircle(downX, downY, radius, mPaint);
		canvas.restore();
		if (isPress) {
			postInvalidateDelayed(0);
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			Log.i("Info", "down");
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			View view = getChildView(downX, downY);
			if (view != null) {

				targetView = view;
				isPress = true;
				postInvalidateDelayed(5);
			}

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			Log.i("Info", "chufa");
			isPress = false;
			radius=0;
			postInvalidate();
			addSpeed=5;

			break;

		}

		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 通过点下得坐标获取子view
	 * 
	 * @param downX
	 * @param downY
	 * @return
	 */
	private View getChildView(int downX, int downY) {

		Log.i("Info", "find child");
		ArrayList<View> list = new ArrayList<View>();// 获取所有ziview
		int count=this.getChildCount();
		for(int i=0;i<count;i++)
		{
			list.add(this.getChildAt(i));
		}
		Log.i("Info", "list:" + list);
		Log.i("Info", "listsize:" + list.size());
		if (list != null && !list.isEmpty()) {
			for (View view : list) {
				int viewLeft = view.getLeft();
				int viewTop = view.getTop();
				int viewBottom = view.getBottom();
				int viewRight = view.getRight();
//				Log.i("Info", "downX:" + downX + "  downY:" + downY
//						+ " viewLeft:" + viewLeft + " viewTop:" + viewTop
//						+ " viewBottom:" + viewBottom + " viewRight:"
//						+ viewRight);
				if (downX > viewLeft && downX < viewRight && downY > viewTop
						&& downY < viewBottom) {
					Log.i("Info", "find sucess");
					return view;
				}
			}
		}

		return null;
	}

}

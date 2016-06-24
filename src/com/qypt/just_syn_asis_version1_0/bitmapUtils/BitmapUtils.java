package com.qypt.just_syn_asis_version1_0.bitmapUtils;

import android.R.color;
import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;
import android.util.TypedValue;

public class BitmapUtils {

	
	
	public static Bitmap getBitmapbyConfig(Context context,int width,int height,int resID)
	{
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		options.inPreferredConfig=Config.ARGB_8888;
		Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), resID, options);
		int realityWidth=options.outWidth;
		int realityHeight=options.outHeight;
		options.inSampleSize=getRate(realityWidth,realityHeight,width,height,context);
		options.inJustDecodeBounds=false;
		
		 
		
		
		return BitmapFactory.decodeResource(context.getResources(), resID, options);
	}

	private static int getRate(int realityWidth, int realityHeight, int width,
			int height,Context context) {
		int widthPx=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
		int heightPx=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
		int rate=1;
		
		if(realityWidth>widthPx||realityHeight>heightPx)
		{
			int maxRate=Math.max((int)realityWidth/widthPx,(int) realityHeight/heightPx);
			rate=maxRate;
		}
		
		return rate;
	}
	
	/**
	 * 获取圆图片
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap getRoundBitmap(Bitmap bitmap)
	{
		
		
		if (bitmap==null)
		{
			return null;
		}
		Log.i("Info", "开始画圆");
		int width=bitmap.getWidth();
		int height=bitmap.getHeight();
		//圆的半径
		int r=0;
		if(width>height)
		{
			//半径取小的值
			r=height;
		}
		else
		{
			r=width;
		}
		
		//初始化画笔
		Paint mPaint=new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		
		Bitmap mBitmap=Bitmap.createBitmap(r, r, Config.ARGB_8888);
		Canvas mCanvas=new Canvas(mBitmap);
		RectF mFRectF=new RectF(0, 0, r, r);
		mCanvas.drawRoundRect(mFRectF, r/2, r/2, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		mCanvas.drawBitmap(bitmap, null, mFRectF, mPaint);
		
		if(bitmap!=null)
		{
			bitmap.recycle();
			bitmap=null;
			System.gc();
		}
		return mBitmap;
	}
	
}

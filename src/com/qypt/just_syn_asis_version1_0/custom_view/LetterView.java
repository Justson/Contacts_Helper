package com.qypt.just_syn_asis_version1_0.custom_view;

import com.qypt.just_syn_asis_version1_0.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on justson 2016/4/13.
 */
public class LetterView extends View {



    private int customWidth = 0;
    private  int customHeight = 0;
    private int mScreenHeight;
    private int mScreenWidth;
    private char[] mChar;
    private Paint mPaint;

    public LetterView(Context context) {
        this(context, null);
        TextView text;
    }

    public LetterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取屏幕狂高
        mScreenHeight=context.getResources().getDisplayMetrics().heightPixels;
        mScreenWidth=context.getResources().getDisplayMetrics().widthPixels;

        /**
         * TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingDrawer, defStyle, 0);
         int contentId = a.getResourceId(R.styleable.SlidingDrawer_content, 0);//比如这里，如果在xml
         中没有定义SlidingDrawer_content这个属性，则获取到的contentId 值是后面设置的0，如果定义
         了SlidingDrawer_content这个属性，则后面设置的默认值0无意义。
         */

        /**
         * 获取自定义属性
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LetterView);
        int count = ta.getIndexCount();


        for (int i = 0; i < count; i++) {
            int atrr = ta.getIndex(i);
            switch (atrr) {
                case R.styleable.LetterView_Width:

                    customWidth = ta.getDimensionPixelSize(atrr, -1);

                    break;
                case R.styleable.LetterView_Height:
                    customHeight = ta.getDimensionPixelSize(atrr, -1);
                    break;

            }
        }
        ta.recycle();

        if (customWidth != -1) {
            customWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, customWidth, context.getResources().getDisplayMetrics());
        }
        if (customHeight != -1) {
            customHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, customHeight, context.getResources().getDisplayMetrics());
        }

        //初始化字母
        initChar();
        char[]mChar=new char[27];

        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#123456"));
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(22);


    }

    private void initChar() {
        mChar = new char[27];
        for(int i = 0; i< mChar.length; i++)
        {
            mChar[i]= (char) ('A'+i);
        }
        mChar[mChar.length-1]='#';
    }


    /**
     * 开始画字母表
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int letterWidth=customWidth;
        int letterHeight=getHeight()/(mChar.length+1);
        float letterX=letterWidth/2-mPaint.measureText(mChar[1]+"")-2;
        for(int i=0;i<mChar.length;i++)
        {
           float letterY=letterHeight+i*letterHeight;
            canvas.drawText(mChar[i]+"",letterX,letterY,mPaint);
//            Log.i("Info","canvas:"+mChar[i]);
        }


    }
}

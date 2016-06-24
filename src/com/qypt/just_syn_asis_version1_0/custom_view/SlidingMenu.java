package com.qypt.just_syn_asis_version1_0.custom_view;


 
import com.nineoldandroids.view.ViewHelper;

import com.qypt.just_syn_asis_version1_0.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2016/3/30. --  justson
 */
public class SlidingMenu  extends HorizontalScrollView{

    private boolean isShow=false;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mRightPadding;
    private int mMenuWidth;
    private View view;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 
     * @param context
     * @param attrs
     * @param defStyleAttr   表示按钮的风格， 一般由系统自动传入v
     * 
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕的宽高
        DisplayMetrics  mDisplayMetrics=context.getResources().getDisplayMetrics();
        mScreenWidth = mDisplayMetrics.widthPixels;
        mScreenHeight = mDisplayMetrics.heightPixels;

        //获取自定义属性
        TypedArray mTypeArray=context.getTheme().obtainStyledAttributes(attrs,R.styleable.SlidingMenu,defStyleAttr,0);
        int count=mTypeArray.getIndexCount();
        for(int i=0;i<count;i++)
        {
            int x=mTypeArray.getIndex(i);
//            Log.i("Info","下表:"+x);
            switch(x)
            {
                case R.styleable.SlidingMenu_RightPadding:
                    mRightPadding = mTypeArray.getDimensionPixelSize(x,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics()));

                    break;
            }
        }

        mTypeArray.recycle();//释放防止内存泄露

        mMenuWidth = mScreenWidth-mRightPadding;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获取自控   并且 设置其宽高
          */
        ViewGroup viewGroup= (ViewGroup) this.getChildAt(0);

        view = viewGroup.getChildAt(0);
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) view.getLayoutParams();
       if(lp!=null)
       {
           lp.width=mScreenWidth-mRightPadding;
           lp.height=mScreenHeight;
       }
        view.setLayoutParams(lp);


        View viewContent=viewGroup.getChildAt(1);
       LinearLayout.LayoutParams contentlp= (LinearLayout.LayoutParams) viewContent.getLayoutParams();
        contentlp.width=mScreenWidth;
        contentlp.height=mScreenHeight;
       viewContent.setLayoutParams(contentlp);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * 滑动到内容区域
         */
        smoothScrollTo(mMenuWidth,0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int currentScollX=this.getScrollX();

        switch(ev.getAction())
        {

            case MotionEvent.ACTION_UP:
                
                 if(currentScollX>mMenuWidth/2)
                 {
                     Log.i("Info","move up");
                     this.smoothScrollTo(mMenuWidth, 0);
                     isShow=false;
                 }else
                 {
                     this.smoothScrollTo(0, 0);
                     isShow=true;
                 }

               return true;
        }

        return super.onTouchEvent(ev);
    }


    /**
     * 滑动触发的监听回调
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.i("Info","滑动触发:"+l);

        ViewHelper.setTranslationX(view,l);

    }


    /**
     * 开关显示菜单或者关闭菜单
     *
     */
    public void switchButton()
    {
        if(isShow)
        {
            this.smoothScrollBy(mMenuWidth,0);
            isShow=false;

        }
        else
        {
            this.smoothScrollTo(0,0);
            isShow=true;
        }
    }

    
    @SuppressLint("NewApi") public void contentPager()
    {
    	this.setScrollX(mMenuWidth);
    	isShow=false;
    }




}

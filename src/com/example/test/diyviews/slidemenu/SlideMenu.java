package com.example.test.diyviews.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlideMenu extends HorizontalScrollView{

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuRightPadding;
    private int mMenuWidth = 0;
    
    private boolean once = false;
    private boolean isSlideOut;

    public static final int RIGHT_PADDING = 100;
    
    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        //WindowManager主要用来管理窗口的一些状态、属性、view增加、删除、更新、窗口顺序、消息收集和处理等.
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取DisplayMetrics对象，再获取屏幕的参数
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        //mScreenWidth屏幕宽度
        mScreenWidth = metrics.widthPixels;
        //px转dip
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SlideMenu.RIGHT_PADDING, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            
            mMenuWidth  = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(mMenuWidth, 0);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX >= mMenuWidth /2){
                    this.smoothScrollTo(mMenuWidth, 0);
                    isSlideOut = false;
                }else{
                    this.smoothScrollTo(0, 0);
                    isSlideOut = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void slideOutMenu(){
        if(!isSlideOut){
            this.smoothScrollTo(0, 0);
            isSlideOut = true;
        }else{
            return;
        }
     }  

    public void slideInMenu(){
        if(isSlideOut){
            this.smoothScrollTo(mMenuWidth, 0);
            isSlideOut = false;
        }else{
            return;
        }
    }

    public void switchMenu(){
        if(isSlideOut){
            slideInMenu();
        }else{
            slideOutMenu();
        }
    }
}
package com.example.test.touch;

import com.example.test.Result;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lw on 2015/12/2.
 */
public class MyViewGroupB extends ViewGroup {
    public MyViewGroupB(Context context) {
        super(context);
    }

    public MyViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int pad = 100;
        int width = (right - left)/count-pad;

        Result.append("ViewGroupB : "+" left="+ left +" top="+ top+" right="+ right+" bottom="+ bottom);
        for(int i = 0; i< count;i++){
            View view = getChildAt(i);
            view.layout(left + i*width, top, left + (i + 1)*width, top + width);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	 Result.append("ViewGroupB  dispatchTouchEvent "+ EventUtils.getMotion(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	 Result.append("ViewGroupB onInterceptTouchEvent "+ EventUtils.getMotion(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	 Result.append("ViewGroupB onTouchEvent "+ EventUtils.getMotion(event));
        return super.onTouchEvent(event);
    }
}

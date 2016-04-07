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
public class MyViewGroupA extends ViewGroup {
    public MyViewGroupA(Context context) {
        super(context);
    }

    public MyViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            View view = getChildAt(0);
            view.layout(l+100,t+200,r-100, b-200);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	 Result.append("ViewGroupA dispatchTouchEvent "+ EventUtils.getMotion(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	 Result.append("ViewGroupA onInterceptTouchEvent " + EventUtils.getMotion(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	 Result.append("ViewGroupA onTouchEvent "+  EventUtils.getMotion(event));
        return super.onTouchEvent(event);
    }
}

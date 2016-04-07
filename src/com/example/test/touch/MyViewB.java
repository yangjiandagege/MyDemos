package com.example.test.touch;

import com.example.test.Result;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lw on 2015/12/2.
 */
public class MyViewB extends View {
    public MyViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewB(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
    	 Result.append("ViewB dispatchTouchEvent "+EventUtils.getMotion(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	 Result.append("ViewB onTouchEvent "+EventUtils.getMotion(event));
        return super.onTouchEvent(event);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
    	 Result.append("ViewB layout : "+" left="+ l +" top="+ t+" right="+ r+" bottom="+ b);
        super.layout(l, t, r, b);
    }
}

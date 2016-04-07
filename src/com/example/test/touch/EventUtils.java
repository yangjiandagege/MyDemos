package com.example.test.touch;

import android.view.MotionEvent;

/**
 * Created by lw on 2015/12/2.
 */
public class EventUtils {

    public static String getMotion(MotionEvent ev){
        String  str ="";
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                str = "down";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "move";
                break;
            case MotionEvent.ACTION_UP:
                str = "up";
                break;
        }
        return str;
    }
}

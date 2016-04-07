package com.example.test.touch;

import com.example.test.R;
import com.example.test.Result;
import com.example.test.Util;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        Util.showResultDialog(this, Result.mResult, "info");
        Result.clean();
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Result.append("Main dispatchTouchEvent " + EventUtils.getMotion(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	Result.append("Main onTouchEvent "+ EventUtils.getMotion(event));
    	if (event.getAction() == MotionEvent.ACTION_UP){
    		Util.showResultDialog(this, Result.mResult, "info");
    		Result.clean();
    	}
        return super.onTouchEvent(event);
    }
}

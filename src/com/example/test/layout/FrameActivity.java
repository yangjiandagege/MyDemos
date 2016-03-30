package com.example.test.layout;

import com.example.test.R;
import android.app.Activity;
import android.os.Bundle;

public class FrameActivity extends Activity{
	String TAG = "FrameActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_frame);
	}
}

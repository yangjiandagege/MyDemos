package com.example.test.layout;

import com.example.test.R;
import android.app.Activity;
import android.os.Bundle;

public class MergeActivity extends Activity{
	String TAG = "MergeActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_merge);
	}
}
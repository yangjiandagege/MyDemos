package com.example.test.layout;

import com.example.test.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LinearActivity extends Activity{
	final String TAG = "LinearActivity";
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_linear);
		//Intent intent = getIntent();
		//Bundle bundle = intent.getExtras();
		//String name = bundle.getString("name");
		//Log.d(TAG,"name is " + name);
	}
}

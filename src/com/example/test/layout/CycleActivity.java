package com.example.test.layout;

import com.example.test.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class CycleActivity extends Activity {

	String TAG = "CycleActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_cycle);
		Log.d(TAG, "onCreate Method is executed.");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		Log.d(TAG, "onCreateOptionsMenu Method is executed.");
		return true;
	}
	
	protected void onStart(){
		super.onStart();
		Log.d(TAG, "onStart Method is executed.");
	}
	
	protected void onRestart(){
		super.onRestart();
		Log.d(TAG, "onRestart Method is executed.");
	}
	
	protected void onResume(){
		super.onResume();
		Log.d(TAG, "onResume Method is executed.");
	}
	
	protected void onPause(){
		super.onPause();
		Log.d(TAG, "onPause Method is executed.");
	}
	
	protected void onStop(){
		super.onStop();
		Log.d(TAG, "onStop Method is executed.");
	}
	
	protected void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "onDestroy Method is executed.");
	}
}

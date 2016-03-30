package com.example.test.controls;

import com.example.test.R;
import com.example.test.layout.CycleActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ButtonActivity extends Activity implements OnClickListener{
	final String TAG = "MainActivity";

	Button btnCycle,btnFrame,btnLinear,btnRelative,btnTable,btnReusable;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controls_button);
		btnCycle = (Button)this.findViewById(R.id.btn_cycle);
		btnFrame = (Button)this.findViewById(R.id.btn_frame);
		btnLinear = (Button)this.findViewById(R.id.btn_linear);
		btnRelative = (Button)this.findViewById(R.id.btn_relative);
		btnTable = (Button)this.findViewById(R.id.btn_table);
		btnReusable = (Button)this.findViewById(R.id.btn_reusable);
		
		btnCycle.setOnClickListener(new BtnCycleListener());
		btnFrame.setOnClickListener(this);
		btnLinear.setOnClickListener(this);
		btnRelative.setOnClickListener(this);
		btnTable.setOnClickListener(this);
		btnReusable.setOnClickListener(this);
		
		try {
			ActivityInfo activityInfo;
			activityInfo = this.getPackageManager().getActivityInfo(getComponentName(),PackageManager.GET_META_DATA);
			String metaData1 = activityInfo.metaData.getString("com.test.meta_data1");
			String metaData2 = activityInfo.metaData.getString("com.test.meta_data2");
			int imageId = activityInfo.metaData.getInt("com.test.meta_data3");
			Log.d(TAG,"metaData1 = "+metaData1+" ; metaData2 = "+metaData2+" ; imageId = "+imageId);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	    
	}
	
	class BtnCycleListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(ButtonActivity.this,CycleActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId())
		{
			case R.id.btn_frame:
				intent = new Intent();
				intent.setAction("android.intent.action.FRAME");
				startActivity(intent);
				break;
			case R.id.btn_linear:
				intent = new Intent();
				intent.setAction("android.intent.action.LINEAR");
				Bundle bundle = new Bundle();
				bundle.putString("name", "yangjian");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.btn_relative:
				intent = new Intent();
				intent.setAction("android.intent.action.RELATIVE");
				startActivity(intent);
				break;
			case R.id.btn_table:
				intent = new Intent();
				intent.setAction("android.intent.action.TABLE");
				startActivity(intent);
				break;
			case R.id.btn_reusable:
				intent = new Intent();
				intent.setAction("android.intent.action.REUSABLE");
				startActivity(intent);
				break;
		}
	}
}

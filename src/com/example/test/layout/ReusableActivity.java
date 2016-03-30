package com.example.test.layout;

import com.example.test.R;
import android.app.Activity;
import android.os.Bundle;

public class ReusableActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_layout_reusable);
	}
}

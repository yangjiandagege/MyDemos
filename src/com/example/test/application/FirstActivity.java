package com.example.test.application;

import com.example.test.R;
import com.example.test.TestApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends Activity{
    private TestApplication app;
    private Button btGoToSecondActivity;
    private TextView tvValue;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_application);
        app = (TestApplication) getApplication(); // 获得CustomApplication对象
        btGoToSecondActivity = (Button) this.findViewById(R.id.app_test) ;
        tvValue = (TextView) this.findViewById(R.id.app_value) ;
        btGoToSecondActivity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent();
		        intent.setClass(FirstActivity.this, SecondActivity.class);
		        startActivity(intent);
			}
        });
        app.setValue("TestApplication.value is xiyuan");
        tvValue.setText(app.getValue());
    }
}

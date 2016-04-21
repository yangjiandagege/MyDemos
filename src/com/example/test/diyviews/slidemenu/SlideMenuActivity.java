package com.example.test.diyviews.slidemenu;

import com.example.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SlideMenuActivity extends Activity {
	private SlideMenu xcSlideMenu;
	private TextView btnSwitch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_slidemenu_main);
		xcSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        btnSwitch = (TextView)findViewById(R.id.btnSwitch);
        btnSwitch.setClickable(true);
        btnSwitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                xcSlideMenu.switchMenu();
                //btnSwitch.setVisibility(View.INVISIBLE);
            }
        });
		
	}
}

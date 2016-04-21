package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public abstract class BaseActivity extends Activity  implements OnClickListener{
	protected static final int BT_NUM = 50;
	private BtPerform mBtPerform[] = new BtPerform[BT_NUM];
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		initUi(BaseActivity.this);
	}

	private void initUi(Context context){
		final ScrollView myScrollView = new ScrollView(context);
		final LinearLayout myLayout = new LinearLayout(context);  
		myLayout.setOrientation(LinearLayout.VERTICAL);
		myScrollView.addView(myLayout);
		mBtPerform = initButtons();
		for(int j = 0; mBtPerform != null ; j++){
			addButton(myLayout,mBtPerform[j].getId(),mBtPerform[j].getName(),mBtPerform[j].getColor());
			if(mBtPerform[j].getName().equalsIgnoreCase("end")){
				break;
			}
		}
		setContentView(myScrollView);
	}
	
	public BtPerform[] initButtons(){
		
		return null;
	}
	
	private void addButton(LinearLayout layout, int id, String msg, int color){
		Button btn = new Button(this); 
		btn.setText(msg);
		btn.setId(id);
		btn.setBackgroundColor(color);
		layout.addView(btn);
		btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		mBtPerform[view.getId()].perform();
	}
}

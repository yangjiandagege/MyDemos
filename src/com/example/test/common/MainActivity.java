package com.example.test.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.test.R;
import com.example.test.Util;
import com.example.test.utils.*;

public class MainActivity extends Activity  implements OnClickListener{
	private static final int GET_APP_NAME  = 1000;
	private static final int GET_APP_VERSION_NAME  = 1000 + 1;
	private static final int SERIALLIZABLE  = 1000 + 2;
	private static final int UNSERIALLIZABLE  = 1000 + 3;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		final LinearLayout myLayout = new LinearLayout(this);  
		myLayout.setOrientation(LinearLayout.VERTICAL);
		addButtion(myLayout, GET_APP_NAME, "getAppName");
		addButtion(myLayout, GET_APP_VERSION_NAME, "getAppVersionName");
		addButtion(myLayout, SERIALLIZABLE, "serializable");
		addButtion(myLayout, UNSERIALLIZABLE, "unserializable");
		setContentView(myLayout);
	}
	
	private void addButtion(LinearLayout layout, int id, String msg){
		Button btn = new Button(this); 
		btn.setText(msg);
		btn.setId(id);
		layout.addView(btn);
		btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId())
		{
			case GET_APP_NAME:
				getAppName();
				break;
			case GET_APP_VERSION_NAME:
				getAppVersionName();
				break;
			case SERIALLIZABLE:
				serializable();
				break;
			case UNSERIALLIZABLE:
				unserializable();
				break;
		}
	}

    void getAppName(){
    	Util.showResultDialog(this, AppUtils.getAppName(this), "App name :");
    }
    
    void getAppVersionName(){
    	Util.showResultDialog(this, AppUtils.getVersionName(this), "App version name :");
    }
    
    void serializable(){
		try {
        	User user = new User(0, "yangjian", true);
        	ObjectOutputStream out;
			out = new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/data.txt"));
        	out.writeObject(user);
        	out.close();
			Util.showResultDialog(this,user.toString() , "Serializable info :");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    void unserializable(){
		try {
			ObjectInputStream in;
			in = new ObjectInputStream(new FileInputStream(getFilesDir()+"/data.txt"));
        	User newUser = (User)in.readObject();
        	in.close();
        	Util.showResultDialog(this,newUser.toString() , "Unserializable info :");
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}

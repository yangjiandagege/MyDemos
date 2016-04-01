package com.example.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.example.test.utils.*;

public class MainActivity extends PreferenceActivity implements OnPreferenceClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_activity_main);
		findPreference(getResources().getString(R.string.GetAppName)).setOnPreferenceClickListener(this);
		findPreference(getResources().getString(R.string.GetAppVersionName)).setOnPreferenceClickListener(this);
		findPreference(getResources().getString(R.string.Serializable)).setOnPreferenceClickListener(this);
		findPreference(getResources().getString(R.string.Unserializable)).setOnPreferenceClickListener(this);
	}
	
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String preferenceKey=preference.getKey().toString();
        if (preferenceKey.equals(getResources().getString(R.string.GetAppName))){
        	getAppName();
        }else if (preferenceKey.equals(getResources().getString(R.string.GetAppVersionName))){
        	getAppVersionName();
        }else if (preferenceKey.equals(getResources().getString(R.string.Serializable))){
        	serializable();
        }else if (preferenceKey.equals(getResources().getString(R.string.Unserializable))){
        	unserializable();
        }
        return true;
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

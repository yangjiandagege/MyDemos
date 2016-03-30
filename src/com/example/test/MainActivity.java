package com.example.test;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class MainActivity extends PreferenceActivity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_activity_main);
		int id1 = getResources().getIdentifier("fsdfasfasf", "id", this.getPackageName());
		int id2 = getResources().getIdentifier("relative", "string", this.getPackageName());
		String string = getResources().getString(id2);
		Log.d("yangjian","yangjian resID = "+id1+" resString"+id2+string);
	}
}

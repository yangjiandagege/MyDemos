package com.example.test;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import com.example.test.utils.*;

public class MainActivity extends PreferenceActivity implements OnPreferenceClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_activity_main);
		findPreference(getResources().getString(R.string.GetAppName)).setOnPreferenceClickListener(this);
		findPreference(getResources().getString(R.string.GetAppVersionName)).setOnPreferenceClickListener(this);
	}
	
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String preferenceKey=preference.getKey().toString();
        if (preferenceKey.equals(getResources().getString(R.string.GetAppName))){
        	Util.showResultDialog(this, AppUtils.getAppName(this), "App name :");
        }else if (preferenceKey.equals(getResources().getString(R.string.GetAppVersionName))){
        	Util.showResultDialog(this, AppUtils.getVersionName(this), "App version name :");
        }
        return true;
    }

}

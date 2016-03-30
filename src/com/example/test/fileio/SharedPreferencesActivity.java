package com.example.test.fileio;

import com.example.test.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SharedPreferencesActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fileio_sharedpreferences);
	}

	public void onClick_WriteData(View view)
	{		
		SharedPreferences mySharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("name", "yangjian");
		editor.putString("habit", "basketball");
		editor.commit();
	}

	public void onClick_ReadData(View view)
	{
		SharedPreferences sharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("name", "");
		String habit = sharedPreferences.getString("habit", "");
		Toast.makeText(this, "name is " + name + "\n" + "habit is " + habit,Toast.LENGTH_LONG).show();
	}
}
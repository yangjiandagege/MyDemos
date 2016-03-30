package com.example.test.fileio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.test.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DataBaseActivity extends Activity{
	TextView tvResult = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fileio_database);
		InputStream is = getResources().openRawResource(R.raw.apk_test);
		tvResult = (TextView)this.findViewById(R.id.tv_result);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("/sdcard/apk_test.db");
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) >= 0)
			{
				fos.write(buffer, 0, count);
			}

			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onClickReadDatabase(View view){
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		Cursor cursor = sqLiteDatabase.rawQuery("select * from t_test",null);
		String result = "";
		while (cursor.moveToNext())
		{
			result = result + cursor.getString(1) + '\n';
		}
		tvResult.setText(result);
		cursor.close();
		sqLiteDatabase.close();
	}
	
	public void onClickInsertDatabase(View view){
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		String INSERT_DATA="insert into t_test(id,name,memo) values('3','yangjian','Student')";
		sqLiteDatabase.execSQL(INSERT_DATA);
		INSERT_DATA="insert into t_test(id,name,memo) values('4','xiyuan','Student')";
		sqLiteDatabase.execSQL(INSERT_DATA);
		sqLiteDatabase.close();
	}
	
	public void onClickUpdateDatabase(View view){
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		String UPDATE_DATA="update t_test set name='yangmiemie' where name='yangjian'";
		sqLiteDatabase.execSQL(UPDATE_DATA);
		UPDATE_DATA="update t_test set name='ximiaomiao' where name='xiyuan'";
		sqLiteDatabase.execSQL(UPDATE_DATA);
		sqLiteDatabase.close();
	}
}

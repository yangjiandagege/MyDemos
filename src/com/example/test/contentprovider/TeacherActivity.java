package com.example.test.contentprovider;

import java.util.Date;

import com.example.test.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
 
public class TeacherActivity extends Activity {
    Uri mUri = Uri.parse("content://yj.android.contentProvider/teacher");
    EditText mEtResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        mEtResult = (EditText) this.findViewById(R.id.etResult);
    }
 
    
	public void onInsert(View view){
        ContentResolver cr = getContentResolver();
        ContentValues cv_yuan = new ContentValues();
        cv_yuan.put(ContentData.UserTableData.TITLE, "proscenium");
        cv_yuan.put(ContentData.UserTableData.NAME, "xiyuan");
        cv_yuan.put(ContentData.UserTableData.SEX, "girl");
        cr.insert(mUri, cv_yuan);
        ContentValues cv_jian = new ContentValues();
        cv_jian.put(ContentData.UserTableData.TITLE, "boss");
        cv_jian.put(ContentData.UserTableData.NAME, "yangjian");
        cv_jian.put(ContentData.UserTableData.SEX, "boy");
        cr.insert(mUri, cv_jian);
        showResult(null);
    }

    public void onQuery(View view){
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(mUri, null, "_ID=?", new String[] { "1" }, null);
        showResult(c);
        c.close();
	}
	
	public void onQuerys(View view){
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(mUri, null, null,null, null);
        showResult(c);
        c.close();
	}
	
	public void onUpdate(View view){
        ContentResolver cr = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("name", "huangbiao");
        cv.put("date_added", (new Date()).toString());
        cr.update(mUri, cv, "_ID=?", new String[]{"3"});
        showResult(null);
	}

    public void onDelete(View view){
        ContentResolver cr = getContentResolver();
        cr.delete(mUri, "_ID=?", new String[]{"2"});
        showResult(null);
    }

    public void showResult(Cursor cursor){
    	ContentResolver cr = getContentResolver();
    	Cursor c = null;
    	if(cursor == null){
    		c = cr.query(mUri, null, null,null, null);
    	}else{
    		c = cursor;
    	}
		String result = "";
		 while(c.moveToNext() == true){
			 result += "name : "+c.getString(c.getColumnIndex(ContentData.UserTableData.NAME))+" | "+"title : "+c.getString(c.getColumnIndex(ContentData.UserTableData.TITLE))+" | "+"sex : "+c.getString(c.getColumnIndex(ContentData.UserTableData.SEX))+"\n";
		 }
		 c.close();
		 mEtResult.setText(result);
	}
}

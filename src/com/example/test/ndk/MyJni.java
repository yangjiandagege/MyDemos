package com.example.test.ndk;
import com.example.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyJni extends Activity{
	String TAG = "TestDNK";
	TextView tvResult = null;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myjni);
		 tvResult = (TextView)this.findViewById(R.id.tv_jni_result);
	}
	
	public void callJNI(View view){
		String result = stringFromJNI();
		tvResult.setText(result);
	}
	
    public native String  stringFromJNI();
    static {
           System.loadLibrary("TestNDK");
    }
}
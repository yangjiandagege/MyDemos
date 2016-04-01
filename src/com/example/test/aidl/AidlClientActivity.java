package com.example.test.aidl;

import com.example.test.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AidlClientActivity extends Activity implements OnClickListener {
	private ITaskBinder mService;
	private final String TAG="yangjian";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        findViewById(R.id.callButton).setOnClickListener(this);
        findViewById(R.id.callbackButton).setOnClickListener(this);
        findViewById(R.id.objectCallButton).setOnClickListener(this);
        findViewById(R.id.bindButton).setOnClickListener(this);
    }
    
	private void ToastInActivity(final String msg){
		Handler handler=new Handler(Looper.getMainLooper());
		 handler.post(new Runnable(){
			 public void run(){
				 Toast.makeText(getApplicationContext(), "From AidlActivity : "+msg, Toast.LENGTH_LONG).show();
			 } 
		 }
	 );
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		  case R.id.bindButton:
		      Intent intent=new Intent("com.example.test.aidl.AidlService");
			  bindService(intent,mConnection,BIND_AUTO_CREATE);
			  break;
		  case R.id.callButton:
			  try {
				mService.commonCall();
			  } catch (RemoteException e) {
				e.printStackTrace();
			  }
			  break;
		  case R.id.callbackButton:
			  try {
				mService.callback();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			  break;
		  case R.id.objectCallButton:
			  try {
				Person person=new Person();
				person.setSex(0);
				person.setName("YangJian");
				person.setDescrip("CEO");
				String ret=mService.objectCall(person);
				ToastInActivity("ret = "+ret);
			  } catch (RemoteException e) {
				e.printStackTrace();
			  }
			  break;
		  default:
			  break;
		}
	}
	
	private ServiceConnection mConnection=new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService=ITaskBinder.Stub.asInterface(service);
			try {
				mService.registerCallBack(mCallBack);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			try {
				mService.unregisterCallBack();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mService=null;
		}
	};
	
	private final ITaskCallBack.Stub mCallBack=new ITaskCallBack.Stub() {
		@Override
		public void onActionBack(String str) throws RemoteException {
			ToastInActivity("onActionBack str = "+str);
		}
	};
}
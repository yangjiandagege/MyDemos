package com.example.test.aidl;

import com.example.test.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AidlClientActivity extends Activity implements OnClickListener {
	private ITaskBinder mService;
	private final String TAG="yangjian";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Button fuc01=(Button)findViewById(R.id.fuc01Button);
        fuc01.setOnClickListener(this);
        Button fuc02=(Button)findViewById(R.id.fuc02Button);
        fuc02.setOnClickListener(this);
        Button fuc03=(Button)findViewById(R.id.fuc03Button);
        fuc03.setOnClickListener(this);
        Button bindBtn=(Button)findViewById(R.id.bindButton);
        bindBtn.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		  case R.id.bindButton:
		      Intent intent=new Intent("com.example.test.aidl.AidlService");
			  bindService(intent,mConnection,BIND_AUTO_CREATE);
			  break;
		  case R.id.fuc01Button:
			  try {
				mService.fuc01();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			  break;
		  case R.id.fuc02Button:
			  try {
				mService.fuc02();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			  break;
		  case R.id.fuc03Button:
			  try {
				Person person=new Person();
				person.setSex(0);
				person.setName("YangJian");
				person.setDescrip("CEO");
				String ret=mService.fuc03(person);
				Log.v(TAG,"ret="+ret);
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
			Log.v(TAG,"onServiceConnected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			try {
				mService.unregisterCallBack();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mService=null;
			Log.v(TAG,"onServiceDisconnected");
		}
	};
	
	private final ITaskCallBack.Stub mCallBack=new ITaskCallBack.Stub() {
		@Override
		public void onActionBack(String str) throws RemoteException {
			Log.v(TAG,"onActionBack str="+str);
		}
	};
}
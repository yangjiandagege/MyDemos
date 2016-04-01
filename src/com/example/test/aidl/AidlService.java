package com.example.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

public class AidlService extends Service {
	
    private ITaskCallBack mTaskCallBack;
    @Override
	public void onCreate(){
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	private final ITaskBinder.Stub mBinder=new ITaskBinder.Stub() {
		
		@Override
		public void unregisterCallBack() throws RemoteException {
			mTaskCallBack = null;
			ToastInServer("unregisterCallBack");
		}
		
		@Override
		public void registerCallBack(ITaskCallBack cb) throws RemoteException {
			mTaskCallBack = cb;
			ToastInServer("registerCallBack");
		}
		
		@Override
		public void commonCall() throws RemoteException {
			ToastInServer("common call");
		}

		@Override
		public void callback() throws RemoteException {
			ToastInServer("I will call AidlActivity");
			mTaskCallBack.onActionBack("AidlService call AidlActivity");
		}

		@Override
		public String objectCall(Person person) throws RemoteException {
			ToastInServer("I will call AidlActivity");
			String name=person.getName();
			String descrip=person.getDescrip();
			int sex=person.getSex();
			String ret="";
			if(sex==0){
				ret="Hello "+name+",you are handsome"+"("+descrip+")";
			}else{
				ret="Hello "+name+",you are beautiful"+"("+descrip+")";
			}
			return ret;
		}
	};
    
	private void ToastInServer(final String msg){
		Handler handler=new Handler(Looper.getMainLooper());
		 handler.post(new Runnable(){
			 public void run(){
				 Toast.makeText(getApplicationContext(), "From AidlService : "+msg, Toast.LENGTH_LONG).show();
			 } 
		 }
	 );
	}
}

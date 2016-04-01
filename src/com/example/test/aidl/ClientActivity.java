package com.example.test.aidl;

import com.example.test.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClientActivity extends Activity implements OnClickListener {
	private static Context mContext;
	private ITaskBinder mService;
    private Messenger mMessenger;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MyConstants.MSG_FROM_SERVICE:
            	Toast.makeText(mContext, "From AidlActivity : receive msg from Service:" + msg.getData().getString("reply"), Toast.LENGTH_LONG).show();
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        mContext = this;
        findViewById(R.id.callButton).setOnClickListener(this);
        findViewById(R.id.callbackButton).setOnClickListener(this);
        findViewById(R.id.objectCallButton).setOnClickListener(this);
        findViewById(R.id.bindButton).setOnClickListener(this);
        findViewById(R.id.messengerBindButton).setOnClickListener(this);
        findViewById(R.id.messengerButton).setOnClickListener(this);
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
				Toast.makeText(mContext, "From AidlActivity : ret = "+ret, Toast.LENGTH_LONG).show();
			  } catch (RemoteException e) {
				e.printStackTrace();
			  }
			  break;
		  case R.id.messengerBindButton:
		        Intent intentMsg = new Intent("com.example.test.aidl.MessengerService");
		        bindService(intentMsg, mMsgConnection, Context.BIND_AUTO_CREATE);
			  break;
		  case R.id.messengerButton:
	            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
	            Bundle data = new Bundle();
	            data.putString("msg", "hello, this is client.");
	            msg.setData(data);
	            msg.replyTo = mGetReplyMessenger;
	            try {
	            	mMessenger.send(msg);
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
	
    private ServiceConnection mMsgConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mMessenger = new Messenger(service);
        }
        public void onServiceDisconnected(ComponentName className) {
        }
    };
	
	private final ITaskCallBack.Stub mCallBack=new ITaskCallBack.Stub() {
		@Override
		public void onActionBack(String str) throws RemoteException {
			Toast.makeText(mContext, "From AidlActivity : onCallback reply = "+str, Toast.LENGTH_LONG).show();
		}
	};
}
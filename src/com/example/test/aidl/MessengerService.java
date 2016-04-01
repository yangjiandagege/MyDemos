package com.example.test.aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    private static Context mContext;
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MyConstants.MSG_FROM_CLIENT:
            	ToastInServer(mContext, "receive msg from Client:" + msg.getData().getString("msg"));
                Messenger client = msg.replyTo;
                Message relpyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                Bundle bundle = new Bundle();
                bundle.putString("reply", "Hi , I'm MessengerService. How are you.");
                relpyMessage.setData(bundle);
                try {
                    client.send(relpyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
    	ToastInServer(mContext, "onBind ok" );
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    
	private static void ToastInServer(final Context context, final String msg){
		Handler handler=new Handler(Looper.getMainLooper());
		 handler.post(new Runnable(){
			 public void run(){
				 Toast.makeText(context, "From MessengerService : "+msg, Toast.LENGTH_LONG).show();
			 } 
		 }
	 );
	}
}

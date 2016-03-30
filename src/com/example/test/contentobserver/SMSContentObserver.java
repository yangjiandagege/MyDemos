package com.example.test.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
//�����۲�ϵͳ�����Ϣ����ݿ�仯  ���?���ݹ۲���,ֻҪ��Ϣ��ݿⷢ��仯�����ᴥ����ContentObserver ������
public class SMSContentObserver extends ContentObserver {
    private static String TAG = "SMSContentObserver";
	
    private int MSG_OUTBOXCONTENT = 2 ;
    
	private Context mContext ;
	private Handler mHandler ; 
	
	public SMSContentObserver(Context context,Handler handler) {
		super(handler);
		mContext = context ;
		mHandler = handler ;
	}

    @Override
	public void onChange(boolean selfChange){
    	Uri outSMSUri = Uri.parse("content://sms/sent") ;
    	
    	Cursor c = mContext.getContentResolver().query(outSMSUri, null, null, null,"date desc");
    	if(c != null){
        	StringBuilder sb = new StringBuilder() ;
        	while(c.moveToNext()){
        		sb.append("address : "+c.getInt(c.getColumnIndex("address"))).append("\n")
      		      .append("body : "+c.getString(c.getColumnIndex("body"))).append("\n")
      		      .append("read : "+c.getInt(c.getColumnIndex("read"))).append("\n")
      		      .append("date : "+c.getInt(c.getColumnIndex("date"))).append("\n");
        	}
        	c.close();       	
        	mHandler.obtainMessage(MSG_OUTBOXCONTENT, sb.toString()).sendToTarget();       	
    	}
	}
	
}

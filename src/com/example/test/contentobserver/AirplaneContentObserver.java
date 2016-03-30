package com.example.test.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.*;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;


//�����۲�system�������ģʽ�������Ƿ���仯 �� ���С����ݹ۲���
public class AirplaneContentObserver extends ContentObserver {

	private static String TAG = "AirplaneContentObserver" ;
	
	private static int MSG_AIRPLANE = 1 ;
	
	private Context mContext;    
    private Handler mHandler ;  //��Handler��������UI�߳�
    
	public AirplaneContentObserver(Context context, Handler handler) {
		super(handler);
		mContext = context;
		mHandler = handler ;
	}

	/**
	 * ��������Uri����ı�ʱ���ͻ�ص��˷���
	 * 
	 * @param selfChange ��ֵ���岻�� һ������¸ûص�ֵfalse
	 */
	@Override
	public void onChange(boolean selfChange) {
        Log.i(TAG, "-------------the airplane mode has changed-------------");
        
		try {
			int isAirplaneOpen = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON);
		    Log.i(TAG, " isAirplaneOpen -----> " +isAirplaneOpen) ;
		    mHandler.obtainMessage(MSG_AIRPLANE,isAirplaneOpen).sendToTarget() ;
		}
		catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

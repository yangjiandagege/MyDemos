package com.example.test.binderpool;


import com.example.test.R;
import com.example.test.Util;
import com.example.test.binderpool.ICompute;
import com.example.test.binderpool.ISecurityCenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class BinderPoolActivity extends Activity {
	private static final String TAG = "BinderPoolActivity";
	
    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;
    private String mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {

            @Override
            public void run() {
                initBinder();
            }
        }).start();
    }
	
	public void encrypt(View view){
		 String msg = "helloworld-安卓";
		 try {
			 mPassword = mSecurityCenter.encrypt(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Util.showResultDialog(this, mPassword, "Encrypted");
	}
	
	public void decrypt(View view){
		 try {
			 Util.showResultDialog(this,  mSecurityCenter.decrypt(mPassword),"Decrypt");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void add(View view){
		try {
			Util.showResultDialog(this,"3+5=" + mCompute.add(3, 5),"Add");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

    private void initBinder() {
        BinderPool binderPool = BinderPool.getInsance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = (ISecurityCenter) SecurityCenterImpl.asInterface(securityBinder);
        
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
    }
}

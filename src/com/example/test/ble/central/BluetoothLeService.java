/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.test.ble.central;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.test.ble.BleConstants;
import com.example.test.ble.BleMsgCtrol;
import com.example.test.ble.ByteUtils;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
@SuppressLint("NewApi")
public class BluetoothLeService extends Service {
    private final static String TAG = "yangjian";

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;
    private BleMsgCtrol mBleMsgCtrol = new BleMsgCtrol();
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED           = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED        = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE           = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA                      = "com.example.bluetooth.le.EXTRA_DATA";

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
        	setCharacteristicNotification(mBleMsgCtrol.mE3ReadRspDataChar, true);
    		boolean flag = mBluetoothGatt.readCharacteristic(mBleMsgCtrol.mE3ReadRspDataChar);
        	Log.d("yangjian","["+Thread.currentThread().getStackTrace()[2].getFileName()+","+
      	           Thread.currentThread().getStackTrace()[2].getMethodName()+","+
      	           Thread.currentThread().getStackTrace()[2].getLineNumber()+"]"+flag
      	           +mBleMsgCtrol.mE3ReadRspDataChar.getPermissions()
      	           +mBleMsgCtrol.mE3ReadRspDataChar.getProperties());
		}
	};
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
                BluetoothGattCharacteristic characteristic;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status){
        	String uuid = characteristic.getUuid().toString();
        	if (uuid.equals(BleConstants.REQUEST_START)
        			|| uuid.equals(BleConstants.REQUEST_DATA)) {
        		byte[] currentBytes = mBleMsgCtrol.getCurrentReqBytes();
        		if (currentBytes != null) {
                	Log.d("yangjian","" + ByteUtils.bytesToHexString(currentBytes));
        			setCharacteristicNotification(mBleMsgCtrol.mE3WriteReqDataChar, true);
            		mBleMsgCtrol.mE3WriteReqDataChar.setValue(currentBytes);
            		writeCharacteristic(mBleMsgCtrol.mE3WriteReqDataChar);
        		} else {
        			setCharacteristicNotification(mBleMsgCtrol.mE3WriteReqEndChar, true);
            		mBleMsgCtrol.mE3WriteReqEndChar.setValue("stop");
            		writeCharacteristic(mBleMsgCtrol.mE3WriteReqEndChar);
        		}
        	}
        }
        
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
        	if (characteristic.getUuid().toString().equals(BleConstants.RESPONSE_DATA)) {
        		final byte[] data = characteristic.getValue();
            	Log.d("yangjian","["+Thread.currentThread().getStackTrace()[2].getFileName()+","+
          	           Thread.currentThread().getStackTrace()[2].getMethodName()+","+
          	           Thread.currentThread().getStackTrace()[2].getLineNumber()+"]"+ByteUtils.bytesToHexString(data));
            	
        	}
        	
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
        	Log.d("yangjian","["+Thread.currentThread().getStackTrace()[2].getFileName()+","+
     	           Thread.currentThread().getStackTrace()[2].getMethodName()+","+
     	           Thread.currentThread().getStackTrace()[2].getLineNumber()+"]"+characteristic.getUuid());
        	if (characteristic.getUuid().toString().equals(BleConstants.RESPONSE_START)) {
            	Log.d("yangjian","["+Thread.currentThread().getStackTrace()[2].getFileName()+","+
         	           Thread.currentThread().getStackTrace()[2].getMethodName()+","+
         	           Thread.currentThread().getStackTrace()[2].getLineNumber()+"]");
				Message msg = new Message();
				msg.what = 0;
				mHandler.sendMessage(msg);
        	}
            //broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        // For all other profiles, writes the data formatted in HEX.
        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        return mBluetoothGatt.readCharacteristic(characteristic);
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }

    public void writeCharacteristic(BluetoothGattCharacteristic characteristic){
   	 	mBluetoothGatt.writeCharacteristic(characteristic);
    }
    
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }
    
    public void sendMsg(BleMsgCtrol bmc) {
    	mBleMsgCtrol = bmc;
    	mBleMsgCtrol.clearOffsetReq();
    	mBleMsgCtrol.mE3WriteReqStartChar.setValue("go");
	    writeCharacteristic(mBleMsgCtrol.mE3WriteReqStartChar);
    }
}

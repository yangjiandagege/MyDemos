package com.example.test.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

public class BleMsgCtrol {
	
	private int offsetReq = 0;
	private int offsetRsp = 0;
	public BluetoothGattService mE3GattService;
	public BluetoothGattCharacteristic mE3WriteReqDataChar;
	public BluetoothGattCharacteristic mE3WriteReqStartChar;
	public BluetoothGattCharacteristic mE3WriteReqEndChar;
	public BluetoothGattCharacteristic mE3NotifyRspStartChar;
	public BluetoothGattCharacteristic mE3ReadRspDataChar;
	public BluetoothGattCharacteristic mE3WriteRspIncrementChar;
	
	public byte[][] mReqBytes;
	public byte[][] mRspBytes;
	
	public void clearOffsetReq() {
		offsetReq = 0;
	}
	
	public void clearOffsetRsp() {
		offsetRsp = 0;
	}
	
	public byte[] getCurrentReqBytes() {
		byte[] res = null;
		if (offsetReq < mReqBytes.length) {
			res = mReqBytes[offsetReq];
			offsetReq ++;
		}
		return res;
	}
	
	public byte[] getCurrentRspBytes() {
		byte[] res = null;
		if (offsetRsp < mRspBytes.length) {
			res = mReqBytes[offsetRsp];
			offsetRsp ++;
		}
		return res;
	}
}

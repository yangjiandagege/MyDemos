package com.example.test.ble.peripheral;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.test.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.ble.AesTools;
import com.example.test.ble.BleMsgCtrol;
import com.example.test.ble.BleStateMachine;
import com.example.test.ble.ByteUtils;
import com.example.test.ble.BleConstants;
import com.example.test.ble.IAction;
import com.example.test.ble.Msg;
import com.example.test.ble.MsgAdapter;
import com.example.test.ble.central.DeviceControlActivity;

public class PeripheralActivity extends Activity{
	private static final String TAG = "PeripheralActivity";
	private Context mContext;
	private ListView msgListView;
	private EditText msgEditText;
	private Button btnSend;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();
	
	private BluetoothManager mBluetoothManager; 
    private BluetoothGattServer  mGattServer; 
    private BluetoothAdapter mAdapter;
    private BluetoothLeAdvertiser mLeAdvertiser;
    private AdvertiseCallback mAdvertiseCallback; 
    
    private BleMsgCtrol mBleMsgCtrol = new BleMsgCtrol();
    private Timer mTimeOut = null;
    private int mRspOffset = 0;
	private byte[]   mReqJsonBytes = null;
	private byte[][] mRspJsonBytes = null;
    
    private int mReadRspState = 0;
    
    private static final int  RESPONSE_STATE_IDLE = 0;
    private static final int  RESPONSE_STATE_READY = 1;
    private static final int  RESPONSE_STATE_END = 2;
    
    BleStateMachine mBleStateMachine = new BleStateMachine();
    
    private StartReqAction mStartReqAction = new StartReqAction();
    private ReceivingReqAction mReceivingReqAction = new ReceivingReqAction();
    private ProcessingReqAction mProcessingReqAction = new ProcessingReqAction();
    private SendingRspAction mSendingRspAction = new SendingRspAction();
    private ReturnIdleAction mReturnIdleAction = new ReturnIdleAction();
    
    private String mAutoReply = "Hi";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_chatting);
        adapter = new MsgAdapter(PeripheralActivity.this, R.xml.msg_item, msgList);
        msgEditText = (EditText) findViewById(R.id.input_et);
        btnSend = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list);
        msgListView.setAdapter(adapter);
        btnSend.setOnClickListener(mOnSendClickListener);
		mContext = this;
		initDevice();
		initStateMachine();
	}
	
    OnClickListener mOnSendClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String content = msgEditText.getText().toString();
			if (!"".equals(content)) {
				Msg msg = new Msg(content, Msg.TYPE_SENT);
				msgList.add(msg);
				adapter.notifyDataSetChanged();
				msgListView.setSelection(msgList.size());
				msgEditText.setText("");
			    
			    try {
			    	byte[] bytes = AesTools.getInstance().encryptReq(content.getBytes("UTF8"));
			    	Log.d("yangjian","all data : "+ByteUtils.bytesToHexString(bytes));
//					mBleMsgCtrol.mReqJsonBytes = ByteUtils.bytesSplit(bytes, BleConstants.BLE_MAX_DATA_ONCE);
//					mBluetoothLeService.sendMsg(mBleMsgCtrol);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				};
			}
		}
	};
	
	void initDevice() {
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(mContext, "BLE is not supported", Toast.LENGTH_LONG).show();
        }
		
		mBluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
		mAdapter = mBluetoothManager.getAdapter();
		if (mAdapter == null) {
			Toast.makeText(mContext, "Bluetooth is not supported.", Toast.LENGTH_LONG).show();
		} else {
			Log.v(TAG, "Bluetooth is supported.");
		}
		
		if(!mAdapter.isEnabled()) {
		    mAdapter.enable();	
		}

		mAdapter.setName("yj");
		
		mLeAdvertiser = mAdapter.getBluetoothLeAdvertiser();
        if(mLeAdvertiser == null) {
        	Toast.makeText(mContext, "The device can not play as  peripheral device", Toast.LENGTH_LONG).show();
        }
        
        mAdvertiseCallback = new TestAdvertiseCallback();
        
        startAdvertising();   
	}
	
	void initStateMachine(){
	    mBleStateMachine.insertAction(BleStateMachine.STATE_BEGIN, BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_BEGIN,null);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_IDLE,BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_EVT_REQ_START,mStartReqAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_EVT_REQ_RECEIVING,mReceivingReqAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_STATE_PROCESSING_REQ,BleConstants.BLE_EVT_REQ_END,mProcessingReqAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_PROCESSING_REQ,BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_EVT_RSP_SENDING,mSendingRspAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_EVT_RSP_SENDING,mSendingRspAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_RSP_END,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_RECEIVE_ERROR,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_SEND_ERROR,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_RECEIVING_REQ,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_TIMEOUT,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_PROCESSING_REQ,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_TIMEOUT,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_SENDING_RSP,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_TIMEOUT,mReturnIdleAction);
	    mBleStateMachine.insertAction(BleConstants.BLE_STATE_IDLE,BleConstants.BLE_STATE_IDLE,BleConstants.BLE_EVT_TIMEOUT,mReturnIdleAction);
	    mBleStateMachine.inputEvent(BleConstants.BLE_EVT_BEGIN, null);
	}

	/*************START : BLE Action******************/
	private class StartReqAction implements IAction{
		public boolean performAction(byte[] value) {

			mTimeOut = new Timer();
			mTimeOut.schedule(new TimerTask() {
				public void run() {
					Log.d("yangjian","Start timeout with 5s");
					mBleStateMachine.inputEvent(BleConstants.BLE_EVT_TIMEOUT, null);
				}
			}, BleConstants.BLE_SET_TIMEOUT*1000);
			mReqJsonBytes = null;
			return true;
		}
	}
	
	private class ReceivingReqAction implements IAction{
		public boolean performAction(byte[] value) {
			Log.d("yangjian","step 2 : Received a data chunk , data type is RECEIVING_REQUEST , size = "+value.length+" value = "+ByteUtils.bytesToHexString(value));//
    		mReqJsonBytes = ByteUtils.byteMerger(mReqJsonBytes, value);
			return true;
		}
	}
	
	private class ProcessingReqAction implements IAction{
		public boolean performAction(byte[] value) {
    		String reqString = null;
    		byte[] rspData = null;
        	try {
    			reqString = new String(AesTools.getInstance().decryptReq(mReqJsonBytes) ,"UTF-8");

				Msg msg = new Msg(reqString, Msg.TYPE_SENT);
				msgList.add(msg);
				adapter.notifyDataSetChanged();
				msgListView.setSelection(msgList.size());
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return true;
		}
	}
	
	private class SendingRspAction implements IAction{
		public boolean performAction(byte[] value) {
    		mRspOffset ++;
    		if(mRspOffset < mRspJsonBytes.length){
    			Log.d("yangjian","step 8 : SC Client read response data from SC Station , mRspOffset = "+mRspOffset+" , State change to SENDING_RESPONSE from PROCESSING_REQUEST");
            	mReadRspState = RESPONSE_STATE_READY;
    		}else{
    			Log.d("yangjian","step 9 : SC Client read response data Over , State change to IDLE from SENDING_RESPONSE");
            	mReadRspState = RESPONSE_STATE_END;
    		}
			return true;
		}
	}
	
	private class ReturnIdleAction implements IAction{
		public boolean performAction(byte[] value) {
			Log.d("yangjian","End timeout");
			mTimeOut.cancel();
			return true;
		}
	}
	/**************END : BLE Action*****************/
	
	private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback(){
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState){
            super.onConnectionStateChange(device, status, newState);
            Toast.makeText(mContext, "onConnectionStateChange", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
            Toast.makeText(mContext, "onServiceAdded", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, 
                                                BluetoothGattCharacteristic characteristic) {
        	Log.d("yangjian","["+Thread.currentThread().getStackTrace()[2].getFileName()+","+
        	           Thread.currentThread().getStackTrace()[2].getMethodName()+","+
        	           Thread.currentThread().getStackTrace()[2].getLineNumber()+"]");
        	byte[] sentinel_array = new byte[0];
        	if(characteristic.getUuid().toString().equalsIgnoreCase(BleConstants.RESPONSE_DATA)){
        		byte currentBytes[] = mBleMsgCtrol.getCurrentRspBytes();
        		if (currentBytes != null) {
        			Log.d("yangjian","------currentBytes = "+currentBytes);
        			characteristic.setValue(currentBytes);
        		} else {
        			characteristic.setValue(sentinel_array);
        		}
        	}
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, 
                                                 boolean preparedWrite, boolean responseNeeded, int offset, byte[] value)  {
        	String uuid = characteristic.getUuid().toString();
        	
        	if(uuid.equalsIgnoreCase(BleConstants.REQUEST_START)){
        		mBleStateMachine.inputEvent(BleConstants.BLE_EVT_REQ_START, null);
        	}else if(uuid.equalsIgnoreCase(BleConstants.REQUEST_DATA)){
        		if(mBleStateMachine.inputEvent(BleConstants.BLE_EVT_REQ_RECEIVING, value) == -1){
        			mBleStateMachine.inputEvent(BleConstants.BLE_EVT_RECEIVE_ERROR, null);
        		}
        	}else if(uuid.equalsIgnoreCase(BleConstants.REQUEST_END)){
        		mBleStateMachine.inputEvent(BleConstants.BLE_EVT_REQ_END, null);

				try {
					byte[] rspData = AesTools.getInstance().encryptRsp(mAutoReply.getBytes("UTF8"));
					mBleMsgCtrol.clearOffsetRsp();
					mBleMsgCtrol.mRspBytes = ByteUtils.bytesSplit(rspData, BleConstants.BLE_MAX_DATA_ONCE);
	            	mBleMsgCtrol.mE3NotifyRspStartChar.setValue("go");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

        		mGattServer.notifyCharacteristicChanged(device, mBleMsgCtrol.mE3NotifyRspStartChar, false);
        	}else if(characteristic.getUuid().toString().equalsIgnoreCase(BleConstants.RESPONSE_INCREMENT)){
        		if(mBleStateMachine.inputEvent(BleConstants.BLE_EVT_RSP_SENDING, null) == -1){
        			mBleStateMachine.inputEvent(BleConstants.BLE_EVT_SEND_ERROR, null);
        		}
        	}else{
        		Log.d("yangjian","write characteristic error !");
        	}
        	mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null);
        	super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status){
            super.onNotificationSent(device, status);
        }
        
        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, 
                                            BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, 
                                             BluetoothGattDescriptor descriptor, boolean preparedWrite, 
                                             boolean responseNeeded, int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            super.onExecuteWrite(device, requestId, execute);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null);
        }
    };
	
    private class TestAdvertiseCallback extends AdvertiseCallback {
    	@Override
		public void onStartFailure(int errorCode) {
			super.onStartFailure(errorCode);
			if(errorCode == ADVERTISE_FAILED_DATA_TOO_LARGE){
				Toast.makeText(mContext, "ADVERTISE_FAILED_DATA_TOO_LARGE", Toast.LENGTH_LONG).show();
			}else if(errorCode == ADVERTISE_FAILED_TOO_MANY_ADVERTISERS){
				Toast.makeText(mContext, "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS", Toast.LENGTH_LONG).show();
			}else if(errorCode == ADVERTISE_FAILED_ALREADY_STARTED){
				Toast.makeText(mContext, "ADVERTISE_FAILED_ALREADY_STARTED", Toast.LENGTH_LONG).show();
			}else if(errorCode == ADVERTISE_FAILED_INTERNAL_ERROR){
				Toast.makeText(mContext, "ADVERTISE_FAILED_INTERNAL_ERROR", Toast.LENGTH_LONG).show();
			}else if(errorCode == ADVERTISE_FAILED_FEATURE_UNSUPPORTED){
				Toast.makeText(mContext, "ADVERTISE_FAILED_FEATURE_UNSUPPORTED", Toast.LENGTH_LONG).show();
			}
 	    }
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
        	Toast.makeText(mContext, "Start advertiser successfully", Toast.LENGTH_LONG).show();
            super.onStartSuccess(settingsInEffect);
        }
    }
    
	public void startAdvertising() {
        if (mLeAdvertiser != null) {
        	// build SC Station Gatt Service
        	mGattServer = mBluetoothManager.openGattServer(this, mGattServerCallback);
            mBleMsgCtrol.mE3WriteReqDataChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.REQUEST_DATA), 
    				BluetoothGattCharacteristic.PROPERTY_WRITE,BluetoothGattCharacteristic.PERMISSION_WRITE);
            mBleMsgCtrol.mE3WriteReqStartChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.REQUEST_START), 
    				BluetoothGattCharacteristic.PROPERTY_WRITE,BluetoothGattCharacteristic.PERMISSION_WRITE);    
            mBleMsgCtrol.mE3WriteReqEndChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.REQUEST_END), 
    				BluetoothGattCharacteristic.PROPERTY_WRITE,BluetoothGattCharacteristic.PERMISSION_WRITE); 
            mBleMsgCtrol.mE3ReadRspDataChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.RESPONSE_DATA), 
    				BluetoothGattCharacteristic.PROPERTY_READ,BluetoothGattCharacteristic.PERMISSION_READ);
            mBleMsgCtrol.mE3NotifyRspStartChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.RESPONSE_START), 
    				BluetoothGattCharacteristic.PROPERTY_NOTIFY,BluetoothGattCharacteristic.PERMISSION_READ);
            mBleMsgCtrol.mE3WriteRspIncrementChar = new BluetoothGattCharacteristic(UUID.fromString(BleConstants.RESPONSE_INCREMENT), 
    				BluetoothGattCharacteristic.PROPERTY_WRITE,BluetoothGattCharacteristic.PERMISSION_WRITE);
            BluetoothGattService RequestResponseService = new BluetoothGattService(UUID.fromString(BleConstants.SERVICE_UUID),BluetoothGattService.SERVICE_TYPE_PRIMARY);
    		RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3WriteReqDataChar);
    		RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3WriteReqStartChar);
    		RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3WriteReqEndChar);
    		RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3ReadRspDataChar);
            RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3NotifyRspStartChar);
            RequestResponseService.addCharacteristic(mBleMsgCtrol.mE3WriteRspIncrementChar);
            mGattServer.addService(RequestResponseService);
            mLeAdvertiser.startAdvertising(buildAdvertiseSettings(), buildAdvertiseData(),mAdvertiseCallback);
        } 
    }
	
	private AdvertiseSettings buildAdvertiseSettings() {
        AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        return settingsBuilder.build();
    }
	
	private AdvertiseData buildAdvertiseData() {
    	AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
    	dataBuilder.addServiceUuid(ParcelUuid.fromString(BleConstants.SERVICE_UUID));
		dataBuilder.setIncludeDeviceName(true);
        return dataBuilder.build();
    }
}

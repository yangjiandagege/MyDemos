package com.example.test.ble;

public class BleConstants {
	/********BLE UUIDs*******************/
	public static final String SERVICE_UUID = "922d84d0-1ab3-4a6c-bd7a-fb00780a3dee";
	public static final String REQUEST_DATA = "89dafb6e-bb39-4aad-a7a3-78cd1d050470";
	public static final String REQUEST_START = "424c164e-2589-4e81-867e-ff8eb4bc85f4";
	public static final String REQUEST_END = "5fc614f4-3429-4814-8a05-52b1a515ae5b";
	public static final String RESPONSE_DATA = "c3d2cdcf-fc9d-4e99-9d18-0cc98c61b557";
	public static final String RESPONSE_START = "6ac603f8-e0f5-4907-b5ce-61496bd819b4";
	public static final String RESPONSE_INCREMENT = "827f70e3-188d-4565-9930-b3eb98ab5506";
	
	/********BLE event*******************/
	public static final int BLE_EVT_BEGIN = 0;
	public static final int BLE_EVT_REQ_START = 1;
	public static final int BLE_EVT_REQ_RECEIVING = 2;
	public static final int BLE_EVT_REQ_END = 3;
	public static final int BLE_EVT_RSP_SENDING = 4;
	public static final int BLE_EVT_RSP_END = 5;
	public static final int BLE_EVT_RECEIVE_ERROR = 6;
	public static final int BLE_EVT_SEND_ERROR = 7;
	public static final int BLE_EVT_TIMEOUT = 8;
	
	/********BLE state*******************/
    public static final int BLE_STATE_BEGIN = BleStateMachine.STATE_BEGIN;
	public static final int BLE_STATE_IDLE = BLE_STATE_BEGIN + 1;
	public static final int BLE_STATE_RECEIVING_REQ = BLE_STATE_BEGIN + 2;
	public static final int BLE_STATE_PROCESSING_REQ = BLE_STATE_BEGIN + 3;
	public static final int BLE_STATE_SENDING_RSP = BLE_STATE_BEGIN + 4;
	
	public static final int BLE_SET_TIMEOUT = 5;
	
	public static final int BLE_MAX_DATA_ONCE = 17;
}

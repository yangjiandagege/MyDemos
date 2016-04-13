package com.example.test.ble;

public class Constants {
	/********BLE UUIDs*******************/
	public static final String SERVICE_UUID = "922D84D0-1AB3-4A6C-BD7A-FB00780A3DEE";
	public static final String REQUEST_DATA = "89DAFB6E-BB39-4AAD-A7A3-78CD1D050470";
	public static final String REQUEST_START = "424C164E-2589-4E81-867E-FF8EB4BC85F4";
	public static final String REQUEST_END = "5FC614F4-3429-4814-8A05-52B1A515AE5B";
	public static final String RESPONSE_DATA = "C3D2CDCF-FC9D-4E99-9D18-0CC98C61B557";
	public static final String RESPONSE_START = "6AC603F8-E0F5-4907-B5CE-61496BD819B4";
	public static final String RESPONSE_INCREMENT = "827F70E3-188D-4565-9930-B3EB98AB5506";
	
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
}

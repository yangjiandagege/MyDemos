package com.example.test.ble.peripheral.jsonoperate;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.test.ble.peripheral.JsonOperation;

import android.util.Log;

public class Activate extends JsonOperation{
	String TAG = "BleStation";
	public JSONObject jsonOperation(JSONObject jsonReq){
		Log.e(TAG, " jsonOperation Activate");
	    JSONObject jsonRsp = new JSONObject(); 
	    
	    try {
            JSONObject jsonActivateData = jsonReq.getJSONObject("ActivationData");
            String strClientIdFromE3phone = jsonReq.getString("client");

            String strApi = jsonActivateData.getString("api");
            String strPassword = jsonActivateData.getString("password");
            String strGroup = jsonActivateData.getString("group");
            jsonRsp.put("method", "Activate");
            jsonRsp.put("result", 200);
            jsonRsp.put("reason", "OK");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRsp;
	}
}

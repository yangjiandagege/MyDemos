package com.example.test.ble.peripheral;

import com.example.test.ble.peripheral.jsonoperate.Activate;

import android.content.Context;

public class JsonOperationFactory {

	public static JsonOperation createJsonOperation(String method,Context context){
		JsonOperation jsonOper = null;
		
		if(method.equals("Activate")){
			jsonOper = new Activate(); 
		}
		
		return jsonOper;
	}
}

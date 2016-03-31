package com.example.test;

public class Result {
	static public String mResult;
	
	static public String append(String result){
		if(mResult == null){
			mResult = result+'\n';
		}else{
			mResult += result+'\n';
		}
		return mResult;
	}
	
	static public void clean(){
		mResult = "";
	}
}

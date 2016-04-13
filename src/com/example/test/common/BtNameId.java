package com.example.test.common;

public class BtNameId {
	String mName;
	int mId;
	int mColor;
	Operation mOperation;
	
	public BtNameId(String name, int id, int color, Operation operation){
		mName  = name;
		mId    = id;
		mColor = color;
		mOperation = operation;	
	}
	
	public String getName(){
		return mName;
	}
	
	public int getId(){
		return mId;
	}
	
	public int getColor(){
		return mColor;
	}
	
	public void operate(){
		if(mOperation != null){
			mOperation.operate();
		}
	}
}

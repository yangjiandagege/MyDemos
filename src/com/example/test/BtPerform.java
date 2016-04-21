package com.example.test;

import android.content.Context;
import android.content.Intent;

public class BtPerform {
	private String mName;
	private int mId;
	private int mColor;
	private Operation mOperation;
	private Context mCurrentActivity;
	private Class<?> mTargetActivity;
	
	public BtPerform(String name, int id, int color, Operation operation){
		mName  = name;
		mId    = id;
		mColor = color;
		mOperation = operation;	
		mCurrentActivity = null;
		mTargetActivity = null;
	}
	
	public BtPerform(String name, int id, int color, Context current, Class<?> target){
		mName  = name;
		mId    = id;
		mColor = color;
		mOperation = null;	
		mCurrentActivity = current;
		mTargetActivity = target;
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
	
	
	public void perform(){
		if(mTargetActivity != null){
			Intent intent = new Intent();
			intent.setClass(mCurrentActivity.getApplicationContext(), mTargetActivity);
			mCurrentActivity.startActivity(intent);
		}else if(mOperation != null) {
			mOperation.operate();
		}
	}
}

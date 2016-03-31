package com.example.test;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 519067123721295773L;
	public int mUserId;
	public String mUserName;
	public boolean mIsMale;

	public User(int userId, String userName, boolean isMale){
		mUserId = userId;
		mUserName = userName;
		mIsMale = isMale;
	}
	
	public String toString(){
		return "mUserId : "+mUserId+" | mUserName : "+mUserName + " | isMale : "+mIsMale;
	}
}

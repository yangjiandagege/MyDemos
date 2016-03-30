package com.example.test;

import android.app.Application;

public class TestApplication extends Application{
    private static final String DEFAULT_VALUE = "Hi , I'm yangjian";
    private String value;

    
    @Override
    public void onCreate(){
        super.onCreate();
        setValue(DEFAULT_VALUE);
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}

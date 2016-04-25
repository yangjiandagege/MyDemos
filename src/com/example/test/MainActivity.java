package com.example.test;

import android.graphics.Color;

public class MainActivity extends BaseActivity{
	public BtPerform[] initButtons(){
		BtPerform[] btPerform = new BtPerform[BT_NUM];
		int i = -1;
		btPerform[++i] = new BtPerform("Common test", i, Color.CYAN, MainActivity.this, com.example.test.common.MainActivity.class);
		btPerform[++i] = new BtPerform("AIDL", i, Color.CYAN, MainActivity.this, com.example.test.aidl.ClientActivity.class);
		btPerform[++i] = new BtPerform("Binder pool", i, Color.CYAN, MainActivity.this, com.example.test.binderpool.BinderPoolActivity.class);
		btPerform[++i] = new BtPerform("Bitmap", i, Color.CYAN, MainActivity.this, com.example.test.bitmap.MainActivity.class);
		btPerform[++i] = new BtPerform("BLE central", i, Color.CYAN, MainActivity.this, com.example.test.ble.central.CentralActivity.class);
		btPerform[++i] = new BtPerform("BLE peripheral", i, Color.CYAN, MainActivity.this, com.example.test.ble.peripheral.PeripheralActivity.class);
		btPerform[++i] = new BtPerform("ViewPager demo", i, Color.CYAN, MainActivity.this, com.example.test.viewpager.MainActivity.class);
		btPerform[++i] = new BtPerform("Content observer", i, Color.CYAN, MainActivity.this, com.example.test.contentobserver.MainActivity.class);
		btPerform[++i] = new BtPerform("Content provider", i, Color.CYAN, MainActivity.this, com.example.test.contentprovider.TeacherActivity.class);
		btPerform[++i] = new BtPerform("Location", i, Color.CYAN, MainActivity.this, com.example.test.location.LocationActivity.class);
		btPerform[++i] = new BtPerform("Touch test", i, Color.CYAN, MainActivity.this, com.example.test.touch.MainActivity.class);
		btPerform[++i] = new BtPerform("Sensors demo", i, Color.CYAN, MainActivity.this, com.example.test.sensors.ListViewActivity.class);
		btPerform[++i] = new BtPerform("QQ login", i, Color.CYAN, MainActivity.this, com.example.test.qqauth.MainActivity.class);
		btPerform[++i] = new BtPerform("Loop advertisement", i, Color.CYAN, MainActivity.this, com.example.test.loopad.LoopAdActivity.class);
		btPerform[++i] = new BtPerform("Asynctask", i, Color.CYAN, MainActivity.this, com.example.test.asynctask.AsyncTaskActivity.class);
		btPerform[++i] = new BtPerform("DIY ViewGroup : My ViewPager", i, Color.CYAN, MainActivity.this, com.example.test.diyviews.slidingconflict.MainActivity.class);
		btPerform[++i] = new BtPerform("DIY ViewGroup : SlideMenu", i, Color.CYAN, MainActivity.this, com.example.test.diyviews.slidemenu.SlideMenuActivity.class);
		btPerform[++i] = new BtPerform("Tab layout", i, Color.CYAN, MainActivity.this, com.example.test.tablayout.MainActivity.class);
		btPerform[++i] = new BtPerform("end", i, Color.BLACK, null);
		return btPerform;
	}
}
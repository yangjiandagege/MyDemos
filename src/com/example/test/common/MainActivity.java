package com.example.test.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test.R;
import com.example.test.Util;
import com.example.test.utils.*;

public class MainActivity extends Activity  implements OnClickListener{
	
	private static final int GET_APP_NAME  = 1000;
	private static final int GET_APP_VERSION_NAME  = GET_APP_NAME + 1;
	private static final int SERIALLIZABLE  = GET_APP_NAME + 2;
	private static final int UNSERIALLIZABLE  = GET_APP_NAME + 3;
	private static final int DIALOG_1  = GET_APP_NAME + 4;
	private static final int DIALOG_2  = GET_APP_NAME + 5;
	private static final int DIALOG_3  = GET_APP_NAME + 6;
	private static final int DIALOG_4  = GET_APP_NAME + 7;
	private static final int DIALOG_5  = GET_APP_NAME + 8;
	private static final int DIALOG_6  = GET_APP_NAME + 9;
	private static final int XML_TO_PRODUCT  = GET_APP_NAME + 10;
	private static final int SYSTEM_UI_FLAG_VISIBLE  = GET_APP_NAME + 11;
	private static final int INVISIBLE  = GET_APP_NAME + 12;
	private static final int SYSTEM_UI_FLAG_FULLSCREEN  = GET_APP_NAME + 13;
	private static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  = GET_APP_NAME + 14;
	private static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  = GET_APP_NAME + 15;
	private static final int SYSTEM_UI_LAYOUT_FLAGS  = GET_APP_NAME + 16;
	private static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION  = GET_APP_NAME + 17;
	private static final int SYSTEM_UI_FLAG_LOW_PROFILE  = GET_APP_NAME + 18;
	private static final int CALL_JNI  = GET_APP_NAME + 19;	
	private static final int SCREEN_INFO  = GET_APP_NAME + 20;
	private static final int DATABASE_INSERT = GET_APP_NAME + 21;
	private static final int DATABASE_UPDATE = GET_APP_NAME + 22;
	private static final int DATABASE_SHOW = GET_APP_NAME + 23;
	private static final int SHARED_PREFERENCES_WRITE = GET_APP_NAME + 24;
	private static final int SHARED_PREFERENCES_READ = GET_APP_NAME + 25;
	private View mRLayout;  
	
    public native String  stringFromJNI();
    
    static {
        System.loadLibrary("TestNDK");
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		final ScrollView myScrollView = new ScrollView(this);
		final LinearLayout myLayout = new LinearLayout(this);  
		
		myLayout.setOrientation(LinearLayout.VERTICAL);
		myScrollView.addView(myLayout);
		addButton(myLayout, GET_APP_NAME, "getAppName", Color.RED);
		addButton(myLayout, GET_APP_VERSION_NAME, "getAppVersionName", Color.RED);
		addButton(myLayout, SERIALLIZABLE, "serializable", Color.BLUE);
		addButton(myLayout, UNSERIALLIZABLE, "unserializable", Color.BLUE);
		addButton(myLayout, DIALOG_1, "dialog1", Color.GREEN);
		addButton(myLayout, DIALOG_2, "dialog2", Color.GREEN);
		addButton(myLayout, DIALOG_3, "dialog3", Color.GREEN);
		addButton(myLayout, DIALOG_4, "dialog4", Color.GREEN);
		addButton(myLayout, DIALOG_5, "dialog5", Color.GREEN);
		addButton(myLayout, DIALOG_6, "dialog6", Color.GREEN);
		addButton(myLayout, XML_TO_PRODUCT, "xml to product", Color.YELLOW);
		addButton(myLayout, SYSTEM_UI_FLAG_VISIBLE, "SYSTEM_UI_FLAG_VISIBLE", Color.CYAN);
		addButton(myLayout, INVISIBLE, "INVISIBLE",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_FLAG_FULLSCREEN, "SYSTEM_UI_FLAG_FULLSCREEN",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_LAYOUT_FLAGS, "SYSTEM_UI_LAYOUT_FLAGS",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_FLAG_HIDE_NAVIGATION, "SYSTEM_UI_FLAG_HIDE_NAVIGATION",  Color.CYAN);
		addButton(myLayout, SYSTEM_UI_FLAG_LOW_PROFILE, "SYSTEM_UI_FLAG_LOW_PROFILE",  Color.CYAN);
		addButton(myLayout, CALL_JNI, "call jni",  Color.RED);
		addButton(myLayout, SCREEN_INFO, "screen info", Color.BLUE);
		addButton(myLayout, DATABASE_INSERT, "database insert", Color.GREEN);
		addButton(myLayout, DATABASE_UPDATE, "database update", Color.GREEN);
		addButton(myLayout, DATABASE_SHOW, "database show", Color.GREEN);
		addButton(myLayout, SHARED_PREFERENCES_WRITE, "sharedPreferences write", Color.GRAY);
		addButton(myLayout, SHARED_PREFERENCES_READ, "sharedPreferences read", Color.GRAY);
		mRLayout = getWindow().getDecorView();
		setContentView(myScrollView);
		initDb();
	}
	
	private void initDb(){
		InputStream is = getResources().openRawResource(R.raw.apk_test);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("/sdcard/apk_test.db");
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) >= 0)
			{
				fos.write(buffer, 0, count);
			}

			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addButton(LinearLayout layout, int id, String msg, int color){
		Button btn = new Button(this); 
		btn.setText(msg);
		btn.setId(id);
		btn.setBackgroundColor(color);
		//btn.setTextColor(color);
		layout.addView(btn);
		btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId())
		{
			case GET_APP_NAME:
				getAppName();
				break;
			case GET_APP_VERSION_NAME:
				getAppVersionName();
				break;
			case SERIALLIZABLE:
				serializable();
				break;
			case UNSERIALLIZABLE:
				unserializable();
				break;
			case DIALOG_1:
				dialog_1();
				break;
			case DIALOG_2:
				dialog_2();
				break;
			case DIALOG_3:
				dialog_3();
				break;
			case DIALOG_4:
				dialog_4();
				break;
			case DIALOG_5:
				dialog_5();
				break;
			case DIALOG_6:
				dialog_6();
				break;
			case XML_TO_PRODUCT:
				xmlToObject();
				break;
			case SYSTEM_UI_FLAG_VISIBLE:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);      
				break;
			case INVISIBLE:
				mRLayout.setSystemUiVisibility(View.INVISIBLE);  
				break;
			case SYSTEM_UI_FLAG_FULLSCREEN:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);  
				break;
			case SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);  
				break;
			case SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);  
				break;
			case SYSTEM_UI_LAYOUT_FLAGS:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);  
				break;
			case SYSTEM_UI_FLAG_HIDE_NAVIGATION:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);  
				break;
			case SYSTEM_UI_FLAG_LOW_PROFILE:
				mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);  
				break;
			case CALL_JNI:
				String result = stringFromJNI();
				Util.showResultDialog(this, result, "Result :");
				break;
			case SCREEN_INFO:
				showScreenInfo();
				break;
			case DATABASE_INSERT:
				insertDatabase();
				break;
			case DATABASE_UPDATE:
				updateDatabase();
				break;
			case DATABASE_SHOW:
				readDatabase();
				break;
			case SHARED_PREFERENCES_WRITE:
				sharedPreferencesWrite();
				break;
			case SHARED_PREFERENCES_READ:
				sharedPreferencesRead();
				break;
		}
	}
	
	public void sharedPreferencesWrite() {		
		SharedPreferences mySharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("name", "yangjian");
		editor.putString("habit", "basketball");
		editor.commit();
		Util.showResultDialog(this, "write ok", "sharedPreferencesWriteData");
	}

	public void sharedPreferencesRead() {
		SharedPreferences sharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("name", "");
		String habit = sharedPreferences.getString("habit", "");
		Util.showResultDialog(this, "name is " + name + "\n" + "habit is " + habit, "sharedPreferencesReadData");
	}
	
	public void readDatabase() {
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		Cursor cursor = sqLiteDatabase.rawQuery("select * from t_test",null);
		String result = "";
		while (cursor.moveToNext()){
			result = result + cursor.getString(1) + '\n';
		}
		Util.showResultDialog(this,result , "readDatabase :");
		cursor.close();
		sqLiteDatabase.close();
	}
	
	public void insertDatabase() {
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		String INSERT_DATA1 = "insert into t_test(id,name,memo) values('3','yangjian','Student')";
		sqLiteDatabase.execSQL(INSERT_DATA1);
		String INSERT_DATA2 = "insert into t_test(id,name,memo) values('4','xiyuan','Student')";
		sqLiteDatabase.execSQL(INSERT_DATA2);
		Util.showResultDialog(this,INSERT_DATA1+","+INSERT_DATA2 , "insertDatabase :");
		sqLiteDatabase.close();
	}
	
	public void updateDatabase() {
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
		String UPDATE_DATA1="update t_test set name='yangmiemie' where name='yangjian'";
		sqLiteDatabase.execSQL(UPDATE_DATA1);
		String UPDATE_DATA2="update t_test set name='ximiaomiao' where name='xiyuan'";
		sqLiteDatabase.execSQL(UPDATE_DATA2);
		Util.showResultDialog(this,UPDATE_DATA1+","+UPDATE_DATA2 , "updateDatabase :");
		sqLiteDatabase.close();
	}
	
	public void showScreenInfo(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		float density = dm.density;
		int densityDpi = dm.densityDpi;
		int widthPixels=dm.widthPixels;  
	    int heightPixels=dm.heightPixels; 
	    float xdpi = dm.xdpi;
	    float ydpi = dm.ydpi;
	    
		String info = "density : "+density+"\n";
		info += "densityDpi : "+densityDpi+"\n";
		info += "widthPixels : "+widthPixels+"\n";
		info += "heightPixels : "+heightPixels+"\n";
		info += "xdpi : "+xdpi+"\n";
		info += "ydpi : "+ydpi+"\n";
//		屏幕尺寸(英寸)
//		屏幕分辨率(像素px)
//		屏幕像素密度(dpi)
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Screen info"); 
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage(info); 
        builder.setNeutralButton("忽略", null);  
        builder.create().show();
	}
	
    void getAppName(){
    	Util.showResultDialog(this, AppUtils.getAppName(this), "App name :");
    }
    
    void getAppVersionName(){
    	Util.showResultDialog(this, AppUtils.getVersionName(this), "App version name :");
    }
    
    void serializable(){
		try {
        	User user = new User(0, "yangjian", true);
        	ObjectOutputStream out;
			out = new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/data.txt"));
        	out.writeObject(user);
        	out.close();
			Util.showResultDialog(this,user.toString() , "Serializable info :");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    void unserializable(){
		try {
			ObjectInputStream in;
			in = new ObjectInputStream(new FileInputStream(getFilesDir()+"/data.txt"));
        	User newUser = (User)in.readObject();
        	in.close();
        	Util.showResultDialog(this,newUser.toString() , "Unserializable info :");
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    private void dialog_1(){  
        //先new出一个监听器，设置好监听  
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){  
   
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                switch(which){  
                    case Dialog.BUTTON_POSITIVE:  
                        Toast.makeText(MainActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();  
                        break;  
                    case Dialog.BUTTON_NEGATIVE:  
                        Toast.makeText(MainActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();  
                        break;  
                    case Dialog.BUTTON_NEUTRAL:  
                        Toast.makeText(MainActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();  
                        break;  
                }  
            }  
        };  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage("是否确认退出?"); //设置内容  
        builder.setPositiveButton("确认",dialogOnclicListener);  
        builder.setNegativeButton("取消", dialogOnclicListener);  
        builder.setNeutralButton("忽略", dialogOnclicListener);  
        builder.create().show();
    }  
    
    
    private void dialog_2() {  
        final String items[]={"张三","李四","王五"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("提示"); //设置标题  
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();  
  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.create().show();  
    }  
    
    private void dialog_3(){  
        final String items[]={"男","女"};  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setIcon(R.drawable.ic_launcher);//设置图标，图片id即可  
        builder.setSingleChoiceItems(items,0,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                //dialog.dismiss();  
                Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.create().show();  
    }  
    
    private void dialog_4(){  
        final String items[]={"篮球","足球","排球"};  
        final boolean selected[]={true,false,true};  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setIcon(R.drawable.ic_launcher);//设置图标，图片id即可  
        builder.setMultiChoiceItems(items,selected,new DialogInterface.OnMultiChoiceClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {  
               // dialog.dismiss();  
                Toast.makeText(MainActivity.this, items[which]+isChecked, Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();  
                //android会自动根据你选择的改变selected数组的值。  
                for (int i=0;i<selected.length;i++){  
                    Log.e("yangjian","selected = "+selected[i]);  
                }  
            }  
        });  
        builder.create().show();  
    } 
    
    private void dialog_5(){  
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	final EditText editText = new EditText(this);
    	builder.setTitle("请输入");
    	builder.setIcon(R.drawable.ic_launcher);
    	builder.setView(editText);
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	Toast.makeText(MainActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();  
            }
    	});
    	builder.setNegativeButton("取消", null);
    	builder.create().show();  
    }
    
    private void dialog_6(){  
    	LayoutInflater inflater = getLayoutInflater();
    	View layout = inflater.inflate(R.xml.dialog,(ViewGroup) findViewById(R.id.dialog));
    	final EditText etName = (EditText)layout.findViewById(R.id.etname);
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setTitle("自定义布局");
    	builder.setIcon(R.drawable.ic_launcher);
    	builder.setView(layout);
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	Toast.makeText(MainActivity.this, etName.getText(), Toast.LENGTH_SHORT).show();  
            }
    	});
    	builder.setNegativeButton("取消", null);
    	builder.create().show();  
    }
    
	public void xmlToObject()
	{
		try {
			InputStream is = getResources().openRawResource(R.raw.products);
			XML2Product xml2Product = new XML2Product();
			android.util.Xml.parse(is, Xml.Encoding.UTF_8, xml2Product);

			List<Product> products = xml2Product.getProducts();
			String msg = "The number of products is " + products.size() + "\n";
			for (Product product : products) {
				msg += "id : " + product.getId() + "  name : " + product.getName()
						+ "  price : " + product.getPrice() + "\n";
			}
			new AlertDialog.Builder(this).setTitle("Info").setMessage(msg)
					.setPositiveButton("ok", null).show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

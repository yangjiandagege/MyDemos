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
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.example.test.R;
import com.example.test.Util;
import com.example.test.utils.*;

public class MainActivity extends Activity  implements OnClickListener{
	BtNameId mBtNameId[] = new BtNameId[50];
	private View mRLayout;  
	
    public native String  stringFromJNI();
    
    static {
        System.loadLibrary("TestNDK");
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		initUi(MainActivity.this);
		initDb();
		mRLayout = getWindow().getDecorView();
	}
	
	private void initUi(Context context){
		final ScrollView myScrollView = new ScrollView(context);
		final LinearLayout myLayout = new LinearLayout(context);  
		myLayout.setOrientation(LinearLayout.VERTICAL);
		myScrollView.addView(myLayout);
		int i = -1;
		mBtNameId[++i] = new BtNameId("getAppName", i, Color.GRAY, new getAppName());
		mBtNameId[++i] = new BtNameId("getAppVersionName", i, Color.GRAY, new getAppVersionName());
		mBtNameId[++i] = new BtNameId("serializable", i, Color.CYAN, new serializable());
		mBtNameId[++i] = new BtNameId("unserializable", i, Color.CYAN, new unserializable());
		mBtNameId[++i] = new BtNameId("dialog1", i, Color.GREEN, new dialog_1());
		mBtNameId[++i] = new BtNameId("dialog2", i, Color.GREEN, new dialog_2());
		mBtNameId[++i] = new BtNameId("dialog3", i, Color.GREEN, new dialog_3());
		mBtNameId[++i] = new BtNameId("dialog4", i, Color.GREEN, new dialog_4());
		mBtNameId[++i] = new BtNameId("dialog5", i, Color.GREEN, new dialog_5());
		mBtNameId[++i] = new BtNameId("dialog6", i, Color.GREEN, new dialog_6());
		mBtNameId[++i] = new BtNameId("xml to object", i, Color.YELLOW, new xmlToObject());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_VISIBLE", i, Color.CYAN, new systemUiFlagVisible());
		mBtNameId[++i] = new BtNameId("INVISIBLE", i, Color.CYAN, new invisible());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_FULLSCREEN", i, Color.CYAN, new systemUiFlagFullScreen());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", i, Color.CYAN, new systemUiFlagLayoutFullScreen());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", i, Color.CYAN, new systemUiFlagLayoutHideNavigation());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_LAYOUT_FLAGS", i, Color.CYAN, new systemUiLayoutFlags());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_HIDE_NAVIGATION", i, Color.CYAN, new systemUiFlagVisible());
		mBtNameId[++i] = new BtNameId("SYSTEM_UI_FLAG_LOW_PROFILE", i, Color.CYAN, new systemUiFlagLowProfile());
		mBtNameId[++i] = new BtNameId("call jni", i, Color.RED, new callJni());
		mBtNameId[++i] = new BtNameId("screen info", i, Color.GRAY, new showScreenInfo());
		mBtNameId[++i] = new BtNameId("database insert", i, Color.GREEN, new insertDatabase());
		mBtNameId[++i] = new BtNameId("database update", i, Color.GREEN, new updateDatabase());
		mBtNameId[++i] = new BtNameId("database show", i, Color.GREEN, new readDatabase());
		mBtNameId[++i] = new BtNameId("sharedPreferences write", i, Color.GRAY, new sharedPreferencesWrite());
		mBtNameId[++i] = new BtNameId("sharedPreferences read", i, Color.GRAY, new sharedPreferencesRead());
		mBtNameId[++i] = new BtNameId("end", i, Color.BLACK, null);
		for(int j = 0; mBtNameId != null ; j++){
			addButton(myLayout,mBtNameId[j].getId(),mBtNameId[j].getName(),mBtNameId[j].getColor());
			if(mBtNameId[j].getName().equalsIgnoreCase("end")){
				break;
			}
		}
		setContentView(myScrollView);
	}
	
	public class systemUiFlagVisible implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}
	}
	
	public class invisible implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.INVISIBLE);
		}
	}
	
	public class systemUiFlagFullScreen implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
	}
	
	public class systemUiFlagLayoutFullScreen implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
	}
	
	public class systemUiFlagLayoutHideNavigation implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
	}
	
	public class systemUiLayoutFlags implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
		}
	}
	
	public class systemUiFlagLowProfile implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
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
		layout.addView(btn);
		btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		mBtNameId[view.getId()].operate();
	}
	
	public class callJni implements Operation{
		@Override
		public void operate() {
			String result = stringFromJNI();
			Util.showResultDialog(MainActivity.this, result, "Result :");
		}
	}
	
	public class sharedPreferencesWrite implements Operation{
		@Override
		public void operate() {
			SharedPreferences mySharedPreferences = getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("name", "yangjian");
			editor.putString("habit", "basketball");
			editor.commit();
			Util.showResultDialog(MainActivity.this, "write ok", "sharedPreferencesWriteData");
		}
	}

	public class sharedPreferencesRead implements Operation{
		@Override
		public void operate() {
			SharedPreferences sharedPreferences = getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			String name = sharedPreferences.getString("name", "");
			String habit = sharedPreferences.getString("habit", "");
			Util.showResultDialog(MainActivity.this, "name is " + name + "\n" + "habit is " + habit, "sharedPreferencesReadData");
		}
	}
	
	public class readDatabase implements Operation{
		@Override
		public void operate() {
			SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
			Cursor cursor = sqLiteDatabase.rawQuery("select * from t_test",null);
			String result = "";
			while (cursor.moveToNext()){
				result = result + cursor.getString(1) + '\n';
			}
			Util.showResultDialog(MainActivity.this,result , "readDatabase :");
			cursor.close();
			sqLiteDatabase.close();
		}
	}
	
	public class insertDatabase implements Operation{
		@Override
		public void operate() {
			SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
			String INSERT_DATA1 = "insert into t_test(id,name,memo) values('3','yangjian','Student')";
			sqLiteDatabase.execSQL(INSERT_DATA1);
			String INSERT_DATA2 = "insert into t_test(id,name,memo) values('4','xiyuan','Student')";
			sqLiteDatabase.execSQL(INSERT_DATA2);
			Util.showResultDialog(MainActivity.this,INSERT_DATA1+","+INSERT_DATA2 , "insertDatabase :");
			sqLiteDatabase.close();
		}
	}
	
	
	public class updateDatabase implements Operation{
		@Override
		public void operate() {
			SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/sdcard/apk_test.db", null);
			String UPDATE_DATA1="update t_test set name='yangmiemie' where name='yangjian'";
			sqLiteDatabase.execSQL(UPDATE_DATA1);
			String UPDATE_DATA2="update t_test set name='ximiaomiao' where name='xiyuan'";
			sqLiteDatabase.execSQL(UPDATE_DATA2);
			Util.showResultDialog(MainActivity.this,UPDATE_DATA1+","+UPDATE_DATA2 , "updateDatabase :");
			sqLiteDatabase.close();
		}
	}
	
	public class showScreenInfo implements Operation{
		@Override
		public void operate() {
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
//			屏幕尺寸(英寸)
//			屏幕分辨率(像素px)
//			屏幕像素密度(dpi)
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
	        builder.setTitle("Screen info"); 
	        builder.setIcon(R.drawable.ic_launcher);
	        builder.setMessage(info); 
	        builder.setNeutralButton("忽略", null);  
	        builder.create().show();
		}
	}
	
	public class getAppName implements Operation{
		@Override
		public void operate() {
			Util.showResultDialog(MainActivity.this, AppUtils.getAppName(MainActivity.this), "App name :");
		}
	}
	
	public class getAppVersionName implements Operation{
		@Override
		public void operate() {
			Util.showResultDialog(MainActivity.this, AppUtils.getVersionName(MainActivity.this), "App version name : ");
		}
	}
    
	public class serializable implements Operation{
		@Override
		public void operate() {
			try {
	        	User user = new User(0, "yangjian", true);
	        	ObjectOutputStream out;
				out = new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/data.txt"));
	        	out.writeObject(user);
	        	out.close();
				Util.showResultDialog(MainActivity.this,user.toString() , "Serializable:");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	public class unserializable implements Operation{
		@Override
		public void operate() {
			try {
				ObjectInputStream in;
				in = new ObjectInputStream(new FileInputStream(getFilesDir()+"/data.txt"));
	        	User newUser = (User)in.readObject();
	        	in.close();
	        	Util.showResultDialog(MainActivity.this,newUser.toString() , "Unserializable info :");
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
	}
	
	public class dialog_1 implements Operation{
		@Override
		public void operate() {
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
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);  //先得到构造器  
	        builder.setTitle("提示"); //设置标题  
	        builder.setIcon(R.drawable.ic_launcher);
	        builder.setMessage("是否确认退出?"); //设置内容  
	        builder.setPositiveButton("确认",dialogOnclicListener);  
	        builder.setNegativeButton("取消", dialogOnclicListener);  
	        builder.setNeutralButton("忽略", dialogOnclicListener);  
	        builder.create().show();
		}
	}
    
	public class dialog_2 implements Operation{
		@Override
		public void operate() {
	        final String items[]={"张三","李四","王五"};  
	        //dialog参数设置  
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);  //先得到构造器  
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
	}
	
	public class dialog_3 implements Operation{
		@Override
		public void operate() {
	        final String items[]={"男","女"};  
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);  //先得到构造器  
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
	}
    
	public class dialog_4 implements Operation{
		@Override
		public void operate() {
	        final String items[]={"篮球","足球","排球"};  
	        final boolean selected[]={true,false,true};  
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);  //先得到构造器  
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
	}
	
    
	public class dialog_5 implements Operation{
		@Override
		public void operate() {
	    	AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
	    	final EditText editText = new EditText(MainActivity.this);
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
	}
    
	public class dialog_6 implements Operation{
		@Override
		public void operate() {
	    	LayoutInflater inflater = getLayoutInflater();
	    	View layout = inflater.inflate(R.xml.dialog,(ViewGroup) findViewById(R.id.dialog));
	    	final EditText etName = (EditText)layout.findViewById(R.id.etname);
	    	AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
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
	}
    
	public class xmlToObject implements Operation{
		@Override
		public void operate() {
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
				new AlertDialog.Builder(MainActivity.this).setTitle("Info").setMessage(msg)
						.setPositiveButton("ok", null).show();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}

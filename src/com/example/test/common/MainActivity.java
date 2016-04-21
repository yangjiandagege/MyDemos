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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.test.BaseActivity;
import com.example.test.BtPerform;
import com.example.test.Operation;
import com.example.test.R;
import com.example.test.Util;
import com.example.test.utils.*;

public class MainActivity extends BaseActivity{
	BtPerform mBtPerform[] = new BtPerform[50];
    private NotificationManager nm;
	private View mRLayout;  
	private Context mContext;
    private int Notification_ID_BASE = 110;
    private Notification baseNF;
    private int Notification_ID_MEDIA = 119;
    private Notification mediaNF;
    private PendingIntent pd;
    
    public native String  stringFromJNI();
    
    static {
        System.loadLibrary("TestNDK");
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mContext = MainActivity.this;
		initDb();
		mRLayout = getWindow().getDecorView();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,MainActivity.class);
        pd = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
	}
	
	public BtPerform[] initButtons(){
		BtPerform[] btPerform = new BtPerform[BT_NUM];
		int i = -1;
		btPerform[++i] = new BtPerform("getAppName", i, Color.GRAY, new getAppName());
		btPerform[++i] = new BtPerform("getAppVersionName", i, Color.GRAY, new getAppVersionName());
		btPerform[++i] = new BtPerform("serializable", i, Color.CYAN, new serializable());
		btPerform[++i] = new BtPerform("unserializable", i, Color.CYAN, new unserializable());
		btPerform[++i] = new BtPerform("dialog1", i, Color.GREEN, new dialog_1());
		btPerform[++i] = new BtPerform("dialog2", i, Color.GREEN, new dialog_2());
		btPerform[++i] = new BtPerform("dialog3", i, Color.GREEN, new dialog_3());
		btPerform[++i] = new BtPerform("dialog4", i, Color.GREEN, new dialog_4());
		btPerform[++i] = new BtPerform("dialog5", i, Color.GREEN, new dialog_5());
		btPerform[++i] = new BtPerform("dialog6", i, Color.GREEN, new dialog_6());
		btPerform[++i] = new BtPerform("xml to object", i, Color.YELLOW, new xmlToObject());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_VISIBLE", i, Color.CYAN, new systemUiFlagVisible());
		btPerform[++i] = new BtPerform("INVISIBLE", i, Color.CYAN, new invisible());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_FULLSCREEN", i, Color.CYAN, new systemUiFlagFullScreen());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", i, Color.CYAN, new systemUiFlagLayoutFullScreen());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", i, Color.CYAN, new systemUiFlagLayoutHideNavigation());
		btPerform[++i] = new BtPerform("SYSTEM_UI_LAYOUT_FLAGS", i, Color.CYAN, new systemUiLayoutFlags());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_HIDE_NAVIGATION", i, Color.CYAN, new systemUiFlagVisible());
		btPerform[++i] = new BtPerform("SYSTEM_UI_FLAG_LOW_PROFILE", i, Color.CYAN, new systemUiFlagLowProfile());
		btPerform[++i] = new BtPerform("call jni", i, Color.RED, new callJni());
		btPerform[++i] = new BtPerform("screen info", i, Color.GRAY, new showScreenInfo());
		btPerform[++i] = new BtPerform("database insert", i, Color.GREEN, new insertDatabase());
		btPerform[++i] = new BtPerform("database update", i, Color.GREEN, new updateDatabase());
		btPerform[++i] = new BtPerform("database show", i, Color.GREEN, new readDatabase());
		btPerform[++i] = new BtPerform("sharedPreferences write", i, Color.GRAY, new sharedPreferencesWrite());
		btPerform[++i] = new BtPerform("sharedPreferences read", i, Color.GRAY, new sharedPreferencesRead());
		btPerform[++i] = new BtPerform("BaseNotification", i, Color.DKGRAY, new BaseNotification());
		btPerform[++i] = new BtPerform("UpdateBaseNotification", i, Color.DKGRAY, new UpdateBaseNotification());
		btPerform[++i] = new BtPerform("ClearBaseNotification", i, Color.DKGRAY, new ClearBaseNotification());
		btPerform[++i] = new BtPerform("MediaNotification", i, Color.DKGRAY, new MediaNotification());
		btPerform[++i] = new BtPerform("ClearMediaNotification", i, Color.DKGRAY, new ClearMediaNotification());
		btPerform[++i] = new BtPerform("CustomNotification", i, Color.DKGRAY, new CustomNotification());
		btPerform[++i] = new BtPerform("ClearAll", i, Color.DKGRAY, new ClearAllNotification());
		btPerform[++i] = new BtPerform("end", i, Color.BLACK, null);
		return btPerform;
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
	
	public class BaseNotification implements Operation{
		@Override
		public void operate() {
            //新建状态栏通知
            baseNF = new Notification();
            //设置通知在状态栏显示的图标
            baseNF.icon = R.drawable.icon;
            //通知时在状态栏显示的内容
            baseNF.tickerText = "You clicked BaseNF!";
            //通知的默认参数 DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
            //如果要全部采用默认值, 用 DEFAULT_ALL.
            //此处采用默认声音
            baseNF.defaults |= Notification.DEFAULT_SOUND;
            baseNF.defaults |= Notification.DEFAULT_VIBRATE;
            baseNF.defaults |= Notification.DEFAULT_LIGHTS;
            //让声音、振动无限循环，直到用户响应
            baseNF.flags |= Notification.FLAG_INSISTENT;
            //通知被点击后，自动消失
            baseNF.flags |= Notification.FLAG_AUTO_CANCEL;
            //点击'Clear'时，不清楚该通知(QQ的通知无法清除，就是用的这个)
            baseNF.flags |= Notification.FLAG_NO_CLEAR;

            //第二个参数 ：下拉状态栏时显示的消息标题 expanded message title
            //第三个参数：下拉状态栏时显示的消息内容 expanded message text
            //第四个参数：点击该通知时执行页面跳转
            baseNF.setLatestEventInfo(MainActivity.this, "Title01", "Content01", pd);

            //发出状态栏通知
            //The first parameter is the unique ID for the Notification
            // and the second is the Notification object.
            nm.notify(Notification_ID_BASE, baseNF);
		}
	}
	
	public class UpdateBaseNotification implements Operation{
		@Override
		public void operate() {
            //更新通知
            //比如状态栏提示有一条新短信，还没来得及查看，又来一条新短信的提示。
            //此时采用更新原来通知的方式比较。
            //(再重新发一个通知也可以，但是这样会造成通知的混乱，而且显示多个通知给用户，对用户也不友好)
            baseNF.setLatestEventInfo(MainActivity.this, "Title02", "Content02", pd);
            nm.notify(Notification_ID_BASE, baseNF);
		}
	}
	
	public class ClearBaseNotification implements Operation{
		@Override
		public void operate() {
            //清除 baseNF
            nm.cancel(Notification_ID_BASE);
		}
	}
	
	public class MediaNotification implements Operation{
		@Override
		public void operate() {
            mediaNF = new Notification();
            mediaNF.icon = R.drawable.icon;
            mediaNF.tickerText = "You clicked MediaNF!";

            //自定义声音
            mediaNF.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
            //通知时发出的振动
            //第一个参数: 振动前等待的时间
            //第二个参数： 第一次振动的时长、以此类推
            long[] vir = {0,100,200,300};
            mediaNF.vibrate = vir;
            mediaNF.setLatestEventInfo(MainActivity.this, "Title03", "Content03", pd);
            nm.notify(Notification_ID_MEDIA, mediaNF);
		}
	}
	
	public class ClearMediaNotification implements Operation{
		@Override
		public void operate() {
            //清除 mediaNF
            nm.cancel(Notification_ID_MEDIA);
		}
	}
	
	public class systemUiFlagVisible implements Operation{
		@Override
		public void operate() {
			mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}
	}
	
	public class CustomNotification implements Operation{
		@Override
		public void operate() {
            //自定义下拉视图，比如下载软件时，显示的进度条。
            Notification notification = new Notification();
            notification.icon = R.drawable.icon;
            notification.tickerText = "Custom!";
            RemoteViews contentView = new RemoteViews(getPackageName(), R.xml.notification_custom);
            contentView.setImageViewResource(R.id.image, R.drawable.icon);
            contentView.setTextViewText(R.id.text, "Hello, this message is in a custom expanded view");
            notification.contentView = contentView;

            //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法
            //但是必须定义 contentIntent
            notification.contentIntent = pd;
            nm.notify(3, notification);
		}
	}
	
	public class ClearAllNotification implements Operation{
		@Override
		public void operate() {
            nm.cancelAll();
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

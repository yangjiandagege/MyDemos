package com.example.test.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		final LinearLayout myLayout = new LinearLayout(this);  
		myLayout.setOrientation(LinearLayout.VERTICAL);
		addButtion(myLayout, GET_APP_NAME, "getAppName");
		addButtion(myLayout, GET_APP_VERSION_NAME, "getAppVersionName");
		addButtion(myLayout, SERIALLIZABLE, "serializable");
		addButtion(myLayout, UNSERIALLIZABLE, "unserializable");
		addButtion(myLayout, DIALOG_1, "dialog1");
		addButtion(myLayout, DIALOG_2, "dialog2");
		addButtion(myLayout, DIALOG_3, "dialog3");
		addButtion(myLayout, DIALOG_4, "dialog4");
		addButtion(myLayout, DIALOG_5, "dialog5");
		addButtion(myLayout, DIALOG_6, "dialog6");
		addButtion(myLayout, XML_TO_PRODUCT, "xml to product");
		setContentView(myLayout);
	}
	
	private void addButtion(LinearLayout layout, int id, String msg){
		Button btn = new Button(this); 
		btn.setText(msg);
		btn.setId(id);
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
		}
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

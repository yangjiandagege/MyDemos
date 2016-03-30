package com.example.test.dialog;

import com.example.test.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DialogActivity extends PreferenceActivity{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_activity_dialog);
		PreferenceScreen prefDialog1 = (PreferenceScreen)findPreference("pref_dialog_1");
		prefDialog1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_1();
		    	return true;
		    }  
		} );
		
		PreferenceScreen prefDialog2 = (PreferenceScreen)findPreference("pref_dialog_2");
		prefDialog2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_2();
		    	return true;
		    }  
		} );
		
		PreferenceScreen prefDialog3 = (PreferenceScreen)findPreference("pref_dialog_3");
		prefDialog3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_3();
		    	return true;
		    }  
		} );
		
		PreferenceScreen prefDialog4 = (PreferenceScreen)findPreference("pref_dialog_4");
		prefDialog4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_4();
		    	return true;
		    }  
		} );
		
		PreferenceScreen prefDialog5 = (PreferenceScreen)findPreference("pref_dialog_5");
		prefDialog5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_5();
		    	return true;
		    }  
		} );
		
		PreferenceScreen prefDialog6 = (PreferenceScreen)findPreference("pref_dialog_6");
		prefDialog6.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {  
		    @Override  
		    public boolean onPreferenceClick(Preference preference) {
		    	dialog_6();
		    	return true;
		    }  
		} );
	}
	
    private void dialog_1(){  
        //先new出一个监听器，设置好监听  
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){  
   
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                switch(which){  
                    case Dialog.BUTTON_POSITIVE:  
                        Toast.makeText(DialogActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();  
                        break;  
                    case Dialog.BUTTON_NEGATIVE:  
                        Toast.makeText(DialogActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();  
                        break;  
                    case Dialog.BUTTON_NEUTRAL:  
                        Toast.makeText(DialogActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();  
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
                Toast.makeText(DialogActivity.this, items[which], Toast.LENGTH_SHORT).show();  
  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogActivity.this, "确定", Toast.LENGTH_SHORT).show();  
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
                Toast.makeText(DialogActivity.this, items[which], Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogActivity.this, "确定", Toast.LENGTH_SHORT).show();  
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
                Toast.makeText(DialogActivity.this, items[which]+isChecked, Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogActivity.this, "确定", Toast.LENGTH_SHORT).show();  
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
            	Toast.makeText(DialogActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();  
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
            	Toast.makeText(DialogActivity.this, etName.getText(), Toast.LENGTH_SHORT).show();  
            }
    	});
    	builder.setNegativeButton("取消", null);
    	builder.create().show();  
    }
}

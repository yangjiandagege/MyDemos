package com.example.test.customlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.test.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomListViewActivity extends ListActivity {
	private List<Map<String, Object>> mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);
		setListAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		map.put("info", "google 1");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "G4");
		map.put("info", "google 4");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G5");
		map.put("info", "google 5");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G6");
		map.put("info", "google 6");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "G7");
		map.put("info", "google 7");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G8");
		map.put("info", "google 8");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G9");
		map.put("info", "google 9");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "G10");
		map.put("info", "google 10");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G11");
		map.put("info", "google 11");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G12");
		map.put("info", "google 12");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "G13");
		map.put("info", "google 13");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G14");
		map.put("info", "google 14");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G15");
		map.put("info", "google 15");
		map.put("img", R.drawable.i3);
		list.add(map);
		return list;
	}
	
	// ListView 中某项被选中后的逻辑
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.v("MyListView4-click", (String)mData.get(position).get("title"));
	}
	
	/**
	 * listview中点击按键弹出对话框
	 */
	public void showInfo(){
		new AlertDialog.Builder(this)
		.setTitle("我的listview")
		.setMessage("介绍...")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();
	}
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public Button viewBtn;
	}
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();
				convertView = mInflater.inflate(R.xml.custom_listview, null);
				holder.img = (ImageView)convertView.findViewById(R.id.img);
				holder.title = (TextView)convertView.findViewById(R.id.title);
				holder.info = (TextView)convertView.findViewById(R.id.info);
				holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
			holder.title.setText((String)mData.get(position).get("title"));
			holder.info.setText((String)mData.get(position).get("info"));
			
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showInfo();					
				}
			});
			return convertView;
		}
	}
}

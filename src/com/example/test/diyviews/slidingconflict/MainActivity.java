package com.example.test.diyviews.slidingconflict;

import java.util.ArrayList;

import com.example.test.R;
import com.example.test.Util;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{
    private HorizontalScrollView mListContainer;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_slidingconflict);
        initView();
    }
    
    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollView) findViewById(R.id.container);
        final int screenWidth = Util.getScreenMetrics(this).widthPixels;
        final int screenHeight = Util.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.xml.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.xml.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(MainActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

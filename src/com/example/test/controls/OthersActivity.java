package com.example.test.controls;

import com.example.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class OthersActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controls_others);
		//>>>>>>>>>>>>>>>>AutoCompleteTextView>>>>>>>>>>>>>>>>>
		String[] autoString = new String[]{"yangjian","xiaoli", "bcd", "bcdf", "Google", "Google Map", "Google Android" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, autoString);
		// AutoCompleteTextView
		AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		autoCompleteTextView.setAdapter(adapter);
		// MultiAutoCompleteTextView
		MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
		multiAutoCompleteTextView.setAdapter(adapter);
		multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		
		//>>>>>>>>>>>>>>>>ChoiceListView>>>>>>>>>>>>>>>>>
		ListView lvCheckedTextView = (ListView) findViewById(R.id.lvCheckedTextView);
		ListView lvMutiCheckedTextView = (ListView) findViewById(R.id.lvMutiCheckedTextView);
		String[] data = new String[]{ "Android", "Meego" };

		ArrayAdapter<String> aaCheckedTextViewAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_checked, data);
		lvCheckedTextView.setAdapter(aaCheckedTextViewAdapter);
		lvCheckedTextView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		setListViewHeightBasedOnChildren(lvCheckedTextView);
		
		ArrayAdapter<String> aaCheckBoxAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, data);
		lvMutiCheckedTextView.setAdapter(aaCheckBoxAdapter);
		lvMutiCheckedTextView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		setListViewHeightBasedOnChildren(lvMutiCheckedTextView);

	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    if(listView == null) return;
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        return;
	    }
	    int totalHeight = 0;
	    
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += listItem.getMeasuredHeight();
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	}
}

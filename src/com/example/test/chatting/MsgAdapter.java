package com.example.test.chatting;

import java.util.List;

import com.example.test.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg>{
	private int resourceId;
	
	public MsgAdapter(Context context,int textViewResourceId,List<Msg> msglist){
		super(context, textViewResourceId,msglist);
		this.resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Msg msg = getItem(position);
		View view;
		ViewHolder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewholder = new ViewHolder();
			viewholder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewholder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewholder.tvLeftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewholder.tvRightMsg = (TextView) view.findViewById(R.id.right_msg);
			view.setTag(viewholder);
		}else {
			view = convertView;
			viewholder = (ViewHolder) view.getTag();
		}
		if (msg.getType() == msg.TYPE_RECEIVED) {
			viewholder.leftLayout.setVisibility(View.VISIBLE);
			viewholder.rightLayout.setVisibility(View.GONE);
			viewholder.tvLeftMsg.setText(msg.getContent());
		}else if (msg.getType() == msg.TYPE_SENT) {
			viewholder.rightLayout.setVisibility(View.VISIBLE);
			viewholder.leftLayout.setVisibility(View.GONE);
			viewholder.tvRightMsg.setText(msg.getContent());
		}
		return view;
	}
	
	class ViewHolder{
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		TextView tvLeftMsg;
		TextView tvRightMsg;
	}
}

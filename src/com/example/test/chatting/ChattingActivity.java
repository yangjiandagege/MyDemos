package com.example.test.chatting;

import java.util.ArrayList;
import java.util.List;
import com.example.test.R;
import com.example.test.ble.Msg;
import com.example.test.ble.MsgAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChattingActivity extends Activity {

	private ListView msgListView;
	private EditText msgEditText;
	private Button btnSend;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        initMsgs();
        adapter = new MsgAdapter(ChattingActivity.this, R.xml.msg_item, msgList);
        msgEditText = (EditText) findViewById(R.id.input_et);
        btnSend = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list);
        msgListView.setAdapter(adapter);
        btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = msgEditText.getText().toString();
				if (!"".equals(content)) {
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					msgEditText.setText("");
				}
			}
		});
    }

    private void initMsgs() {
    	Msg msg0 = new Msg("Hey man!", Msg.TYPE_SENT);
		msgList.add(msg0);
    	Msg msg1 = new Msg("How are you ?", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("I'm fine , How about you !", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("I'm fine too ", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
		
	}
}

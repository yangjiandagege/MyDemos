package com.example.test.contentobserver;

import com.example.test.R;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.*;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvAirplane;
	private EditText etSmsoutbox;

	private static final int MSG_AIRPLANE = 1;
	private static final int MSG_OUTBOXCONTENT = 2;

	private AirplaneContentObserver airplaneCO;
	private SMSContentObserver smsContentObserver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentobserver);

		tvAirplane = (TextView) findViewById(R.id.tvAirplane);
		etSmsoutbox = (EditText) findViewById(R.id.smsoutboxContent);

		airplaneCO = new AirplaneContentObserver(this, mHandler);
		smsContentObserver = new SMSContentObserver(this, mHandler);
		
		registerContentObservers() ;
	}

	private void registerContentObservers() {
		Uri airplaneUri = Settings.System.getUriFor(Settings.System.AIRPLANE_MODE_ON);
		getContentResolver().registerContentObserver(airplaneUri, false, airplaneCO);
		Uri smsUri = Uri.parse("content://sms");
		getContentResolver().registerContentObserver(smsUri, true,smsContentObserver);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_AIRPLANE:
				int isAirplaneOpen = (Integer) msg.obj;
				if (isAirplaneOpen != 0)
					tvAirplane.setText("Airplane is open");
				else if (isAirplaneOpen == 0)
					tvAirplane.setText("Airplane is close");
				break;
			case MSG_OUTBOXCONTENT:
				String outbox = (String) msg.obj;
				etSmsoutbox.setText(outbox);
				break;
			default:
				break;
			}
		}
	};
}
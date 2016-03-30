package com.example.test.sensors;

import com.example.test.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 方向传感器界面
 * 
 * @author Gavin
 * 
 */
public class Orientation extends Activity {
	private TextView tvResult = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_result);
		tvResult = (TextView) findViewById(R.id.sensor_result);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			String result = "X轴方向的角度：" + event.values[0] + "度"+'\n';
			result += "Y轴方向的角度：" + event.values[1] + "度"+'\n';
			result += "Z轴方向的角度：" + event.values[2] + "度"+'\n';
			String orientationString = "";
			String topBottomString = "";
			String leftRightString = "";
			if (event.values[0] == 0) {
				orientationString = "正北";
			} else if (event.values[0] == 90) {
				orientationString = "正东";
			} else if (event.values[0] == 180) {
				orientationString = "正南";
			} else if (event.values[0] == 270) {
				orientationString = "正西";
			} else if (event.values[0] < 90 && event.values[0] > 0) {
				orientationString = "北偏东" + event.values[0] + "度";
			} else if (event.values[0] < 180 && event.values[0] > 90) {
				orientationString = "南偏东" + (180 - event.values[0]) + "度";
			} else if (event.values[0] < 270 && event.values[0] > 180) {
				orientationString = "南偏西" + (event.values[0] - 180) + "度";
			} else if (event.values[0] < 360 && event.values[0] > 270) {
				orientationString = "北偏西" + (360 - event.values[0]) + "度";
			}
			if (event.values[1] == 0) {
				topBottomString = "手机头部或底部没有翘起";
			} else if (event.values[1] > 0) {
				topBottomString = "底部向上翘起" + event.values[1] + "度";
			} else if (event.values[1] < 0) {
				topBottomString = "顶部向上翘起" + Math.abs(event.values[1]) + "度";
			}
			if (event.values[2] == 0) {
				leftRightString = "手机左侧或右侧没有翘起";
			} else if (event.values[2] > 0) {
				leftRightString = "右侧向上翘起" + event.values[2] + "度";
			} else if (event.values[2] < 0) {
				leftRightString = "左侧向上翘起" + Math.abs(event.values[2]) + "度";
			}
			result += "手机头部指向：\n          " + orientationString+'\n';
			result += "手机顶部或尾部翘起的角度:\n          " + topBottomString+'\n';
			result += "手机左侧或右侧翘起的角度:\n          " + leftRightString+'\n';
			tvResult.setText(result);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		sensorManager.unregisterListener(listener);
	}

}

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
 * 线性加速度传感器界面
 * 
 * @author Gavin
 * 
 */
public class LinearAccelerometer extends Activity {
	private TextView tvResult = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_result);
		tvResult = (TextView) findViewById(R.id.sensor_result);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	}

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

	private SensorEventListener listener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent e) {
			String result = "沿X方向线性加速度：\n          " + e.values[0] + "m/s^2"+'\n';
			result += "沿Y方向线性加速度：\n          " + e.values[1] + "m/s^2"+'\n';
			result += "沿Z方向线性加速度：\n          " + e.values[2] + "m/s^2"+'\n';
			tvResult.setText(result);
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}

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
 * 加速度传感器界面
 * 
 * @author Gavin
 * 
 */
public class Accelerometer extends Activity {
	private TextView sensor_result = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	private float gravity[] = new float[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_result);
		sensor_result = (TextView) findViewById(R.id.sensor_result);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
			gravity[0] = e.values[0];
			gravity[1] = e.values[1];
			gravity[2] = e.values[2];
			String result = "X方向上加速度：\n          " + gravity[0] + "m/s^2"+'\n';
			result += "Y方向上加速度：\n          " + gravity[1] + "m/s^2"+'\n';
			result += "Z方向上加速度：\n          " + gravity[2] + "m/s^2";
			sensor_result.setText(result);
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}

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
 * 旋转矢量传感器界面
 * 
 * @author Gavin
 * 
 */
public class Rotation extends Activity {
	private TextView tvResult = null;

	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_result);
		tvResult = (TextView) findViewById(R.id.sensor_result);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
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
			String result = "沿X方向旋转矢量：\n          " + e.values[0]+'\n';
			result+="沿Y方向旋转矢量：\n          " + e.values[1]+'\n';
			result+="沿Z方向旋转矢量：\n          " + e.values[2]+'\n';
			tvResult.setText(result);
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}

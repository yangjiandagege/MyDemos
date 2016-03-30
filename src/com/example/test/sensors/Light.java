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
 * 光线传感器界面
 * 
 * @author Gavin
 * 
 */
public class Light extends Activity {
	private TextView tvResult = null;

	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	
	public static final float LIGHT_SHADE=20000.0f;
	public static final float LIGHT_OVERCAST = 10000.0f;
	public static final float LIGHT_SUNRISE = 400.0f;
	public static final float LIGHT_CLOUDY = 100.0f;
	public static final float LIGHT_FULLMOON = 0.25f;
	public static final float LIGHT_NO_MOON = 0.001f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_result);
		tvResult = (TextView) findViewById(R.id.sensor_result);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
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
			float light = e.values[0];
			String result = "光线强度：" + light+'\n';
			if (light < LIGHT_NO_MOON) {
				result += "当前等效状态：\n          没有月亮的夜晚~"+'\n';
			} else if (light >= LIGHT_NO_MOON && light < LIGHT_FULLMOON) {
				result += "当前等效状态：\n          有月亮的夜晚~"+'\n';
			} else if (light >= LIGHT_FULLMOON && light < LIGHT_CLOUDY) {
				result += "当前等效状态：\n          多云的阴天~"+'\n';
			} else if (light >= LIGHT_CLOUDY && light < LIGHT_SUNRISE) {
				result += "当前等效状态：\n          太阳正在升起~"+'\n';
			} else if (light >= LIGHT_SUNRISE&&light <LIGHT_OVERCAST) {
				result += "当前等效状态：\n          多云的白天~"+'\n';
			}else if (light >= LIGHT_OVERCAST) {
				result += "当前等效状态：\n          晴朗的白天~"+'\n';
			}
			tvResult.setText(result);
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}

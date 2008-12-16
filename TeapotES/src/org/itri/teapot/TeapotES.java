package org.itri.teapot;

import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class TeapotES extends Activity implements SensorListener {
		
    private GLSurfaceView mGLSurfaceView;
	private SensorManager sensorManager;
	private float[] sensorValues;
    
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create our Preview view and set it as the content of our
		// Activity
		mGLSurfaceView = new GLSurfaceView(this);
		mGLSurfaceView.setRenderer(new TeapotRender());
		setContentView(mGLSurfaceView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorValues = new float[3];
	}

	@Override
	protected void onResume() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onResume();
		sensorManager.registerListener(this, 
                SensorManager.SENSOR_ACCELEROMETER | 
                SensorManager.SENSOR_MAGNETIC_FIELD | 
                SensorManager.SENSOR_ORIENTATION,
                SensorManager.SENSOR_DELAY_FASTEST);
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onPause();
		sensorManager.unregisterListener(this);
		mGLSurfaceView.onPause();
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
			sensorValues[0] = (float) (values[0] / SensorManager.GRAVITY_EARTH);
			sensorValues[1] = (float) (values[1] / SensorManager.GRAVITY_EARTH);
			sensorValues[2] = (float) (values[2] / SensorManager.GRAVITY_EARTH);
			mGLSurfaceView.onSensorChanged(sensorValues);
		}
	}
}
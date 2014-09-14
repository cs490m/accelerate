package edu.washington.cs.accelerate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by power on 9/2/14.
 */
public class AccelerateService extends Service {
    private static final String TAG = "AccelerateService";
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor barometerSensor;

    private MetricBackend mMetricBackend = new MetricBackend(this);

    public AccelerateService() {
        Log.i(TAG, "Service started...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (this.sensorManager == null) {
            sensorManager = (SensorManager) AccelerateService.this.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
            for (Sensor sensor : sensorList) {
                Log.i(TAG, sensor.getName() + " : " + sensor.getPower());
                if (sensor.getName().equals("BMP280 Barometer")) {
                    barometerSensor = sensor;
                }
            }

            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            Log.i(TAG, "Registered sensors...");

            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    AccelerateService.this.addEvent(sensorEvent);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, accelerometerSensor, 1000 * 1000, 1000 * 1000 * 10);

            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    AccelerateService.this.addEvent(sensorEvent);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, barometerSensor, 1000 * 1000, 1000 * 1000 * 10);

        } else {
            Log.i(TAG, "Service is already active, skipping registration.");
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void addEvent(SensorEvent sensorEvent) {
        mMetricBackend.addEvent(sensorEvent.sensor.getName(),
                sensorEvent.timestamp, sensorEvent.values);
    }
}

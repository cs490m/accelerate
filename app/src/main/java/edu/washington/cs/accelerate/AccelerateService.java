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
    private Map<String, SensorReadings> sensorReadings = new HashMap<String, SensorReadings>();

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
                    Log.i(TAG, String.format("x: %f, y: %f, z: %f",
                            sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                    AccelerateService.this.addEvent(sensorEvent);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, accelerometerSensor, 1000 * 1000 * 10, 1000 * 1000 * 10);

            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    Log.i(TAG, String.format("Barometer: %f %f %f",
                            sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, barometerSensor, 1000 * 1000 * 10, 1000 * 1000 * 10);

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
        String name = sensorEvent.sensor.getName();
        if (!sensorReadings.containsKey(name)) {
            sensorReadings.put(name, new SensorReadings(name));
        }

        SensorReadings readings = sensorReadings.get(name);
        readings.measurements.add(new Measurement(sensorEvent.values, sensorEvent.timestamp));
    }


    private class Measurement {
        public float[] values;
        public long timestamp;

        public Measurement(float[] values, long timestamp) {
            this.values = values;
            this.timestamp = timestamp;
        }
    }

    private class SensorReadings {
        private String name;
        private List<Measurement> measurements = new ArrayList<Measurement>();

        public SensorReadings(String name) {
            this.name = name;
        }
    }
}

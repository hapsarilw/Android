package com.example.m0815037;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;

    Sensor accelerometer;
    Sensor magnetometer;

    TextView tvAzimuthAc, tvPitchAc, tvRollAc;
    TextView tvAzimuthMg, tvPitchMg, tvRollMg;

    float[] accelerometerReading;
    float[] magnetometerReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAzimuthAc = findViewById(R.id.tv_azimuth_ac);
        tvPitchAc = findViewById(R.id.tv_pitch_ac);
        tvRollAc = findViewById(R.id.tv_roll_ac);

        tvAzimuthMg = findViewById(R.id.tv_azimuth_mg);
        tvPitchMg = findViewById(R.id.tv_pitch_mg);
        tvRollMg = findViewById(R.id.tv_roll_mg);

        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        List<Sensor> sensorList  =
                mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        this.showAllSensor();





    }

    public void showAllSensor(){
        List<Sensor> sensorList = this.mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor currentSensor : sensorList){
            Log.d("sensor", currentSensor.getName());
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(this.accelerometer != null){
            this.mSensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(this.magnetometer != null){
            this.mSensorManager.registerListener(this, this.magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected  void onStop(){
        super.onStop();
        this.mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event){
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                this.accelerometerReading = event.values.clone();
                final float[] rotationMatrix = new float[9];
                mSensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
                // Express updated rotation matrix as 3 orientation angles.
                final float[] orientationAngles = new float[3];
                mSensorManager.getOrientation(rotationMatrix, orientationAngles);
                tvAzimuthAc.setText("Azimuth  : "+orientationAngles[0]);
                tvPitchAc.setText("Pitch: "+orientationAngles[1]);
                tvRollAc.setText("Roll : "+orientationAngles[2]);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetometerReading = event.values.clone();
                final float[] rotationMatrix2 = new float[9];
                mSensorManager.getRotationMatrix(rotationMatrix2, null, accelerometerReading, magnetometerReading);
                // Express updated rotation matrix as 3 orientation angles.
                final float[] orientationAngles2 = new float[3];
                mSensorManager.getOrientation(rotationMatrix2, orientationAngles2);
                tvAzimuthMg.setText("Azimuth  : "+orientationAngles2[0]);
                tvPitchMg.setText("Pitch: "+orientationAngles2[1]);
                tvRollMg.setText("Roll : "+orientationAngles2[2]);
                break;
        }
        // Rotation matrix based on current readings.



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}

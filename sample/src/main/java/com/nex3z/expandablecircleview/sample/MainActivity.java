package com.nex3z.expandablecircleview.sample;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nex3z.expandablecircleview.ExpandableCircleView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final float MAX_FORCE = 4.0f;
    private static final float ONE_G = 1.0f;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    final Random mRnd = new Random();

    private ExpandableCircleView mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircle = (ExpandableCircleView) findViewById(R.id.circle);

        Button button = (Button) findViewById(R.id.btn_change_color);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int outerColor = getRandomColor();
                    mCircle.setOuterColor(outerColor);
                    int innerColor = getRandomColor();
                    mCircle.setInnerColor(innerColor);
                }
            });
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;
        float force = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        mCircle.expand((force - ONE_G)/MAX_FORCE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private int getRandomColor() {
        return Color.argb(255, mRnd.nextInt(256), mRnd.nextInt(256), mRnd.nextInt(256));
    }

}

package com.example.timeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private CurrentTime currentTime;
    private TextView textView;
    private Sensor sensor;
    private SensorManager sensorManager;
    private Chronometer chronometer;
    private boolean goAhead;
    private boolean running;
    private long pauseOffSet;
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private boolean timerButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //setting up the time left in day on the screen
        currentTime = new CurrentTime();
        setTimeLeft();

        //creating sensor manager
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //setting up accelerometer
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //registering sensor listener
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //initializing chronometer
        chronometer = (Chronometer)findViewById(R.id.chronometer1);

        //setting up image view
        imageViewOne = (ImageView)findViewById(R.id.imageView);
        imageViewTwo = (ImageView)findViewById(R.id.imageView2);

    }

    public void timerplaybutton(View view){
        //Log.i("timerplaybutton", "clicked");
        timerButton = true;
        chronoMeterManager(true);


    //    imageViewOne.setVisibility(View.INVISIBLE);
     //   imageViewTwo.setVisibility(View.VISIBLE);
    }

    public void timerpausebutton(View view){
        //Log.i("timerpausebutton", "clicked");
        timerButton = false;
        chronoMeterManager(false);

      //  imageViewOne.setVisibility(View.VISIBLE);
     //   imageViewTwo.setVisibility(View.INVISIBLE);
    }

    //resetting chronometer
    public void chronoClick(View view){
        chronometer.setBase(SystemClock.elapsedRealtime());pauseOffSet=0;
    }

    //managing chronometer through accelerometer
    public void chronoMeterManager(boolean goAhead){
        if(goAhead==true&&!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffSet);
            chronometer.start();
            running = true;
            imageViewOne.setVisibility(View.INVISIBLE);
            imageViewTwo.setVisibility(View.VISIBLE);
        }
        if(goAhead==false&&running){
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime()-chronometer.getBase();
            running = false;
            imageViewOne.setVisibility(View.VISIBLE);
            imageViewTwo.setVisibility(View.INVISIBLE);
        }
    }
    //monitoring changes on accelerometer
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!timerButton){
        if(event.values[2]<-8){
            chronoMeterManager(true);

        }
        else{
            chronoMeterManager(false);

        }}

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use
    }

    //setting time left
    public void setTimeLeft(){
        textView = (TextView)findViewById(R.id.textView3);
        long hour = currentTime.getTimeLeft();
        long minute = currentTime.getNowMinute();
        if(hour<10&&minute<10){
            textView.setText("0"+hour+":"+"0"+minute);
        } else if(hour<10&&minute>10){
            textView.setText("0"+hour+":"+minute);
        } else if(hour>10&&minute<10){
            textView.setText(hour+":"+"0"+minute);
        }
        else {
            textView.setText(hour+":"+minute);
        }
    }
}
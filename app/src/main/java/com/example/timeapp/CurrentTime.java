package com.example.timeapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public class CurrentTime {
    private long nowHour;
    private long nowMinute;
    private long timeLeft;
    LocalTime localTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CurrentTime(){
        localTime = LocalTime.now();
        this.nowHour = localTime.getHour();
        this.nowMinute = localTime.getMinute();
        this.timeLeft = timeLeft(nowHour, nowMinute);
    }

    public long getTimeLeft(){
        return this.timeLeft;
    }

    public long getNowHour() {
        return this.nowHour;
    }

    public long getNowMinute() {
        return this.nowMinute;
    }

    private long timeLeft(long nowHour, long nowMinute){
        if(nowMinute > 0){
            timeLeft = 23 - nowHour;
        } else if(nowMinute == 0){
            timeLeft = 24 - nowHour;
        } else {
            timeLeft = 0;
        }
        return timeLeft;
    }




}
/*
*     @RequiresApi(api = Build.VERSION_CODES.O)
    public void timerTask() {
        LocalTime currentTime = LocalTime.now();
        int currentHour = (int) currentTime.getHour();
        int currentMinute = (int) currentTime.getMinute();
        int exactHourLeft = timeLeftHour(currentHour, currentMinute);
        TextView textViewTwo = (TextView) findViewById(R.id.textView4);
        textViewTwo.setText(exactHourLeft + ":" + (60 - currentMinute));
    }


    public static int timeLeftHour(int currentHour, int currentMinute) {
        if (currentMinute > 0) {
            return 23 - currentHour;
        } else if (currentMinute == 0) {
            return 24 - currentHour;
        } else {
            return 0;
        }
    }

*
* */
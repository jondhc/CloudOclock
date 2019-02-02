package com.jondhc.cloudoclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLocalTime();
    } //end onCreate

    public void setLocalTime(){
        TextView localTime = findViewById(R.id.localTime);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(c.getTime());
        localTime.setText(formattedTime);
    } //end setLocalTime




} //end MainActivity

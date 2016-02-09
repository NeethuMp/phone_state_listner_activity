package com.example.neethu.phonestatelistneractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by neethu on 8/2/16.
 */
public class BatteryStatusActiviy extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_status);
        Intent statusIntent=getIntent();
//        statusIntent.getIntExtra(MainActivity.BATTERY_PERCENTAGE);
    }


}

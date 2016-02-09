package com.example.neethu.phonestatelistneractivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static final String CONNECTIVITY_STATUS="btr_status";
    private  int connectivityStatus;
//    private  int wifi;


    Button battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battery = (Button) findViewById(R.id.battery_button);
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getBatteryPercentageConnectivityStatus();
            }
        });
    }
    public int getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == connectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if (activeNetwork.getType() == connectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
            else
                return TYPE_NOT_CONNECTED;
        return 0;
    }

   public int getBatteryPercentageConnectivityStatus() {
       connectivityStatus= getConnectivityStatus(getApplicationContext());
       Toast.makeText(getApplicationContext(),"CONNECTIVITY STATUS:"+connectivityStatus,
               LENGTH_LONG).show();

       BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (currentLevel >= 0 && scale > 0)
                    level = (currentLevel * 100) / scale;
               Toast.makeText(getApplication(), "Battery Level Percentage:" + level + "%",
                        LENGTH_LONG).show();
            }
        };

        IntentFilter batteryLevelFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver,batteryLevelFilter);
        Intent intent=this.registerReceiver(null,batteryLevelFilter);
        int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        if(status==BatteryManager.BATTERY_STATUS_CHARGING)
            Toast.makeText(getApplicationContext(),"The Battery is Charging", LENGTH_LONG)
                    .show();
        if(status==BatteryManager.BATTERY_STATUS_DISCHARGING)
           Toast.makeText(getApplicationContext(),"The Battery is Discharging", LENGTH_LONG)
                    .show();
        return status;
    }
}

package com.s.c.s.abyssinia.callertelker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ${yohannes} on 17/11/2017.
 */
public class BatteryChengeDetector extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(BatteryChengeDetector.this, "service Battery check", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent BatteryStatus = registerReceiver(null,intentFilter);
        assert BatteryStatus != null;
        int status = BatteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        String btstat = null;
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            btstat = "charging   ...   ";

        } if (status == BatteryManager.BATTERY_STATUS_FULL) {
            btstat = "battery full";

        } if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
            btstat = "not charging";
        }

        Toast.makeText(BatteryChengeDetector.this, btstat, Toast.LENGTH_SHORT).show();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

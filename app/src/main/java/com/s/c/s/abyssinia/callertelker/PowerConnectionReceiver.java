package com.s.c.s.abyssinia.callertelker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

/**
 * Created by ${yohannes} on 17/11/2017.
 */
public class PowerConnectionReceiver extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        boolean isCharging  =  state == BatteryManager.BATTERY_STATUS_CHARGING || state == BatteryManager.BATTERY_STATUS_FULL;


        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);

        boolean usb = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        boolean ac = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;

    }
}

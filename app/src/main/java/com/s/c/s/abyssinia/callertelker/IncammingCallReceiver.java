package com.s.c.s.abyssinia.callertelker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by ${yohannes} on 16/11/2017.
 */
public class IncammingCallReceiver extends BroadcastReceiver {
            Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals( TelephonyManager.CALL_STATE_RINGING)){
                Toast.makeText(context, "ringing...............Resever", Toast.LENGTH_SHORT).show();

            } if (state.equals(TelephonyManager.CALL_STATE_OFFHOOK)){
                Toast.makeText(context, "phone is currently in call....resiver ", Toast.LENGTH_SHORT).show();
            } if (state.equals(TelephonyManager.CALL_STATE_IDLE)){
                Toast.makeText(context, "niether Ringing nor in a call........reciver", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

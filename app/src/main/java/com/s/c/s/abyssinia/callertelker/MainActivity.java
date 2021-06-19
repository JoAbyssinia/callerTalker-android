package com.s.c.s.abyssinia.callertelker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button ansbtn,bttny;
    public TextView btTExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView  = (TextView)findViewById(R.id.textview);
        btTExt = (TextView)findViewById(R.id.btText);
        //startService(new Intent(this,IncammingCallService.class));
        startService(new Intent(this,BatteryChengeDetector.class));
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        String IMEINumber = tm.getDeviceId();
        String subscriberId = tm.getDeviceId();
        String SIMserviceNumber = tm.getSimSerialNumber();
        String networkCounterISO = tm.getNetworkCountryIso();
        String SIMCounteryID = tm.getSimCountryIso();
        String softwareVerision = tm.getDeviceSoftwareVersion();
        String VoiceMeilNumber = tm.getVoiceMailNumber();

        String strphoneType= "";

        int phoneType = tm.getPhoneType();
        switch (phoneType){
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GCM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }
        boolean isRoaming = tm.isNetworkRoaming();
        String info = "phone detail:\n";
        info+= "\nIMEINumber: "+IMEINumber;
        info+= "\nsubscriberId: "+subscriberId;
        info+= "\nSIMserviceNumber: "+SIMserviceNumber;
        info+= "\nnetworkCounterISO: "+networkCounterISO;
        info+= "\nSIMCounteryID: "+SIMCounteryID;
        info+= "\nsoftwareVerision: "+softwareVerision;
        info+= "\nVoiceMeilNumber: "+VoiceMeilNumber;
        info+= "\nnet work type: "+strphoneType;
        info+= "\nRoaming: "+isRoaming;
        textView.setText(info);

        BatteryManager bm = (BatteryManager)getSystemService(Context.BATTERY_SERVICE);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent BatteryStatus = registerReceiver(null,intentFilter);
        int status = BatteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        String btstat,info2;
        switch (status){
            case BatteryManager.BATTERY_STATUS_CHARGING:
                btstat="charging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                btstat= "battery full";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                btstat = "unknown";
                break;
            default :
                btstat = "not charging";
        }
        int ChargePlug = BatteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
        boolean usb = ChargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean ac = ChargePlug==BatteryManager.BATTERY_PLUGGED_AC;
        String plug = null;
        if (usb){
            plug = "USB";
        }if (ac){
            plug = "AC";
        }

        int level = BatteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = BatteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        float batteryper = level/(float)scale*100;

        int bthel = BatteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH,-1);
        String healhybt = null;
        switch (bthel){
            case BatteryManager.BATTERY_HEALTH_COLD:
                healhybt = "BATTERY_HEALTH_COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healhybt="BATTERY_HEALTH_GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healhybt = "BATTERY_HEALTH_DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healhybt= "BATTERY_HEALTH_OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healhybt= "BATTERY_HEALTH_UNKNOWN";
                break;
        }
        int temps = BatteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
        switch (temps){
            //case BatteryManager.:
        }
        String temp = BatteryManager.EXTRA_TEMPERATURE;

        info2 = "\tbattery detail ";
        info2+= "\nbattery condition :" +btstat;
        info2+= "\nplugged :"+ plug;
        info2+= "\nlevel :"+batteryper;
        info2 += "\nbattery healthy :"+healhybt;
        info2+="\ntemp :"+temp;
        btTExt.setText(info2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ansbtn = (Button)findViewById(R.id.ans);
        bttny = (Button)findViewById(R.id.bt);
        ansbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,IncammingCallService.class);
                startService(i);
            }
        });
        bttny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(),BatteryChengeDetector.class));
            }
        });
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

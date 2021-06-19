package com.s.c.s.abyssinia.callertelker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import android.widget.Toast;


import java.util.Locale;

/**
 * Created by ${yohannes} on 16/11/2017.
 */
public class IncammingCallService extends Service implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
      throw new UnsupportedOperationException("not yet impliment");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(IncammingCallService.this, "servicve in camming call ", Toast.LENGTH_SHORT).show();
        tts = new TextToSpeech(getApplicationContext(),this);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener callStatesListener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING){
                    tts.speak("in coming call"+incomingNumber,TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(getApplicationContext(), "ringing", Toast.LENGTH_SHORT).show();
                } if (state == TelephonyManager.CALL_STATE_OFFHOOK){
                    Toast.makeText(getApplicationContext(), "phone is currently in call ", Toast.LENGTH_SHORT).show();
                } if (state == TelephonyManager.CALL_STATE_IDLE){
                    Toast.makeText(getApplicationContext(), "niether Ringing nor in a call", Toast.LENGTH_SHORT).show();
                }
            }
        };
        tm.listen(callStatesListener,PhoneStateListener.LISTEN_CALL_STATE);
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {

        if (tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS ){
            int result = tts.setLanguage(Locale.getDefault());
            if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(getApplicationContext(), "this language is not supported", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(IncammingCallService.this, "speech failed", Toast.LENGTH_SHORT).show();
        }
    }
}

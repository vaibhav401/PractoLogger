package com.example.vaibhavchoudhary.betterlogger;

import android.app.IntentService;
import android.content.Intent;


public class TestLoggerIntentService extends IntentService {


    private static final String TAG = TestLoggerIntentService.class.getSimpleName();

    public TestLoggerIntentService() {
        super("No custom thread name ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       for(int i =0; i < MainActivity.LOOP_COUNTER; i++){
           PractoLogger.i(TAG, "Message From service " + i);
       }
    }

}

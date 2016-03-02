package com.example.vaibhavchoudhary.betterlogger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyTestService extends Service {
    private static final String TAG = MyTestService.class.getSimpleName();
    public MyTestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < MainActivity.LOOP_COUNTER; i++) {
                        String g = null;
                        try{
                            int k = g.length();
                        }catch (NullPointerException e){
                            Log.e(TAG, "some message",e );
                            e.printStackTrace();
                            PractoLogger.e(TAG, "message from Service ", e);
                            PractoLogger.d(TAG, "message from Service ", e);
                            PractoLogger.w(TAG, "message from Service ", e);
                            PractoLogger.e(TAG, "message from Service ", e);
                            PractoLogger.v(TAG, "message from Service ", e);
                        }
                }
            }
        };
        thread.start();
    }
}

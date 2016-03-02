package com.example.vaibhavchoudhary.betterlogger;

import android.app.Application;
import android.util.Log;

import java.io.IOException;

/**
 * Created by vaibhavchoudhary on 01/03/16.
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "opening application");
        try {
            PractoLogger.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "Closing application");
        PractoLogger.close();
        super.onLowMemory();
    }
}

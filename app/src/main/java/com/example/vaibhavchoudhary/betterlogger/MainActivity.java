package com.example.vaibhavchoudhary.betterlogger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;


    public class MainActivity extends AppCompatActivity {


        private static final String TAG = MainActivity.class.getSimpleName();
        public static final long LOOP_COUNTER = 1000;
    private PractoLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PractoLogger.i(MainActivity.class.getSimpleName(), "Button clicked  " );
//                PractoLogger.w(MainActivity.class.getSimpleName(), "Button clicked  " );
//                PractoLogger.v(MainActivity.class.getSimpleName(), "Button clicked  " );
//                PractoLogger.d(MainActivity.class.getSimpleName(), "Button clicked  " );
//                PractoLogger.e(MainActivity.class.getSimpleName(), "Button clicked  " );
//            }
//        });

        Intent intentService = new Intent(this, MyTestService.class);
        startService(intentService);
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
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
                return null;
            }
        }.execute();
//        for(int i = 0; i < LOOP_COUNTER; i++){
//            PractoLogger.i(MainActivity.class.getSimpleName(), "some message from Main Activity " + i);
//        }
    }

}
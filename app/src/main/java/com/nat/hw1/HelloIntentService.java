package com.nat.hw1;


import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


public class HelloIntentService extends IntentService {
    private String TAG = "HELLO HelloIntentService";

    public HelloIntentService() {
        super("HelloIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent()");
        String name = intent.getStringExtra(Intent.EXTRA_TEXT);
        String greeting = String.format("Hello, %s!", name);
        Log.i(TAG, greeting);
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(Intent.ACTION_SEND);
        serviceIntent.putExtra(Intent.EXTRA_TEXT, greeting);

        LocalBroadcastManager.getInstance(this).sendBroadcast(serviceIntent);
    }
}
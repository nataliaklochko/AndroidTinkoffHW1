package com.nat.hw1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SecondActivity extends AppCompatActivity {

    private String TAG = "HELLO SecondActivity";

    private Button callIntentService;
    private EditText nameText;
    private String name;

    private BroadcastReceiver helloReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"helloReceiver onReceive()");
            String greeting = intent.getStringExtra(Intent.EXTRA_TEXT);

            Intent helloIntent = new Intent();
            helloIntent.putExtra(Intent.EXTRA_TEXT, greeting);
            sendResult(helloIntent);
        }

    };

    private void sendResult(Intent data) {
        Log.i(TAG, "sendResult()");
        setResult(FirstActivity.RESULT_OK, data);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(TAG, "onCreate()");

        callIntentService = (Button) findViewById(R.id.callIntentService);
        nameText = (EditText) findViewById(R.id.nameText);

        View.OnClickListener onClickCallIntentService = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "callIntentService onClick()");
                name = nameText.getText().toString();
                if (name.isEmpty()) {
                    name = "User";
                }
                Intent intent = new Intent(SecondActivity.this, HelloIntentService.class);
                intent.putExtra(Intent.EXTRA_TEXT, name);
                startService(intent);
            }
        };
        callIntentService.setOnClickListener(onClickCallIntentService);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SEND);
        LocalBroadcastManager.getInstance(this).registerReceiver(helloReceiver, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(helloReceiver);
    }


}

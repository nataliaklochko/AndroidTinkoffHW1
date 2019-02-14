package com.nat.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    private String TAG = "HELLO FirstActivity";

    private Button openSecondActivityBtn;
    private TextView helloTextView;
    protected static Integer SECOND_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Log.i(TAG, "onCreate()");

        openSecondActivityBtn = (Button) findViewById(R.id.open_secondactivity_btn);
        helloTextView = (TextView) findViewById(R.id.helloTextView);

        OnClickListener onClickOpenSecondActivity = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "openSecondActivityBtn onClick()");
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST);
            }
        };
        openSecondActivityBtn.setOnClickListener(onClickOpenSecondActivity);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FirstActivity.RESULT_OK && requestCode == SECOND_ACTIVITY_REQUEST) {
                String greeting = data.getStringExtra(Intent.EXTRA_TEXT);
                Log.i(TAG, String.format("onActivityResult()"));
                helloTextView.setText(greeting);
            }
    }
}


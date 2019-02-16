package com.nat.hw1;


import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;


public class HelloIntentService extends IntentService {
    private String TAG = "HELLO HelloIntentService";

    private static final String[] PROJECTION = { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
    private static final Uri CONTACTS_URI = ContactsContract.Data.CONTENT_URI;

    public HelloIntentService() {
        super("HelloIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent()");
        String name = intent.getStringExtra(Intent.EXTRA_TEXT);
        String greeting;
        ArrayList<String> contactNames = getContactNames();
        if (contactNames.isEmpty()) {
            greeting = String.format("Hello from %s!", name);
        } else {
            String contactNamesString = String.join(", ", contactNames);
            greeting = String.format("Hello, %s from %s", contactNamesString, name);
        }
        Log.i(TAG, greeting);
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(Intent.ACTION_SEND);
        serviceIntent.putExtra(Intent.EXTRA_TEXT, greeting);

        LocalBroadcastManager.getInstance(this).sendBroadcast(serviceIntent);
    }



    private ArrayList<String> getContactNames() {
        ArrayList<String> contactNames = new ArrayList<String>();

        try {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(CONTACTS_URI, PROJECTION,null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                String contactName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                );
                contactNames.add(contactName);
            }
            cursor.close();
        } catch (SecurityException e) {
            Log.i(TAG,"getContactNames(): contact permission denied");
        }
        return contactNames;
    }
}
package com.example.usr.scheduler.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.usr.scheduler.data.ReminderContract;

public class ReminderService extends IntentService {

    private static final String TAG = ReminderService.class.getSimpleName();

    private static final String ACTION_ADD = TAG + ".INSERT";
    private static final String ACTION_REMOVE = TAG + ".DELETE";

    public static final String EXTRA_VALUES = TAG + ".extra.REMINDER_VALUES";
    public static final String EXTRA_DELETE_URI = TAG + ".extra.DELETE_URI";


    public ReminderService() {
        super(TAG);
    }

    public static void addReminder(Context context, ContentValues values) {
        Intent intent = new Intent(context, ReminderService.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void removeReminder(Context context, Uri uri) {
        Intent intent = new Intent(context, ReminderService.class);
        intent.setAction(ACTION_REMOVE);
        intent.putExtra(EXTRA_DELETE_URI, uri);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_ADD)) {
                ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
                handleActionAdd(values);
            } else if (action.equals(ACTION_REMOVE)) {
                Uri uri = intent.getParcelableExtra(EXTRA_DELETE_URI);
                handleActionRemove(uri);
            }
        }
    }

    private void handleActionRemove(Uri uri) {
        if (getContentResolver().delete(uri, null, null) != 0) {
            Log.d(TAG, "Deleted reminder with id " + uri.getLastPathSegment());
        } else {
            Log.w(TAG, "Error Deleting reminder with id " + uri.getLastPathSegment());
        }
    }

    private void handleActionAdd(ContentValues values) {
        if (getContentResolver().insert(ReminderContract.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted new reminder");
        } else {
            Log.w(TAG, "Error inserting new reminder");
        }
    }


}

package com.example.usr.scheduler.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import static com.example.usr.scheduler.data.ReminderContract.CONTENT_AUTHORITY;
import static com.example.usr.scheduler.data.ReminderContract.TABLE_NAME;

public class ReminderProvider extends ContentProvider {

    private ReminderSQLite mReminderSQLite;

    /** Matcher identifier for all reminder **/
    private static final int REMINDERS = 100;
    /** Matcher identifier for one reminder **/
    private static final int SINGLE_REMINDER = 101;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // "content://com.example.usr.scheduler/reminders"
        matcher.addURI(CONTENT_AUTHORITY, TABLE_NAME, REMINDERS);

        // "content://com.example.usr.scheduler/reminders"
        matcher.addURI(CONTENT_AUTHORITY, TABLE_NAME + "/#", SINGLE_REMINDER);
    }

    @Override
    public boolean onCreate() {
        mReminderSQLite = new ReminderSQLite(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}

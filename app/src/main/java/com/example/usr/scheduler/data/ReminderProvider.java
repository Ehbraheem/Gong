package com.example.usr.scheduler.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db;
        int match = matcher.match(uri);
        Cursor cursor;

        switch (match) {
            case REMINDERS:
                db = mReminderSQLite.getReadableDatabase();
                cursor = db.query(TABLE_NAME,
                                  projection,
                                  selection,
                                  selectionArgs,
                                  null,
                                  null,
                                  sortOrder);
                break;

            case SINGLE_REMINDER:
                db = mReminderSQLite.getReadableDatabase();
                String reminderId = uri.getLastPathSegment();
                String[] args = makeSelectionArgs(ReminderContract.Columns._ID);
                String select = makeSelection(reminderId);


                cursor = cursor = db.query(TABLE_NAME,
                        projection,
                        select,
                        args,
                        null,
                        null,
                        sortOrder);
                break;

                default:

                    throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id = 0;
        Uri returnUri = null;

        switch (matcher.match(uri)) {

            case REMINDERS:
                final SQLiteDatabase db = mReminderSQLite.getWritableDatabase();

                id = db.insert(TABLE_NAME,
                        null,
                        values);


                if (id > 0) {
                    returnUri = ReminderContract.buildUriWithId(id);
                }

                break;

            default:

                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(returnUri, null);
        return  returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        int numDeleted = 0;

        switch (matcher.match(uri)) {

            case SINGLE_REMINDER:

                final SQLiteDatabase db = mReminderSQLite.getWritableDatabase();
                String reminderId = uri.getLastPathSegment();
                String[] args = makeSelectionArgs(reminderId);
                String condition = makeSelection(ReminderContract.Columns._ID);

                numDeleted = db.delete(TABLE_NAME,
                        condition,
                        args);

                break;

            default:

                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @NonNull
    private String[] makeSelectionArgs(Object obj) {
        return new String[]{String.valueOf(obj)};
    }

    private String makeSelection(String criteria) {
        return criteria + " = ? ";
    }
}

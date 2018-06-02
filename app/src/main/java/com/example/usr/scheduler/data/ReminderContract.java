package com.example.usr.scheduler.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ReminderContract {

    public static final String TABLE_NAME = "reminders";

    public static final String CONTENT_AUTHORITY = "com.example.usr.scheduler";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final String CONTENT_DIR_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    public static Uri buildUriWithId(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static final class Columns implements BaseColumns {

        public static final String REMINDER_TITLE = "title";

        public static final String REMINDER_DATE = "date";

        public static final String REMINDER_TYPE = "type";

        public static final String REMINDER_BODY = "body";

    }

}

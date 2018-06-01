package com.example.usr.scheduler.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_BODY;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_DATE;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_TITLE;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_TYPE;
import static com.example.usr.scheduler.data.ReminderContract.TABLE_NAME;
import static com.example.usr.scheduler.data.ReminderContract.Columns._ID;

public class ReminderSQLite extends SQLiteOpenHelper {

    public static final String DB_NAME = "reminder.db";
    public static final int DB_VERSION = 0;

    public ReminderSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "   +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "             +
                REMINDER_TITLE + " STRING NOT NULL, "                    +
                REMINDER_DATE + " TIMESTAMP NOT NULL, "                  +
                REMINDER_TYPE + " STRING NOT NULL, "                     +
                REMINDER_BODY + " TEXT NOT NULL, "                       +
                "UNIQUE ( " + REMINDER_TITLE + " ) ON CONFLICT REPLACE " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
}

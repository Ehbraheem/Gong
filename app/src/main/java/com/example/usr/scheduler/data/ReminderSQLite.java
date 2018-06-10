package com.example.usr.scheduler.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.usr.scheduler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_BODY;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_DATE;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_TITLE;
import static com.example.usr.scheduler.data.ReminderContract.Columns.REMINDER_TYPE;
import static com.example.usr.scheduler.data.ReminderContract.TABLE_NAME;
import static com.example.usr.scheduler.data.ReminderContract.Columns._ID;

public class ReminderSQLite extends SQLiteOpenHelper {

    public static final String DB_NAME = "reminder.db";
    public static final int DB_VERSION = 1;

    public static final String TAG = ReminderSQLite.class.getName();

    private Resources mResources;

    public ReminderSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mResources = context.getResources();
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

        addDemoReminders(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    private void addDemoReminders(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            try {
                readRemindersFromResource(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Unable to pre-fill database", e);
        }
    }

    private void readRemindersFromResource(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.reminders);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        // Parse resource to key/values
        JSONObject root = new JSONObject(builder.toString());

        JSONArray reminders = getReminders(root);
        
        // Inserts values to Database
        insertRemindersFromJSONArray(db, reminders);
    }

    private void insertRemindersFromJSONArray(SQLiteDatabase db, JSONArray reminders) throws JSONException {
        for (int i = 0; i < reminders.length(); i++) {
            JSONObject reminder = reminders.getJSONObject(i);
            insertReminder(reminder, db);
        }
    }

    private void insertReminder(JSONObject reminder, SQLiteDatabase db) throws JSONException {
        String title = reminder.getString("title");
        String date = reminder.getString("date");
        String type = reminder.getString("type");
        String body = reminder.getString("body");

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("date", date);
        values.put("body", body);
        values.put("type", type);

        db.insert(ReminderContract.TABLE_NAME,
                null,
                values);
    }

    private JSONArray getReminders(JSONObject root) throws JSONException {
        return root.getJSONArray("reminders");
    }
}

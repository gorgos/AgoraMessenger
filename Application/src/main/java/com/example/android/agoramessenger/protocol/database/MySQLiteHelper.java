package com.example.android.agoramessenger.protocol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xx on 22.12.15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "messages.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_FROM = "m_from";
    public static final String COLUMN_TO = "m_to";
    public static final String COLUMN_MESSAGE= "m_message";
    public static final String COLUMN_TIMESTAMP= "timestamp";

    public static final String TABLE_CONNECTED_DEVICES = "connected_devices";
    public static final String COLUMN_ADDRESS = "address";

    // Database creation sql statement
    private static final String MESSAGES_DATABASE_CREATE = "create table "
            + TABLE_MESSAGES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_FROM + " text not null, "
            + COLUMN_TO + " text not null, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_TIMESTAMP + " text not null);";

    // Database creation sql statement
    private static final String CONNECTED_DEVICES_DATABASE_CREATE = "create table "
            + TABLE_CONNECTED_DEVICES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ADDRESS + " text, "
            + COLUMN_TIMESTAMP + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        System.out.println("kreiert!");
        database.execSQL(MESSAGES_DATABASE_CREATE);
        database.execSQL(CONNECTED_DEVICES_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTED_DEVICES);
        onCreate(db);
    }
}

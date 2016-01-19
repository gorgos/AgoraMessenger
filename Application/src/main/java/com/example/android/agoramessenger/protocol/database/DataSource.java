package com.example.android.agoramessenger.protocol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 21.12.15.
 */
public class DataSource {
    private static final String TAG = "DataSource";
    private final Context context;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns_messages = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_FROM, MySQLiteHelper.COLUMN_TO, MySQLiteHelper.COLUMN_MESSAGE, MySQLiteHelper.COLUMN_TIMESTAMP};
    private String[] allColumns_connected_devices = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_ADDRESS, MySQLiteHelper.COLUMN_TIMESTAMP};


    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.context = context;
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     *
     */
    public void createMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FROM, message.getFrom());
        values.put(MySQLiteHelper.COLUMN_TO, message.getTo());
        values.put(MySQLiteHelper.COLUMN_MESSAGE, message.getMessage());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, message.getTimestamp());

        long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGES, null, values);

        Log.i(TAG, "Message '" + message.getMessage() + "' stored with id: " + insertId);
    }

    /**
     *
     */
    public void createConnectedDevice(ConnectedDevice connectedDevice) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ADDRESS, connectedDevice.getAddress());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, connectedDevice.getTimestamp());

        long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGES, null, values);

        Log.i(TAG, "Message stored with id: " + insertId);
    }

    /**
     *
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
                allColumns_messages, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    /**
     *
     */
    public List<ConnectedDevice> getAllConnectedDevices() {
        List<ConnectedDevice> connectedDevices = new ArrayList<ConnectedDevice>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
                allColumns_messages, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ConnectedDevice connectedDevice = cursorToConnectedDevice(cursor);
            connectedDevices.add(connectedDevice);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return connectedDevices;
    }


    /**
     *
     */
    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setFrom(cursor.getString(1));
        message.setTo(cursor.getString(2));
        message.setMessage(cursor.getString(3));
        message.setTimestamp(cursor.getString(4));
        return message;
    }

    /**
     *
     */
    private ConnectedDevice cursorToConnectedDevice(Cursor cursor) {
        ConnectedDevice connectedDevice = new ConnectedDevice();
        connectedDevice.setId(cursor.getLong(0));
        connectedDevice.setAddress(cursor.getString(0));
        connectedDevice.setTimestamp(cursor.getString(1));
        return connectedDevice;
    }
}

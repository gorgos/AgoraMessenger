package com.example.android.agoramessenger.protocol;

import android.content.Context;
import android.os.Handler;

import com.example.android.agoramessenger.Constants;
import com.example.android.agoramessenger.protocol.database.DataSource;
import com.example.android.agoramessenger.protocol.database.Message;
import com.example.android.common.logger.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by xx on 21.12.15.
 */
public class ProtocolHelper {

    // return values for processCommand
    public static final int RETURN_VALUE_NORMAL = 1;
    public static final int RETURN_VALUE_NOMORETOSEND = 0;
    public static final int RETURN_VALUE_ERROR = 2;
    public static final int RETURN_VALUE_SHOWMESSAGE = 3;

    public static final String CMD_MESSAGE = "msg";
    public static final String CMD_NOMORETOSEND = "noMoreToSend";

    private static final String TAG = "ProtocolHelper";

    private Context context;
    private Handler handler;

    public ProtocolHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     * processes a command. If command is 'noMoreToSend', return 1, else return 0
     */
    public int processCommand(String command, String address) {
        try {
            JSONObject mainObject = new JSONObject(command);
            String cmd = mainObject.getString("cmd");
            switch(cmd) {
                case CMD_MESSAGE:
                    JSONObject message = mainObject.getJSONObject("message");
                    processMessageCmd(message, address);
                    return RETURN_VALUE_NORMAL;
                case CMD_NOMORETOSEND:
                    return RETURN_VALUE_NOMORETOSEND;
                default:
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return RETURN_VALUE_ERROR;
    }

    /**
     * get Messages (in transport format with command) from database to send to the device with the given address
     */
    public List<Message> getMessagesForDevice(String address) {
        DataSource dataSource = new DataSource(context);
        dataSource.open();
        List<Message> messages = dataSource.getAllMessages();
        return messages;
    }

    /**
     * store message (just text) from user input
     */
    public void sendMessage(String message, String to) {
        String from = "from";
        String timestamp = "";
        Message m = new Message(from, to, message, timestamp);
        sendToGui(m);
        storeMessage(m);
    }

    /**
     * should we connect to the device with the given address?
     */
    public boolean readyToConnect(String address) {
        return true;
    }

    /**
     * takes a message as a string and builds a message command
     */
    public String buildMessageCmd(Message message) {
        JSONObject object = new JSONObject();
        try {
            object.put("cmd", CMD_MESSAGE);
            object.put("message", message.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    /**
     * takes a message as a string and builds a message command
     */
    public String buildNoMoreToSendCmd() {
        JSONObject object = new JSONObject();
        try {
            object.put("cmd", CMD_NOMORETOSEND);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    /**
     * decide what to do with message (in transport format) from the given address
     */
    private void processMessageCmd(JSONObject message, String address) {
        Message m = new Message(message);

        // ToDo lookup if we are the addressed person
        sendToGui(m);

        storeMessage(m);

    }

    private void sendToGui(Message message) {
        String str = message.getFrom() + ": " + message.getMessage();
        handler.obtainMessage(Constants.MESSAGE_READ, str.length(), -1, str.getBytes())
                .sendToTarget();
    }

    /**
     * store message in database
     */
    private void storeMessage(Message message) {
        DataSource dataSource = new DataSource(context);
        dataSource.open();
        dataSource.createMessage(message);
        dataSource.close();
        Log.e(TAG, "Message: " + message.getMessage() + " from " + message.getFrom() + " to " + message.getTo());
    }


}

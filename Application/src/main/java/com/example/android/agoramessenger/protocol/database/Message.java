package com.example.android.agoramessenger.protocol.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xx on 21.12.15.
 */
public class Message {
    private long id;
    private String to;
    private String from;
    private String message;
    private String timestamp;

    public Message() {

    }
    public Message(String from, String to, String message, String timestamp) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Message(String json) {
//        try {
//            JSONObject mainObject = new JSONObject(json);
//            this.to = mainObject.getString("to");
//            this.from = mainObject.getString("from");
//            this.message = mainObject.getString("message");
//            this.timestamp = mainObject.getString("timestamp");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public Message(JSONObject mainObject) {
        try {
            this.to = mainObject.getString("to");
            this.from = mainObject.getString("from");
            this.message = mainObject.getString("message");
            this.timestamp = mainObject.getString("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setTo(String to) {

        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {

        return timestamp;
    }

    public String getMessage() {

        return message;
    }

    public String getFrom() {

        return from;
    }

    public String getTo() {

        return to;
    }

    // ToDo implement hash value for message

//    public String toTransportFormat() {
//        JSONObject object = new JSONObject();
//        try {
//            object.put("from", from);
//            object.put("to", to);
//            object.put("message", message);
//            object.put("timestamp", timestamp);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return object.toString();
//    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("from", from);
            object.put("to", to);
            object.put("message", message);
            object.put("timestamp", timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}

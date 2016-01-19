package com.example.android.agoramessenger.protocol.database;

/**
 * Created by xx on 22.12.15.
 */
public class ConnectedDevice {
    private long id;
    private String address;
    private String timestamp;

    public ConnectedDevice() {
    }

    public ConnectedDevice(long id, String address, String timestamp) {
        this.id = id;
        this.address = address;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

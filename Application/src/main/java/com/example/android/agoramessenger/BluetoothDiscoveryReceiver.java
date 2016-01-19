package com.example.android.agoramessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.android.agoramessenger.protocol.ProtocolHelper;
import com.example.android.common.logger.Log;

/**
 * Created by xx on 21.12.15.
 */
public class BluetoothDiscoveryReceiver extends BroadcastReceiver {
    private BluetoothAdapter mBtAdapter;
    private static final String TAG = "BluetoothDiscoveryReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.d(TAG, "Name: " + device.getName() + ", Address:" + device.getAddress());
            Toast.makeText(context, "Name: " + device.getName() + ", Address:" + device.getAddress(), Toast.LENGTH_LONG).show();

            // ask database if we should connect to device
            ProtocolHelper protocolHelper = new ProtocolHelper(context, null);
            if(protocolHelper.readyToConnect(device.getAddress())){
                BluetoothDevice btDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
                // Attempt to connect to the device
                MyBluetoothChatService chatService = new MyBluetoothChatService(context, null);
                chatService.connect(btDevice, false);

            }

            // When discovery is finished
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Toast.makeText(context, "Discovering bluetooth devices finished!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "discovery finished!");

            // ToDo pause for x seconds after discovery
//            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
//            // Make sure we're not doing discovery anymore
//            if (mBtAdapter != null) {
//                mBtAdapter.cancelDiscovery();
//            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            // restart discovering
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            if(!mBtAdapter.isDiscovering()) {
                mBtAdapter.startDiscovery();
            }

        }
    }
}

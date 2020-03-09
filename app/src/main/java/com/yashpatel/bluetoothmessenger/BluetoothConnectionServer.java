package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothConnectionServer extends Thread {

    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothServerSocket socket;
    private BluetoothConnectionIO bConnection;

    public List<BluetoothDevice> nearbyDevices = new ArrayList<>();

    public BluetoothConnectionServer(boolean secureType) {

        try {
            if (secureType) {
                socket = bAdapter.listenUsingRfcommWithServiceRecord(bAdapter.getName(), (UUID.fromString("10382677-585d-45c9-996c-d47837a98ca9")));
            } else {
                socket = bAdapter.listenUsingInsecureRfcommWithServiceRecord(bAdapter.getName(), UUID.fromString("10382677-585d-45c9-996c-d47837a98ca9"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        setName("ServerAccept");

        BluetoothSocket bSocket = null;

        while (true) {
            try {
                bSocket = socket.accept();
                bConnection = new BluetoothConnectionIO(bSocket, nearbyDevices);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (bSocket != null) {
                bConnection.read();
            }
        }
    }

    public void cancel(){
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

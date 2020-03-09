package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionClient extends Thread {

    private BluetoothDevice bDevice;
    private BluetoothSocket socket;
    private BluetoothConnectionIO bConnection;

    public BluetoothConnectionClient(BluetoothDevice device) throws IOException {
        bDevice = device;
        try {
            socket = bDevice.createRfcommSocketToServiceRecord((UUID.fromString("10382677-585d-45c9-996c-d47837a98ca9")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        bConnection = new BluetoothConnectionIO(socket);
    }

    public void run() {

        try {
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            try{
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

    }

    public void write(UserMessage message){
        bConnection.write(message);
    }

    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

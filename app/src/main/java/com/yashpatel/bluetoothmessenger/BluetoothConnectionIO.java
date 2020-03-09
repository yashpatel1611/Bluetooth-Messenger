package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BluetoothConnectionIO extends Thread {

    //Create a bluetooth sockect object
    BluetoothSocket bSocket;

    //Input and output streams allow for information to be sent over bluetooth
    //By using object input and output streams, a whole message object can be sent with all the relevant data

    //Create an inputstream object alongside an objectInputStream
    private InputStream inputStream;
    ObjectInputStream ois;

    //Create an outputstream object alongside an objectOutputStream
    private OutputStream outputStream;
    ObjectOutputStream oos;

    //Create list for nearbyDevices, needed for resending messages back
    private List<BluetoothDevice> nearbyDevices;

    //Constructor -> takes in socket and devices list
    BluetoothConnectionIO(BluetoothSocket socket, List<BluetoothDevice> devices) throws IOException {
        bSocket = socket;
        nearbyDevices = devices;
    }

    //Overloading constructor for client side functions
    BluetoothConnectionIO(BluetoothSocket socket) throws IOException {
        bSocket = socket;
        ois = new ObjectInputStream(inputStream);
        oos = new ObjectOutputStream(outputStream);
    }

    //Read function, will take in information from stream
    public UserMessage read(){
        UserMessage userMessage = null; //Create null object of UserMessage type

        //The block of code below takes the inputstream from the socket and extracts the object from it
        //The header on the object is then checked
        try{
            inputStream = bSocket.getInputStream();
            ois = new ObjectInputStream(inputStream);
            userMessage = (UserMessage) ois.readObject();

            //If the message is only intended for nearby devices
            if(userMessage.getHeader() != "nearby"){
                for(int i = 0; i < nearbyDevices.size(); i++){
                    write(userMessage);
                }
            }

            if(userMessage.getHeader() == "nearby"){

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userMessage;
    }

    public void write(UserMessage userMessage){
        try{
            outputStream = bSocket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(userMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

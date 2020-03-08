package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class BluetoothManager {

    private TabPagerAdapter tabPagerAdapter; //TabPagerAdapter to access method to update variables in fragments
    private Context mainActivityContext; //Context object to allow for registration of broadcast receiver to activity
    private List<BluetoothDevice> nearbyDevices; //List object to hold bluetooth address of nearby devices
    private BluetoothAdapter bAdapter; //Bluetooth adapter object to allow the discovery for nearby devices
    private BroadcastReceiver discoveryReceiver;
    private BroadcastReceiver bReceiver;

    //Constructor which takes in Context (in this case, the mainActivity class) and TabPagerAdapter (so that a new one is not needed)
    BluetoothManager(Context context, TabPagerAdapter adapter) {
        mainActivityContext = context;
        tabPagerAdapter = adapter;
        bluetoothIntialisation();
    }

    //private function, returns nothing, called bluetoothInitialisation --> Will assign the adapter to the bluetooth variable
    private void bluetoothIntialisation() {

        bAdapter = BluetoothAdapter.getDefaultAdapter(); //Assign the BluetoothAdapter object -> This will allow access to bluetooth module

        if (bAdapter == null) { //If the line above returns a null object i.e. no bluetooth module exists
            Toast.makeText(mainActivityContext, "Error: No Bluetooth module found on device", Toast.LENGTH_LONG).show(); //Show a popup toast which will tell the user it is incompatible

        }

        if (!bAdapter.isEnabled()) { //If the bluetooth module is not enabled i.e. the bluetooth is toggled off
            bAdapter.enable();
        }

    }

    //Function, returns nothing, called getNearbyDevices --> goal is to populate an array with bluetooth devices
    void getNearbyDevices() {

        //Create an empty list
        nearbyDevices = new ArrayList<>();

        //Get the default adapter from the BluetoothAdapter class
        bAdapter = BluetoothAdapter.getDefaultAdapter();

        //Create new filter, this is needed for starting the discovery again after it finishes
        IntentFilter discoveryFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        //Initialise filter for the finding for Bluetooth devices
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        //Clear the nearbyDevices --> useful to remove devices no longer in range
        nearbyDevices.clear();

        //Assign a function to broadcast receiver --> will override the current one
        //Get the action from the intent as a string variable
        //The if statement checks if the action is a bluetooth device found
        //It then gets the bluetooth device from the intent and assigns it to a variable
        //If the nearbyDevices list does not contain the device just discovered
        //Add the bluetooth device to the nearbyDevices list
        bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Get the action from the intent as a string variable
                String action = intent.getAction();
                //The if statement checks if the action is a bluetooth device found
                //It then gets the bluetooth device from the intent and assigns it to a variable
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    //If the nearbyDevices list does not contain the device just discovered
                    //Add the bluetooth device to the nearbyDevices list and update the device list in the tabPagerAdapter
                    if (!nearbyDevices.contains(bDevice)) {
                        nearbyDevices.add(bDevice);
                        tabPagerAdapter.updateDeviceList(nearbyDevices);
                    }
                }

            }
        };

        //This is a new receiver which will receive a broadcast when bluetooth discover has finished
        discoveryReceiver = new BroadcastReceiver() {
            //Function below is called when the discovery times out
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //If the adapter is in a state where discovery has finished
                if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //Clear the nearbyDevices list (to prevent out of range devices to remain)
                    nearbyDevices.clear();
                    //Update the nearbyDevices list variables in the TabPagerAdapter class
                    tabPagerAdapter.updateDeviceList(nearbyDevices);
                    //Start the discovery again
                    bAdapter.startDiscovery();
                }
            }
        };

        //Register the receiver and the filter for the discovery using the activity
        mainActivityContext.getApplicationContext().registerReceiver(discoveryReceiver, discoveryFilter);

        //Register the receiver and the filter using the activity
        mainActivityContext.getApplicationContext().registerReceiver(bReceiver, bluetoothFilter);

        //Start the discover of bluetooth devices
        bAdapter.startDiscovery();

    }

    //Function called when MainActivity is destroyed -> cleans up class and finishes jobs
    void onDestroy() {
        //Unregister the discoveryReceiver
        mainActivityContext.getApplicationContext().unregisterReceiver(discoveryReceiver);
        //Unregister the bluetooth receiver
        mainActivityContext.getApplicationContext().unregisterReceiver(bReceiver);
        //Stop the discovery
        bAdapter.cancelDiscovery();
    }
}

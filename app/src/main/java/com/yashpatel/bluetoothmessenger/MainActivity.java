package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

//This is the main activity -> It will handle the layouts and the functions for the main screen
public class MainActivity extends AppCompatActivity {

    // Create bluetooth adapter variable -> not initialised for now
    BluetoothAdapter bAdapter;
    BroadcastReceiver bReceiver; //This is a broadcast receiver -> allows a function to run when an action occurs
    IntentFilter filter; //Filter helps to register the broadcast receiver for a specific intent
    List<BluetoothDevice> nearbyDevices; //List to hold all the nearby bluetooth devices

    //This function will allow me to add a 'Kebab' menu to the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the screen view to lay out the main screen activity resource file
        setContentView(R.layout.activity_main_screen);

        //Call the bluetooth initialisation function
        bluetoothIntialisation();

        //Find the toolbar view using the id and assign it to an object of type 'Toolbar'
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar above to serve as the action bar for the activity
        setSupportActionBar(toolbar);
        //Get the support bar that was just assigned and set the title as 'BlueChat'
        //getSupportActionBar was used because it ensures that if the toolbar is not set then an error will not
        //be thrown
        getSupportActionBar().setTitle("BlueChat");

        //Find the viewpager object on the activity
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager); //Call setupViewPager function - below
        TabLayout tabLayout = findViewById(R.id.tabLayout); //Find and assign the tabLayout view to a TabLayout object
        tabLayout.setupWithViewPager(viewPager); //Setup the tabLayout with the view pager

        //Initialise the nearbyDevices list
        nearbyDevices = new ArrayList<>();

        //Initialise filter for the finding for Bluetooth devices
        filter = new IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND);

        //Run the function -> its purpose is to populate nearbyDevices with nearbyDevices
        getNearbyDevices();


    }

    //Private function, returns nothing, called getNearbyDevices --> goal is to populate an array with bluetooth devices
    private void getNearbyDevices() {
        //Clear the nearbyDevices --> useful to remove devices no longer in range
        nearbyDevices.clear();
        //Assign a function to broadcast receiver --> will override the current one
        bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Get the action from the intent as a string variable
                String action = intent.getAction();
                //The if statement checks if the action is a bluetooth device found
                //It then gets the bluetooth device from the intent and assigns it to a variable
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice bDevice = intent.getParcelableExtra(android.bluetooth.BluetoothDevice.EXTRA_DEVICE);

                    //If the nearbyDevices list does not contain the device just discovered
                    //Add the bluetooth device to the nearbyDevices list
                    if (!nearbyDevices.contains(bDevice)) {
                        nearbyDevices.add(bDevice);
                    }
                    Log.v("Device", bDevice.getAddress());
                }


                if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    bAdapter.startDiscovery();
                    Log.v("S", "started discover");
                }

            }
        };
        //Register the receiver and the filter using the activity
        registerReceiver(bReceiver, filter);
        //Start the discover of bluetooth devices -> has an interval of 12 seconds
        bAdapter.startDiscovery();
    }


    private void setupViewPager(ViewPager viewPager) {
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager()); //Create instance of TabPagerAdapter class
        viewPager.setAdapter(tabPagerAdapter); // Set the viewPager to work with that tabPagerAdapter

    }

    //private function, returns nothing, called bluetoothInitialisation --> Will assign the adapter to the bluetooth variable
    private void bluetoothIntialisation() {

        bAdapter = BluetoothAdapter.getDefaultAdapter(); //Assign the BluetoothAdapter object -> This will allow access to bluetooth module

        if (bAdapter == null) { //If the line above returns a null object i.e. no bluetooth module exists
            Toast.makeText(this, "Error: No Bluetooth module found on device", Toast.LENGTH_LONG).show(); //Show a popup toast which will tell the user it is incompatible

        }

        if (!bAdapter.isEnabled()) { //If the bluetooth module is not enabled i.e. the bluetooth is toggled off
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Make an intent to ask the user to turn on bluetooth
            startActivityForResult(bluetoothIntent, 0); //Start the intent -> Will cause a popup to ask user to press button to turn on bluetooth
        }

    }

    //Function that will be called when the activity is destroyed
    @Override
    protected void onDestroy() {
        //Unregister the broadcastReceiver
        this.unregisterReceiver(bReceiver);
        //Stop the discovery for bluetooth devices
        bAdapter.cancelDiscovery();
        //Call the destroy function for the super class
        super.onDestroy();
    }
}



package com.yashpatel.bluetoothmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

//This is the main activity -> It will handle the layouts and the functions for the main screen
public class MainActivity extends AppCompatActivity {

    //TabPagerAdapter class object
    public TabPagerAdapter tabPagerAdapter;
    //BluetoothManager class object
    BluetoothManager bManager;

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

        //Create a new BluetoothManager class instance and pass into it itself (the activity) and the tabPagerAdapter
        bManager = new BluetoothManager(this, tabPagerAdapter);

        //Start the discovery for nearby devices
        bManager.getNearbyDevices();



    }


    public void setupViewPager(ViewPager viewPager) {
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager()); //Create instance of TabPagerAdapter class
        viewPager.setAdapter(tabPagerAdapter); // Set the viewPager to work with that tabPagerAdapter

    }


    //Function that will be called when the activity is destroyed
    @Override
    protected void onDestroy() {
        //Start the onDestroy function for the BluetoothManager
        bManager.onDestroy();
        //Call the destroy function for the super class
        super.onDestroy();
    }
}



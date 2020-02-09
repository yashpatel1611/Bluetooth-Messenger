package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    // Create bluetooth adapter variable -> not initialised for now
    BluetoothAdapter bAdapter;

    // Allows the 3-dot menu to be shown
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BlueChat");

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        bluetoothIntialisation();
        List<String> nearbyDevices = new ArrayList<>(getNearbyDevicesList());



    }

    private List<String> getNearbyDevicesList() {
        List<BluetoothDevice> nearbyDevices = new ArrayList<>(bAdapter.getBondedDevices());
        List<String> nearbyDevicesList = new ArrayList<>();
        for (int i = 0; i < nearbyDevices.size(); i++) {
            nearbyDevicesList.add(nearbyDevices.get(i).getName());
        }
        return nearbyDevicesList;


    }


    private void bluetoothIntialisation() {

        bAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bAdapter == null) {
            Toast.makeText(this, "Error: No Bluetooth module found on device", Toast.LENGTH_LONG).show();
            this.finish();

        }

        if (!bAdapter.isEnabled()) {
            bAdapter.enable();
        }

    }
}

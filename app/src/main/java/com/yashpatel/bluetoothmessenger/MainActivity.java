package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bluetoothIntialisation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BlueChat");

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);


    }

    private void bluetoothIntialisation() {

        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bAdapter == null) {
            Toast.makeText(this, "Error: No Bluetooth module found on device", Toast.LENGTH_LONG).show();

        }

        if (!bAdapter.isEnabled()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, 0);
        }

    }
}

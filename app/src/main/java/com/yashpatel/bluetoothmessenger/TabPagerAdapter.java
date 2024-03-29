package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//Created class called TabPagerAdepter which implements the functions from FragmentPagerAdapter
//The class FragmentPagerAdapter is used to persistently keep the fragment manager as long as the user can return to it
//This is useful as the fragments have to always be available to be used by the user
//The functions getItem() and getCount() are part of the FragmentPagerAdapter class
public class TabPagerAdapter extends FragmentPagerAdapter {
    RecyclerViewAdapter everyoneRecyclerAdapter;
    RecyclerViewAdapter nearbyRecyclerAdapter;

    Fragment everyone = new everyone_fragment();
    Fragment chats = new chats_fragment();
    Fragment contacts = new contacts_fragment();
    Fragment nearby = new nearby_fragment();

    //Constructor for class, sends the incoming fragment manager to the super class
    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        everyoneRecyclerAdapter = new RecyclerViewAdapter(context);

    }

    //This function is used to get the fragment necessary. It will return the activity based on the position of the view pager
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null; //Create a Fragment object instance which is null to allow it to be assigned when needed

        //The use of the switch case allows for cleaner and more readable code compared to an if statement
        //Here, the variable fragment will be assigned a different fragment depending on the value 'position'
        switch (position) {
            case 0:
                fragment = everyone; //Set fragment to the everyone_fragment fragment
                break;
            case 1:
                fragment = nearby; //Set fragment to the nearby_fragment fragment
                break;
            case 2:
                fragment = chats; //Set fragment to the chats_fragment
                break;
            case 3:
                fragment = contacts; //Set fragment to the contacts_fragment
                break;
        }

        //Return the fragment
        return fragment;

    }

    //This is a function which will take in the deviceList passed in from BluetoothManager and update the fragments' respective device lists
    public void updateDeviceList(List<BluetoothDevice> deviceList) {
        ((everyone_fragment) everyone).updateNearbyDevices(deviceList);
        ((nearby_fragment) nearby).updateNearbyDevices(deviceList);

    }


    //This function returns the number of available fragments. This is useful as it will prevent the
    //user from swiping more than they can.
    @Override
    public int getCount() {
        return 4;
    }

    //This function is used to return the page title of the fragment -> it is consistent with the getFragment method in terms of names
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence pageTitle = null; //Set pageTitle to null

        //Used a switch statement rather than if to make code readable and easier to debug
        switch (position) {
            case 0:
                pageTitle = "Everyone"; //Set page title to 'Everyone'
                break;
            case 1:
                pageTitle = "Nearby"; //Set page title to 'Nearby'
                break;
            case 2:
                pageTitle = "Chats"; //Set page title to 'Chats'
                break;
            case 3:
                pageTitle = "Contacts"; //Set page title to 'Contacts'
                break;
        }
        //Return the page title
        return pageTitle;
    }
}

package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//Class which will hold all the methods required for the 'chats' tab fragment
public class chats_fragment extends Fragment {

    private List<BluetoothDevice> nearbyDevices = new ArrayList<>();

    //Constructor for fragment -> currently, empty
    public chats_fragment() {

    }

    //Method which will return the view associated with the fragment activity
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chats, container, false); //Creates View object called fragmentView, which holds the required fragment
        return fragmentView; //Return it to the class it was called from
    }

    //Method which will be called when fragment is started up
    @Override
    public void onStart() {
        super.onStart();
    }

    //Method called when the fragment has been opened after the first time
    @Override
    public void onResume() {
        super.onResume();
    }

}

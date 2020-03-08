package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//Class which will hold all the methods required for the 'everyone' tab fragment
public class everyone_fragment extends Fragment {

    //Nearby devices list, which will be populated from the MainActivity to the FragmentPagerAdapter to here

    private List<BluetoothDevice> nearbyDevices = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    //Constructor for fragment -> currently, empty
    public everyone_fragment() {
    }

    //Method which will return the view associated with the fragment activity
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_everyone, container, false); //Creates View object called fragmentView, which holds the required fragment

        RecyclerView recyclerView = fragmentView.findViewById(R.id.recyclerView_everyone); //Get the recycler view from the fragment and assign it to the RecyclerView object
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), nearbyDevices); //Create an instance of RecyclerViewAdapter -> pass in the current context and dummy data (for now)

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //Set a layout manager, needed for positioning and setting the items in the recycler view
        recyclerView.setAdapter(recyclerViewAdapter); //Set the adapter to the view -> an adapter is a class which handles all the data and updates the view as necessary

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

    //Function, returns nothing and takes in List of type BluetoothDevices
    //Assigns the incoming argument into nearbyDevices list initialised above
    //Updates the data in recyclerViewAdapter as well -> This is done for debugging purposes as the dummy data is nearbyDevices
    void updateNearbyDevices(List<BluetoothDevice> devices) {
        nearbyDevices = devices;
        recyclerViewAdapter.data = devices;
        recyclerViewAdapter.notifyDataSetChanged();

    }

}




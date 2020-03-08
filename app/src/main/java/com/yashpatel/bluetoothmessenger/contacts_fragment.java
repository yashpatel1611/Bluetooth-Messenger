package com.yashpatel.bluetoothmessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


//Class which will hold all the methods required for the 'contacts' tab fragment
public class contacts_fragment extends Fragment {

    //Constructor for fragment -> currently, empty
    public contacts_fragment() {

    }

    //Method which will return the view associated with the fragment activity
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_contacts, container, false); //Creates View object called fragmentView, which holds the required fragment
        return fragmentView; //Return it to the class it was called from
    }
}

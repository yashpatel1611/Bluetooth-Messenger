package com.yashpatel.bluetoothmessenger;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    //Tags needed so that the correct layout is shown for the message
    private final int VIEW_MESSAGE_RECEIVED = 0;
    private final int VIEW_MESSAGE_SENT = 1;
    List<BluetoothDevice> data; //Bluetooth list called data. This is temporary and will be adjusted once bluetooth messenging works
    private LayoutInflater inflater; //Layout inflater object, helps setup the layout file
    private Context mContext; //Context object, needed to get inflater from main activity

    //Constructor for class -> Takes in context (main activity) and a data attribute (uses devices list for debugging)
    RecyclerViewAdapter(Context context, List<BluetoothDevice> devices) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        data = devices;
    }

    //Method onCreateViewHolder -> called when the recycler view needs a new holder for a data item
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View object, keep it null as it will be initialised in the if statements
        View view = null;

        //If the message is one that the device has received
        if (viewType == VIEW_MESSAGE_RECEIVED) {
        }
        //If the message is one that the device has sent
        else if (viewType == VIEW_MESSAGE_SENT) {
            //Get the view using the inflater and inflating the message_sent layout file
            view = inflater.inflate(R.layout.message_sent, parent, false);
            return new messageSentHolder(view);
        }

        //These two lines below will be in the first if clause of the if statement
        //They are here for debugging purposes -> allows me to see if they recycler view works properly
        view = inflater.inflate(R.layout.message_received, parent, false);
        return new messageReceivedHolder(view);
    }

    //This a function that is called by the recycler view when data needs to be displayed at a specific position
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BluetoothDevice device = data.get(position);
        ((messageReceivedHolder) holder).bind(device);

    }

    //This is a function which returns the size of the data intended to be displayed
    @Override
    public int getItemCount() {
        return data.size();
    }

    //Nested class (or inner class) for setting up views with the data provided
    public class messageReceivedHolder extends RecyclerView.ViewHolder {
        TextView message, senderName, timeSent; //Creates 3 variables of type TextView -> message holds message,
                                                // senderName holds the name of the sender and timeSent holds the timestamp

        //Constructor for inner class -> takes in View object
        public messageReceivedHolder(@NonNull View itemView) {
            super(itemView);
            //Retrieve the relevant views from the xml file
            message = itemView.findViewById(R.id.text_message_body_received);
            senderName = itemView.findViewById(R.id.text_message_name_received);
            timeSent = itemView.findViewById(R.id.text_message_time_received);
        }

        //This is a bind function, it will essentially populate the view with the relevant data
        //Currently it takes in dummy data (which I have chosen to be the nearby devices list)
        //The function will set the textviews to the required information
        void bind(BluetoothDevice device) {
            message.setText(device.getAddress());
            senderName.setText("TestName");
            timeSent.setText("25:25");
        }

    }

    //Nested class for setting up views for sent messages
    public class messageSentHolder extends RecyclerView.ViewHolder {
        TextView message, timeSent; //Creates 2 variables of type TextView -> message holds message and timeSent holds the timestamp of message

        //Constructor for the class -> takes in a View object
        public messageSentHolder(@NonNull View itemView) {
            super(itemView);
            //Get the relevant text views from the layout xml file and assign them to the text view objects
            message = itemView.findViewById(R.id.text_message_body_sent);
            timeSent = itemView.findViewById(R.id.text_message_time_sent);
        }

        //This is a bind function, it populates the view with the relevant data
        //Takes in dummy data for now as I have not developed the message holder yet
        //Will set text views with relevant information
        void bind(BluetoothDevice device){
            message.setText(device.getAddress());
            timeSent.setText("25:25");
        }

    }


}

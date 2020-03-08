package com.yashpatel.bluetoothmessenger;

import java.util.Date;

public class UserMessage {

    private String mMessage;
    private String mSender;
    private Date mDate;

    UserMessage(String message, String sender, Date date){
        mMessage = message;
        mSender = sender;
        mDate = date;

    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmSender() {
        return mSender;
    }
}

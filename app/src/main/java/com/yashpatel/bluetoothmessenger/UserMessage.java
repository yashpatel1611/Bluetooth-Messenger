package com.yashpatel.bluetoothmessenger;

public class UserMessage {

    private String mHeader;
    private String mMessage;
    private String mSender;
    private long mDate;

    UserMessage(String header, String message, String sender, long date) {
        mHeader = header;
        mMessage = message;
        mSender = sender;
        mDate = date;

    }


    public String getHeader() {
        return mHeader;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getSender() {
        return mSender;
    }

    public long getDate() {
        return mDate;
    }
}

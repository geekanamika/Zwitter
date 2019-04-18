package com.example.zwitter.data.models;

public class Message {
    private String message;
    private String timeStamp;
    private String senderId;

    // needed for firebase
    public Message() {
    }

    public Message(String message, String timeStamp, String senderId) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}

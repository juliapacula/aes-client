package com.encrypted.chat.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    public String message;
    public MessageOwner owner;
    public MessageType type;
    public String timestamp;

    public Message(String message, MessageOwner owner) {
        this.message = message;
        this.owner = owner;
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        timestamp = simpleDateFormat.format(new Date());
    }

    public Message(String message, MessageOwner owner, MessageType type) {
        this.message = message;
        this.owner = owner;
        this.type = type;
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        timestamp = simpleDateFormat.format(new Date());
    }
}

package com.encrypted.chat.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    public String message;
    public MessageType type;
    public String timestamp;

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        timestamp = simpleDateFormat.format(new Date());
    }
}

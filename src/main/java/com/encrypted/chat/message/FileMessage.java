package com.encrypted.chat.message;

public class FileMessage extends Message {
    public String filePath;

    public FileMessage(String fileName, String path, MessageOwner owner) {
        super(fileName, owner, MessageType.FILE);
        filePath = path;
    }
}

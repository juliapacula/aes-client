package com.encrypted.chat.communication;

public class ExternalFileMessage extends ExternalMessage {
    public byte[] fileName;

    ExternalFileMessage(byte[] fileName, byte[] file, byte[] iv) {
        super(ExternalMessageType.FILE_MESSAGE, file, iv);
        this.fileName = fileName;
    }
}

package com.encrypted.chat.communication;

import java.io.Serializable;

public class ExternalMessage implements Serializable {
    public ExternalMessageType type;
    public byte[] iv;
    public byte[] content;

    public ExternalMessage(ExternalMessageType type, byte[] content, byte[] iv) {
        this.type = type;
        this.content = content;
        this.iv = iv;
    }

    public ExternalMessage(ExternalMessageType type, byte[] content) {
        this.type = type;
        this.content = content;
        this.iv = null;
    }
}

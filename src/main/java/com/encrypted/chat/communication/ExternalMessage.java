package com.encrypted.chat.communication;

import java.io.Serializable;

public class ExternalMessage implements Serializable {
    public ExternalMessageType type;
    public byte[] content;

    public ExternalMessage(ExternalMessageType type, byte[] content) {
        this.type = type;
        this.content = content;
    }
}

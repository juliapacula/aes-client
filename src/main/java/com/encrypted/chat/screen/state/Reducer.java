package com.encrypted.chat.screen.state;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageType;

public class Reducer {
    private Store store;

    public Reducer(Store store) {
        this.store = store;
        addSelfMessage("Test!");
    }

    public void addSelfMessage(String text) {
        Message message = new Message(text, MessageType.OWNER);
        store.addMessage(message);
    }

    public void addIncomingMessage(String text) {
        Message message = new Message(text, MessageType.INCOMING);
        store.addMessage(message);
    }

    public void connectToClient() {
        store.setConnected();
    }
}

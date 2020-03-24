package com.encrypted.chat.screen;

import com.encrypted.chat.message.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Store {
    private ObservableList<Message> messages;

    public Store() {
        messages = FXCollections.observableArrayList();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }
}

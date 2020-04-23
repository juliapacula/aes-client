package com.encrypted.chat.screen.state;

import com.encrypted.chat.message.Message;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Store {
    private ObservableList<Message> messages;
    private BooleanProperty connected = new SimpleBooleanProperty(false);

    public Store() {
        messages = FXCollections.observableArrayList();
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public BooleanProperty connectedProperty() {
        return connected;
    }

    public boolean isConnected() {
        return connected.get();
    }

    void setConnected() {
        this.connected.set(true);
    }

    void addMessage(Message message) {
        messages.add(message);
    }
}

package com.encrypted.chat.state;

import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.message.Message;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Store {
    private ObservableList<Message> messages;
    private BooleanProperty connected = new SimpleBooleanProperty(false);
    private StringProperty encryptionMode = new SimpleStringProperty(EncryptionMode.ECB.toString());

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

    public StringProperty encryptionModeProperty() {
        return encryptionMode;
    }

    public String getEncryptionMode() {
        return encryptionMode.get();
    }

    public void setEncryptionMode(String encryptionMode) {
        this.encryptionMode.set(encryptionMode);
    }

    void addMessage(Message message) {
        messages.add(message);
    }

    void setConnected() {
        this.connected.set(true);
    }

}

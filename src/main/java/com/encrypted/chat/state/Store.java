package com.encrypted.chat.state;

import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.message.Message;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Store implements SessionStore {
    private final ObservableList<Message> messages;
    private final BooleanProperty connected = new SimpleBooleanProperty(false);
    private final StringProperty encryptionMode = new SimpleStringProperty(EncryptionMode.ECB.toString());

    protected byte[] encryptionSessionKey = null;
    protected byte[] decryptionSessionKey = null;
    protected PublicKey receivedPublicKey;
    protected PrivateKey selfPrivateKey;

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

    public EncryptionMode getEncryptionMode() {
        return EncryptionMode.valueOf(encryptionMode.get());
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

    public byte[] getDecryptionSessionKey() {
        return decryptionSessionKey;
    }

    public byte[] getEncryptionSessionKey() {
        return encryptionSessionKey;
    }

    public PublicKey getReceivedPublicKey() {
        return receivedPublicKey;
    }

    public PrivateKey getSelfPrivateKey() {
        return selfPrivateKey;
    }
}

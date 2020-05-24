package com.encrypted.chat.screen;

import com.encrypted.chat.communication.*;
import com.encrypted.chat.encryption.AES;
import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.encryption.IvSpecProvider;
import com.encrypted.chat.encryption.RsaKeyGetter;
import com.encrypted.chat.state.Reducer;
import com.encrypted.chat.state.Store;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.stage.Stage;

import java.io.File;
import java.security.KeyPair;

public class Presenter {
    private final ConnectionManager connectionManager;
    private final Reducer reducer;
    private final Store store;
    private Router router;

    public Presenter(Stage primaryStage) {
        router = new Router(primaryStage, this);
        store = new Store();
        reducer = new Reducer(store);
        connectionManager = new ConnectionManager(reducer, store);

        initialActions();
    }

    public void connectToClient(String clientIp, String userPassword) {
        connectionManager.connectToClient(clientIp, userPassword);
        router.showMessagingScreen();
    }

    public ReadOnlyDoubleProperty sendMessage(String message) {
        byte[] iv = IvSpecProvider.getInitialVector(null).getIV();
        byte[] content = AES.encrypt(message.getBytes(),
                store.getEncryptionSessionKey(),
                store.getEncryptionMode(),
                IvSpecProvider.getInitialVector(iv));
        ExternalMessage externalMessage = new ExternalMessage(ExternalMessageType.TEXT_MESSAGE, content, iv);

        reducer.addSelfMessage(message);
        return connectionManager.sendMessage(externalMessage);
    }

    public ReadOnlyDoubleProperty sendFile(File file) {
        reducer.addSelfFileMessage(file);
        return connectionManager.sendFile(file);
    }

    public void changeEncryptionMode(EncryptionMode mode) {
        ExternalMessage externalMessage = new ExternalMessage(ExternalMessageType.ENCRYPTION_TYPE, mode.toString().getBytes());
        connectionManager.sendMessage(externalMessage);
        reducer.changeEncryptionMode(mode);
    }

    public void exit() {
        Platform.exit();
    }

    public Reducer getReducer() {
        return reducer;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public Store getStore() {
        return store;
    }

    private void initialActions() {
        connectionManager.listenForMessages();
    }
}

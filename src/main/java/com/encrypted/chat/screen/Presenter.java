package com.encrypted.chat.screen;

import com.encrypted.chat.communication.ConnectionManager;
import com.encrypted.chat.communication.ExternalMessage;
import com.encrypted.chat.communication.ExternalMessageType;
import com.encrypted.chat.state.Reducer;
import com.encrypted.chat.state.Store;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Presenter {
    private ConnectionManager connectionManager;
    private Reducer reducer;
    private Store store;
    private Router router;

    public Presenter(Stage primaryStage) {
        router = new Router(primaryStage, this);
        store = new Store();
        reducer = new Reducer(store);
        connectionManager = new ConnectionManager(reducer, router);

        initialActions();
    }

    public void connectToClient(String clientIp) {
        connectionManager.connectToClient(clientIp);
    }

    public void sendMessage(String message) {
        ExternalMessage externalMessage = new ExternalMessage(ExternalMessageType.TEXT_MESSAGE, message.getBytes());
        connectionManager.sendMessage(externalMessage);
        reducer.addSelfMessage(message);
    }

    public void exit() {
        Platform.exit();
    }

    public Reducer getReducer() {
        return reducer;
    }

    public Store getStore() {
        return store;
    }

    private void initialActions() {
        connectionManager.listenForMessages(reducer);
    }
}

package com.encrypted.chat.screen;

import com.encrypted.chat.communication.ConnectionManager;
import com.encrypted.chat.communication.ExternalMessage;
import com.encrypted.chat.communication.ExternalMessageType;
import com.encrypted.chat.message.Message;
import com.encrypted.chat.screen.state.Reducer;
import com.encrypted.chat.screen.state.Store;
import javafx.stage.Stage;

import java.io.IOException;

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
        try {
            connectionManager.connectToClient(clientIp);
            router.showMessagingScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        ExternalMessage externalMessage = new ExternalMessage(ExternalMessageType.TEXT_MESSAGE, message.getBytes());
        connectionManager.sendMessage(externalMessage);
        reducer.addSelfMessage(message);
    }

    public Reducer getReducer() {
        return reducer;
    }

    public Store getStore() {
        return store;
    }

    private void initialActions() {
        connectionManager.listenForConnections(router);
        connectionManager.listenForMessages(reducer);
    }
}

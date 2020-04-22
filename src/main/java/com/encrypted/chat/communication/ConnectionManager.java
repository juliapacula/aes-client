package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import com.encrypted.chat.screen.Router;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {
    private Socket connectedClient;

    public void connectToClient(String ip) throws IOException {
        connectedClient = new Socket(ip, 4040);
    }

    public void listenForConnections(Router router) {
        ReceiveConnectedSocketService receiveMessageService = new ReceiveConnectedSocketService(Main.isDev ? 1234 : 4040);
        receiveMessageService.setOnSucceeded(event -> {
            connectedClient = (Socket) event.getSource().getValue();
            router.showMessagingScreen();
        });
        receiveMessageService.start();
    }
}

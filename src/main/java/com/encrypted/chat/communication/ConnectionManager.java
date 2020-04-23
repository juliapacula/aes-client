package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import com.encrypted.chat.screen.Router;
import com.encrypted.chat.screen.state.Reducer;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {
    private Socket connectedClient;
    private Reducer reducer;
    private Router router;
    private String ipToAddress;
    private ReceiveMessageService receiveMessageService;

    public ConnectionManager(Reducer reducer, Router router) {
        this.reducer = reducer;
        this.router = router;
    }

    public void connectToClient(String ip) throws IOException {
        connectedClient = new Socket(ip, Main.isDev ? 4040 : 1234);
        reducer.connectToClient();
        router.showMessagingScreen();
    }

    public void listenForConnections(Router router) {
        ReceiveConnectedSocketService receiveConnectedSocketService = new ReceiveConnectedSocketService(Main.isDev ? 1234 : 4040);
        receiveConnectedSocketService.setOnSucceeded(event -> {
            connectedClient = (Socket) event.getSource().getValue();
            router.showMessagingScreen();
        });
        receiveConnectedSocketService.start();
    }

    public void listenForMessages(Reducer reducer) {
        if (receiveMessageService == null) {
            receiveMessageService = new ReceiveMessageService(connectedClient);
        }

        receiveMessageService.setOnSucceeded(event -> {
            ExternalMessage message = (ExternalMessage) event.getSource().getValue();
            String messageText = new String(message.content);
            System.out.println(messageText);
            reducer.addIncomingMessage(messageText);
        });

        receiveMessageService.start();
    }

    public void stopListeningForMessages() {
        receiveMessageService.cancel();
    }

    public void sendMessage(ExternalMessage message) {
        SendMessageService sendMessageService = new SendMessageService(connectedClient);
        sendMessageService.setMessageToSend(message);
        sendMessageService.start();
    }
}

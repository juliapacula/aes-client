package com.encrypted.chat.communication;

import com.encrypted.chat.screen.Router;
import com.encrypted.chat.state.Reducer;

public class ConnectionManager {
    private Reducer reducer;
    private Router router;
    private String ipToSend;
    private ReceiveMessageService receiveMessageService;

    public ConnectionManager(Reducer reducer, Router router) {
        this.reducer = reducer;
        this.router = router;
    }

    public void connectToClient(String ip) {
        ipToSend = ip;
        reducer.connectToClient();
        router.showMessagingScreen();
    }


    public void listenForMessages(Reducer reducer) {
        if (receiveMessageService == null) {
            receiveMessageService = new ReceiveMessageService();
        }

        receiveMessageService.setOnSucceeded(event -> {
            ExternalMessage message = (ExternalMessage) event.getSource().getValue();
            String messageText = new String(message.content);
            System.out.println(messageText);
            reducer.addIncomingMessage(messageText);
        });

        receiveMessageService.start();
    }

    public void sendMessage(ExternalMessage message) {
        SendMessageService sendMessageService = new SendMessageService(ipToSend);
        sendMessageService.setMessageToSend(message);
        sendMessageService.start();
    }
}

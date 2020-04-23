package com.encrypted.chat.communication;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessageService extends Service<Void> {
    private Socket connectedSocket;
    private ExternalMessage messageToSend;

    SendMessageService(Socket socket) {
        this.connectedSocket = socket;
    }

    public void setMessageToSend(ExternalMessage message) {
        messageToSend = message;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    ObjectOutputStream out = new ObjectOutputStream(connectedSocket.getOutputStream());

                    if (messageToSend != null) {
                        System.out.println("Sent a message.");
                        out.writeObject(messageToSend);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
    }
}

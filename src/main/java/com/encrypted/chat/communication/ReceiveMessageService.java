package com.encrypted.chat.communication;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveMessageService extends ScheduledService<ExternalMessage> {
    private Socket connectedSocket;

    ReceiveMessageService(Socket socket) {
        setDelay(Duration.ZERO);
        this.connectedSocket = socket;
    }

    @Override
    protected Task<ExternalMessage> createTask() {
        return new Task<ExternalMessage>() {
            @Override
            protected ExternalMessage call() {
                ExternalMessage message = null;

                try {
                    ObjectInputStream in = new ObjectInputStream(connectedSocket.getInputStream());
                    message = (ExternalMessage)in.readObject();
                    if (message != null) {
                        System.out.println("Received a message.");
                        System.out.println(new String(message.content));
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                return message;
            }
        };
    }
}

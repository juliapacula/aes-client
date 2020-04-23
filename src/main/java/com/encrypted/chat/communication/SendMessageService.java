package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessageService extends Service<Void> {
    private String receiverIp;
    private ExternalMessage messageToSend;

    SendMessageService(String receiverIp) {
        this.receiverIp = receiverIp;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try (
                    Socket socket = new Socket(receiverIp, Main.isDev ? 4040 : 1234);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ) {
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

    void setMessageToSend(ExternalMessage message) {
        messageToSend = message;
    }
}

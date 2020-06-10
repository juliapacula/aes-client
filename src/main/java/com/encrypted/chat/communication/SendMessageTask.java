package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessageTask extends Task<Void> {
    private final String receiverIp;
    private final ExternalMessage messageToSend;

    SendMessageTask(String receiverIp, ExternalMessage message) {
        this.receiverIp = receiverIp;
        messageToSend = message;
    }

    @Override
    protected Void call() {
        try (
                Socket socket = new Socket(receiverIp, 8005);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ) {
            if (messageToSend != null) {
                System.out.println("Sent a message.");
                out.writeObject(messageToSend);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        return null;
    }
}

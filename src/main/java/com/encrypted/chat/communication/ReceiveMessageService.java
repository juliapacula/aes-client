package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveMessageService extends ScheduledService<ExternalMessage> {
    ReceiveMessageService() {
        setDelay(Duration.ONE);
    }

    @Override
    protected Task<ExternalMessage> createTask() {
        return new Task<ExternalMessage>() {
            @Override
            protected ExternalMessage call() {
                ExternalMessage message = null;

                try (
                        ServerSocket serverSocket = new ServerSocket(Main.isDev ? 1234 : 4040, 0, InetAddress.getByName("127.0.0.1"));
                        Socket connectedSocket = serverSocket.accept();
                        ObjectInputStream in = new ObjectInputStream(connectedSocket.getInputStream())
                ) {
                    message = (ExternalMessage) in.readObject();
                    if (message != null) {

                        if (message.type == ExternalMessageType.FILE_MESSAGE) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[4096];
                            int count;
                            while ((count = in.read(buffer)) > 0) {
                                byteArrayOutputStream.write(buffer, 0, count);
                            }

                            return new ExternalFileMessage(message.content, byteArrayOutputStream.toByteArray(), message.iv);
                        } else {
                            System.out.println("Received a message: " + new String(message.content));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return message;
            }
        };
    }
}

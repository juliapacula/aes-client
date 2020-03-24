package com.encrypted.chat.communication;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveConnectedSocketService extends Service<Socket> {
    private int port;

    public ReceiveConnectedSocketService(int port) {
        this.port = port;
    }

    @Override
    protected Task<Socket> createTask() {
        return new Task<Socket>() {
            @Override
            protected Socket call() throws IOException {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Listening for incoming connections.");

                return serverSocket.accept();
            }
        };
    }
}

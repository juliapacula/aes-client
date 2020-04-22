package com.encrypted.chat.screen;

import com.encrypted.chat.communication.ConnectionManager;
import com.encrypted.chat.screen.welcoming.WelcomingScreen;
import com.encrypted.chat.screen.messaging.MessagingScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Presenter implements Router {
    private Stage primaryStage;
    private ConnectionManager connectionManager;

    public Presenter(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Encrypting App");
        connectionManager = new ConnectionManager();
    }

    public void showWelcomingScreen() {
        primaryStage.setScene(new Scene(new WelcomingScreen(this), 400, 400));
        primaryStage.show();
    }

    public void connectToClient(String clientIp) {
        try {
            connectionManager.connectToClient(clientIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        showMessagingScreen();
    }

    public void startListeningForConnection() {
        connectionManager.listenForConnections(this);
    }

    public void showMessagingScreen() {
        primaryStage.setScene(new Scene(new MessagingScreen(this), 800, 600));
        primaryStage.show();
    }
}

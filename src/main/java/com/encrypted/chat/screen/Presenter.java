package com.encrypted.chat.screen;

import com.encrypted.chat.Main;
import com.encrypted.chat.communication.ReceiveConnectedSocketService;
import com.encrypted.chat.screen.loading.LoadingScreen;
import com.encrypted.chat.screen.messaging.MessagingScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;

public class Presenter {
    private Stage primaryStage;
    private Socket clientSocket;
    private ReceiveConnectedSocketService receiveMessageService;

    public Presenter(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Encrypting App");
    }

    public void showLoadingScreen() {
        primaryStage.setScene(new Scene(new LoadingScreen(this), 400, 200));
        primaryStage.show();
    }

    public void showMessagingScreen() {
        receiveMessageService.cancel();
        primaryStage.setScene(new Scene(new MessagingScreen(this), 800, 600));
        primaryStage.show();
    }

    public void startListeningForConnection() {
        receiveMessageService = new ReceiveConnectedSocketService(Main.isDev ? 1234 : 4040);
        receiveMessageService.setOnSucceeded(event -> {
            clientSocket = (Socket) event.getSource().getValue();
            showMessagingScreen();
        });
        receiveMessageService.start();
    }
}

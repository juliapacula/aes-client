package com.encrypted.chat;

import com.encrypted.chat.screen.Presenter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.Socket;

public class Main extends Application {
    public static boolean isDev = false;
    public static String localIpAddress = null;

    @Override
    public void start(Stage primaryStage) {
        new Presenter(primaryStage);
    }


    public static void main(String[] args) {
        isDev = args.length > 0;

        try (Socket socket = new Socket("google.com", 80)) {
            localIpAddress = socket.getLocalAddress().getHostAddress();
        } catch (Exception ignored) {}

        launch(args);
    }
}

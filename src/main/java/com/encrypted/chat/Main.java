package com.encrypted.chat;

import com.encrypted.chat.screen.Presenter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static boolean isDev = false;

    @Override
    public void start(Stage primaryStage) {
        Presenter presenter = new Presenter(primaryStage);
        presenter.startListeningForConnection();
        presenter.showLoadingScreen();
    }


    public static void main(String[] args) {
        isDev = args.length > 0;
        launch(args);
    }
}
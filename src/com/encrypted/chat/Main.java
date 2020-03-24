package com.encrypted.chat;

import com.encrypted.chat.screen.loading.LoadingScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Encrypting App");
        primaryStage.setScene(new Scene(new LoadingScreen(), 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

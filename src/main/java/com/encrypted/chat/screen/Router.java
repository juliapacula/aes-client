package com.encrypted.chat.screen;

import com.encrypted.chat.screen.messaging.MessagingScreen;
import com.encrypted.chat.screen.welcoming.WelcomingScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {
    private Stage welcomeStage;
    private Stage messageStage;
    private Presenter presenter;

    Router(Stage welcomeStage, Presenter presenter) {
        this.welcomeStage = welcomeStage;
        this.presenter = presenter;

        welcomeStage.setTitle("Encrypting App");
        showWelcomingScreen();
    }

    public void showMessagingScreen() {
        if (messageStage != null) {
            return;
        }

        messageStage = new Stage();
        messageStage.setScene(new Scene(new MessagingScreen(presenter), 800, 600));
        messageStage.show();
    }

    public void showWelcomingScreen() {
        welcomeStage.setScene(new Scene(new WelcomingScreen(presenter), 400, 400));
        welcomeStage.show();
    }
}

package com.encrypted.chat.screen.messaging;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MessagingScreen extends BorderPane {
    public MessagingScreen() {
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MessagingScreen.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

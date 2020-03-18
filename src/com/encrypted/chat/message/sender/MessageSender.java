package com.encrypted.chat.message.sender;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageSender extends HBox {
    public MessageSender() {
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageSender.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

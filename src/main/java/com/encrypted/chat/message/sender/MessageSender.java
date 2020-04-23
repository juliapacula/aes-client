package com.encrypted.chat.message.sender;

import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageSender extends HBox {
    private Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public MessageSender() {
        loadFxml();
    }

    @FXML
    public void sendMessage() {
        this.presenter.sendMessage("Test");
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageSender.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

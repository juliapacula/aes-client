package com.encrypted.chat.message.sender;

import com.encrypted.chat.screen.Presenter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageSender extends HBox {
    @FXML
    public JFXTextField textMessage;
    @FXML
    public JFXButton sendButton;
    private Presenter presenter;

    public MessageSender() {
        loadFxml();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    public void sendMessage() {
        String messageToSend = textMessage.getText();
        this.presenter.sendMessage(messageToSend);
        textMessage.setText("");
    }

    @FXML
    public void initialize() {
        disableSendButtonOnEmptyMessage();
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

    private void disableSendButtonOnEmptyMessage() {
        BooleanBinding emptyText = new BooleanBinding() {
            {
                super.bind(textMessage.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return textMessage.getText().isEmpty();
            }
        };

        sendButton.disableProperty().bind(emptyText);
    }
}

package com.encrypted.chat.message.list.item;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageOwner;
import com.encrypted.chat.message.MessageType;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageListItemController {
    @FXML
    private Label messageText;
    @FXML
    private Label messageTimestamp;
    @FXML
    private VBox messageBox;

    public void setMessage(Message message) {
        messageBox.setVisible(true);
        messageBox.setAlignment(getMessagePosition(message.owner));
        messageText.setText(message.message);
        messageText.setAlignment(getMessagePosition(message.owner));
        messageText.setStyle("-fx-background-radius: 5; -fx-background-color: " + getColor(message.owner));
        messageTimestamp.setText(message.timestamp);
    }

    private Pos getMessagePosition(MessageOwner type) {
        switch (type) {
            case SELF:
                return Pos.CENTER_RIGHT;
            case INCOMING:
            default:
                return Pos.CENTER_LEFT;
        }
    }

    private String getColor(MessageOwner type) {
        switch (type) {
            case SELF:
                return "#dedede";
            case INCOMING:
            default:
                return "#8CA9D6";
        }
    }
}

package com.encrypted.chat.message.list.item;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageListItemController {
    @FXML
    private Label messageText;
    @FXML
    private Label messageTimestamp;
    @FXML
    private VBox messageBox;

    public void setMessage(Message message) {
        messageBox.setVisible(true);
        messageBox.setAlignment(getMessagePosition(message.type));
        messageText.setText(message.message);
        messageText.setAlignment(getMessagePosition(message.type));
        messageText.setStyle("-fx-background-radius: 5; -fx-background-color: " + getColor(message.type));
        messageTimestamp.setText(message.timestamp);
    }

    private Pos getMessagePosition(MessageType type) {
        switch (type) {
            case OWNER:
                return Pos.CENTER_RIGHT;
            case INCOMING:
            default:
                return Pos.CENTER_LEFT;
        }
    }

    private String getColor(MessageType type) {
        switch (type) {
            case OWNER:
                return "#dedede";
            case INCOMING:
            default:
                return "#8CA9D6";
        }
    }
}

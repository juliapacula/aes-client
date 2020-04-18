package com.encrypted.chat.message.list.item;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageListItemController {
    @FXML
    private Label messageText;
    @FXML
    private HBox messageBox;

    public void setMessage(Message message) {
        messageText.setText(message.message);
        messageBox.setAlignment(getMessagePosition(message.type));
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
}

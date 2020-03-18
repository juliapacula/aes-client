package com.encrypted.chat.message.list;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MessageListController {
    @FXML
    private ListView<Message> messageList;

    @FXML
    public void initialize() {
        ObservableList<Message> list = FXCollections.observableArrayList(new Message("Hello! Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!", MessageType.OWNER));
        messageList.setItems(list);
    }
}

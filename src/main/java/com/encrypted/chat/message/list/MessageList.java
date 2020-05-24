package com.encrypted.chat.message.list;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.list.item.MessageListItemFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MessageList extends ListView<Message> {
    private ObservableList<Message> messages;

    @FXML
    private ListView<Message> messageList;

    public MessageList() {
        loadFxml();
    }

    public void setMessages(ObservableList<Message> messages) {
        messageList.setItems(messages);
        this.messages = messages;
    }

    @FXML
    public void initialize() {
        messageList.setCellFactory(new MessageListItemFactory());
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package com.encrypted.chat.message.list;

import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.list.item.MessageListItemFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MessageList extends ListView<Message> {
    public MessageList() {
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageList.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

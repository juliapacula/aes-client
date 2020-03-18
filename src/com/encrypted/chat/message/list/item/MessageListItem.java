package com.encrypted.chat.message.list.item;

import com.encrypted.chat.message.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MessageListItem extends ListCell<Message> {
    private MessageListItemController controller;

    public MessageListItem() {
        loadFxml();
    }

    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            controller.setMessage(item);
        }
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageListItem.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
            controller = loader.getController();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

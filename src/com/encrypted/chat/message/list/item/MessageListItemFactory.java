package com.encrypted.chat.message.list.item;

import com.encrypted.chat.message.Message;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MessageListItemFactory implements Callback<ListView<Message>, ListCell<Message>> {
    @Override
    public ListCell<Message> call(ListView<Message> param) {
        return new MessageListItem();
    }
}

package com.encrypted.chat.screen.messaging;

import com.encrypted.chat.message.list.MessageList;
import com.encrypted.chat.message.sender.MessageSender;
import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXML;

public class MessagingScreenController {
    private Presenter presenter;

    @FXML
    public MessageList messageList;
    @FXML
    public MessageSender messageSender;

    void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}

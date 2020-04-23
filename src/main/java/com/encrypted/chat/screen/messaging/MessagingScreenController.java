package com.encrypted.chat.screen.messaging;

import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.message.list.MessageList;
import com.encrypted.chat.message.sender.MessageSender;
import com.encrypted.chat.screen.Presenter;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class MessagingScreenController {
    @FXML
    public MessageList messageList;
    @FXML
    public MessageSender messageSender;
    @FXML
    public ToggleGroup modeToggleGroup;
    private Presenter presenter;

    public void exitApplication() {
        presenter.exit();
    }

    @FXML
    public void initialize() {
    }

    void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        messageList.setMessages(presenter.getStore().getMessages());
        messageSender.setPresenter(presenter);
        messageSender.visibleProperty().bind(presenter.getStore().connectedProperty());
        setupToggleGroup();
    }

    private void setupToggleGroup() {
        modeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioMenuItem selectedItem = (RadioMenuItem) newValue;
                EncryptionMode mode = EncryptionMode.valueOf(selectedItem.getText());
                presenter.getReducer().changeEncryptionMode(mode);
            }
        });

        FilteredList<Toggle> toSelect = modeToggleGroup
            .getToggles()
            .filtered((toggle -> ((RadioMenuItem) toggle).getText().equals(presenter.getStore().getEncryptionMode())));
        toSelect.get(0).setSelected(true);
    }
}

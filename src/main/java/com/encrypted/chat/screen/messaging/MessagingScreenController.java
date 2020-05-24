package com.encrypted.chat.screen.messaging;

import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.message.list.MessageList;
import com.encrypted.chat.message.sender.MessageSender;
import com.encrypted.chat.screen.Presenter;
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
        modeToggleGroup.selectedToggleProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        RadioMenuItem selectedItem = (RadioMenuItem) newValue;
                        EncryptionMode mode = EncryptionMode.valueOf(selectedItem.getText());
                        presenter.changeEncryptionMode(mode);
                    }
                });

        presenter.getStore().encryptionModeProperty()
                .addListener((observable, oldValue, newValue) -> {
                    Toggle toggleToDeselect = getSelectedToggle(oldValue);
                    Toggle toggleToSelect = getSelectedToggle(newValue);
                    toggleToDeselect.setSelected(false);
                    toggleToSelect.setSelected(true);
                });

        Toggle toggleToSelect = getSelectedToggle(presenter.getStore().getEncryptionMode().toString());
        toggleToSelect.setSelected(true);
    }

    private Toggle getSelectedToggle(String selectedValue) {
        return modeToggleGroup
                .getToggles()
                .filtered((toggle) -> isToggleSelected(toggle, selectedValue))
                .get(0);
    }

    private boolean isToggleSelected(Toggle toggle, String selectedValue) {
        return ((RadioMenuItem) toggle).getText().equals(selectedValue);
    }
}

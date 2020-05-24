package com.encrypted.chat.message.sender;

import com.encrypted.chat.screen.Presenter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MessageSender extends VBox {
    @FXML
    public JFXTextField textMessage;
    @FXML
    public JFXButton sendButton;

    private Presenter presenter;

    public MessageSender() {
        loadFxml();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    public void sendMessage() {
        String messageToSend = textMessage.getText();
        presenter.sendMessage(messageToSend);
        textMessage.setText("");
    }

    @FXML
    public void pickFile() {
        Stage dialog = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(dialog);

        if (selectedFile == null) {
            System.out.println("No file was chosen.");
        } else {
            ProgressBar progressBar = new ProgressBar();
            progressBar.prefWidthProperty().bind(widthProperty());
            progressBar.prefHeight(5);
            getChildren().add(progressBar);

            progressBar.progressProperty().bind(presenter.sendFile(selectedFile));

            progressBar.progressProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue.equals(1.0)) {
                            getChildren().remove(getChildren().size() - 1);
                        }
                    });
        }
    }

    @FXML
    public void initialize() {
        disableSendButtonOnEmptyMessage();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessageSender.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void disableSendButtonOnEmptyMessage() {
        BooleanBinding emptyText = new BooleanBinding() {
            {
                super.bind(textMessage.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return textMessage.getText().isEmpty();
            }
        };

        sendButton.disableProperty().bind(emptyText);
    }
}

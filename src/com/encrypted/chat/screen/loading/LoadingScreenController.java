package com.encrypted.chat.screen.loading;

import com.encrypted.chat.screen.messaging.MessagingScreen;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LoadingScreenController {
    @FXML
    public TextField clientIp;
    @FXML
    public Label errorIp;

    @FXML
    public void connectToClient() {
        String clientIpText = clientIp.getText();
        Pattern patternIP = Pattern.compile("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b");

        boolean isCorrectIp = patternIP.matcher(clientIpText).matches();

        if (!isCorrectIp) {
            errorIp.setText("This IP is not correct.");
        } else {
            errorIp.setText("");
            loadMainScene();
        }
    }

    private void loadMainScene() {
        Stage rootStage = (Stage) clientIp.getScene().getWindow();
        rootStage.setScene(new Scene(new MessagingScreen(), 800, 600));
    }
}

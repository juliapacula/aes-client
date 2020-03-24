package com.encrypted.chat.screen.loading;

import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class LoadingScreenController {
    @FXML
    public TextField clientIp;
    @FXML
    public Label errorIp;

    private Presenter presenter;

    void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    public void connectToClient() {
        String clientIpText = clientIp.getText();
        Pattern patternIP = Pattern.compile("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b");

        boolean isCorrectIp = patternIP.matcher(clientIpText).matches();

        if (!isCorrectIp) {
            errorIp.setText("This IP is not correct.");
        } else {
            errorIp.setText("");
            presenter.showMessagingScreen();
        }
    }
}

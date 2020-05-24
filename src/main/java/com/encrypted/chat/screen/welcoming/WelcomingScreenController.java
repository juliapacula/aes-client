package com.encrypted.chat.screen.welcoming;

import com.encrypted.chat.screen.Presenter;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class WelcomingScreenController {
    @FXML
    public JFXTextField password;
    @FXML
    public JFXTextField clientIp;

    private Presenter presenter;

    void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @FXML
    public void initialize() {
        initPassword();
        initClientIp();
    }

    @FXML
    public void connectToClient() {
        if (clientIp.validate() && password.validate()) {
            presenter.connectToClient(clientIp.getText(), password.getText());
            Stage currentStage = (Stage) password.getScene().getWindow();
            currentStage.close();
        } else if (!clientIp.validate()) {
            clientIp.requestFocus();
        } else {
            password.requestFocus();
        }
    }

    private void initPassword() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("This field is required.");

        password.getValidators().add(validator);

        password.focusedProperty().addListener((o, oldVa, newVal) -> {
            if (!newVal) {
                password.validate();
            }
        });
    }

    private void initClientIp() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("This field is required.");
        RegexValidator regexValidator = new RegexValidator();
        regexValidator.setRegexPattern("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b");
        regexValidator.setMessage("This is not an IP address.");

        clientIp.getValidators().add(validator);
        clientIp.getValidators().add(regexValidator);

        clientIp.focusedProperty().addListener((o, oldVa, newVal) -> {
            if (!newVal) {
                clientIp.validate();
            }
        });
    }
}

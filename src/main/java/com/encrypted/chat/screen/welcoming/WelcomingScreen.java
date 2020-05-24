package com.encrypted.chat.screen.welcoming;

import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class WelcomingScreen extends GridPane {
    private Presenter presenter;

    public WelcomingScreen(Presenter presenter) {
        this.presenter = presenter;
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomingScreen.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
            WelcomingScreenController controller = loader.getController();
            controller.setPresenter(presenter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package com.encrypted.chat.screen.messaging;

import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MessagingScreen extends BorderPane {
    private Presenter presenter;

    public MessagingScreen(Presenter presenter) {
        this.presenter = presenter;
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MessagingScreen.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
            MessagingScreenController controller = loader.getController();
            controller.setPresenter(presenter);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

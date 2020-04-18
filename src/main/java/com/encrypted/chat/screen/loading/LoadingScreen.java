package com.encrypted.chat.screen.loading;

import com.encrypted.chat.screen.Presenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoadingScreen extends GridPane {
    private Presenter presenter;

    public LoadingScreen(Presenter presenter) {
        this.presenter = presenter;
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoadingScreen.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
            LoadingScreenController controller = loader.getController();
            controller.setPresenter(presenter);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

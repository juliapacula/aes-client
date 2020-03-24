package com.encrypted.chat.screen.loading;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoadingScreen extends GridPane {
    public LoadingScreen() {
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingScreen.fxml"));
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}

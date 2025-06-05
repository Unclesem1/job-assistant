package com.jobassistant.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class RootController {
    @FXML private StackPane contentPane;

    @FXML
    public void initialize() throws Exception {
        showResume();
    }

    @FXML
    public void showResume() throws Exception {
        loadView("resume.fxml");
    }

    @FXML
    public void showStats() throws Exception {
        loadView("stats.fxml");
    }

    @FXML
    public void logout() {
        System.exit(0);
    }

    private void loadView(String fxml) throws Exception {
        Node view = FXMLLoader.load(getClass().getResource("/com/jobassistant/ui/view/" + fxml));
        contentPane.getChildren().setAll(view);
    }
}

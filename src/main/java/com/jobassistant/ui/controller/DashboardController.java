package com.jobassistant.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {
    @FXML private Button resumeButton;

    @FXML
    private void initialize() {
        System.out.println("Загрузка Dashboard");
    }
}

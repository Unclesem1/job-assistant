package com.jobassistant.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField phoneField;
    @FXML private PasswordField codeField;
    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> System.out.println("Заглушка входа в hh.ru"));
    }
}

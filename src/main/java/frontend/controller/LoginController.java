package frontend.controller;

import backend.hh.HhAuthManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> {
            try {
                String url = HhAuthManager.getAuthorizationUrl();
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                errorLabel.setText("Ожидание авторизации...");
            } catch (Exception ex) {
                errorLabel.setText("Ошибка запуска браузера: " + ex.getMessage());
            }
        });
    }
}

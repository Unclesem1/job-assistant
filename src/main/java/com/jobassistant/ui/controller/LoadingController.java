package com.jobassistant.ui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class LoadingController {

    @FXML
    public void initialize() {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // эмуляция задержки

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jobassistant/ui/view/dashboard.fxml"));
                                Parent root = loader.load();

                                // создаём новую сцену и получаем текущий stage через окно
                                Scene scene = new Scene(root, 900, 600);
                                Stage currentStage = (Stage) javafx.stage.Window.getWindows().get(0);
                                currentStage.setScene(scene);
                                currentStage.setTitle("Кабинет HH");
                                currentStage.show();

                    } catch (Exception e) {
                        System.out.println("❌ Ошибка загрузки dashboard.fxml");
                        e.printStackTrace();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

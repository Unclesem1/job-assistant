package com.jobassistant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static com.jobassistant.HhAuthManager.authenticate;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Авторизация через hh.ru
         authenticate();

        // Загрузка начального экрана
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jobassistant/ui/view/loading.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/com/jobassistant/ui/view/styles.css").toExternalForm());
        stage.setTitle("Кабинет HH");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            com.jobassistant.auth.CallbackServer.start(new HhAuthManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}

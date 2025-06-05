package com.jobassistant;

import com.jobassistant.auth.CallbackServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        HhAuthManager manager = new HhAuthManager();
        try {
            CallbackServer.start(manager, Main::showResumeScreen);
            manager.authenticate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showResumeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/jobassistant/ui/view/resume.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);
            mainStage.setTitle("Моё резюме");
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Очистка порта 8080
        try {
            echo "��� Освобождаем порт 8080..."
            fuser -k 8080/tcp > /dev/null 2>&1 || true
        } catch {
            echo "⚠ Не удалось освободить порт — продолжим."
        }

        launch(args);
    }
}

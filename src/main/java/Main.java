

import backend.hh.CallbackServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CallbackServer.start();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/view/login.fxml"));
        primaryStage.setTitle("Авторизация через HH.ru");
        primaryStage.setScene(new Scene(loader.load(), 400, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

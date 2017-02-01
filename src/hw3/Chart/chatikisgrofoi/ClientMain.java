package hw3.Chart.chatikisgrofoi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Жека on 1/26/2017.
 */
public class ClientMain extends Application {
  @Override
        public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Chatik/client.fxml"));
        primaryStage.setTitle("Кдлиентский чат");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

}


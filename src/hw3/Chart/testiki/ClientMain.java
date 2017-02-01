package hw3.Chart.testiki;

import hw3.Chart.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Жека on 1/26/2017.
 */
public class ClientMain extends Application implements Runnable{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Chatik/client.fxml"));
        primaryStage.setTitle("Клиентский чат");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        Thread secpot = new Thread(new ClientMain());
        secpot.start();
        launch(args);
    }

    @Override
    public void run() {
       ClientTest test = new ClientTest();
        test.startClient();

    }
}


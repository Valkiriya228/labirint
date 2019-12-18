package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;



public class Main extends Application {

    public Main() throws AWTException {
    }

    @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/labirint.fxml"));
            primaryStage.setTitle("LABIRINT");
            Scene scene = (new Scene(root, 1187, 853));
            primaryStage.setScene(scene);

            primaryStage.setResizable(true);
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}

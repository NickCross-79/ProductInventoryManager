package main.java.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.controllers.AppController;

public class App extends Application{
    public static Scene scene;
    public static Stage primaryStage;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
            primaryStage = stage;

            AppController.initialize();
            primaryStage.setTitle("Headphone Store");
            primaryStage.setScene(scene);
            primaryStage.show();
    }
}
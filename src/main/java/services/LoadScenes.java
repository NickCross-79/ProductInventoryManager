package main.java.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.controllers.AppController;

public class LoadScenes {
    public static List<Scene> list;
    Stage stage;
    Scene scene;
    AnchorPane root;
    public static int sceneNum;
    public static int totalPages;

    public LoadScenes(){
        sceneNum = 0;
        stage = new Stage();
        list = new ArrayList<>();
        totalPages = AppController.products.size()/10;
        if(AppController.products.size()%10 != 0) totalPages++;
    }

    public List<Scene> loadScenes(){
        for (int i = 0; i < totalPages; i++) {
            try {
                sceneNum++;
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/view/MainScene.fxml"));
                scene = new Scene(root);
                stage.setTitle("Application");
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(scene);
        }
        return list;
    }   
}

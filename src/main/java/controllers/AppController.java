package main.java.controllers;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import main.java.application.App;
import main.java.services.*;

public class AppController {
    public static List<VBox> products;
    public static List<Scene> scenes;
    public static int pages;
    public static ActionEvent lastAction;

    public static void initialize(){
        Server.connect("http://3.133.84.199:3000/Products/");
        products = new LoadProducts().loadProducts();
        pages = products.size();
        App.scene = new LoadScenes().loadScenes().get(0);
    }

    public static void reloadScene(){
        App.primaryStage.setScene(new LoadScenes().loadScenes().get(0));
    }
}

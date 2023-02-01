package main.java.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.controllers.AppController;

public class LoadProducts {
    private static List<VBox> items;
    private static JSONArray names;
    private static JSONObject obj;
    private static Button newNode;
    private static VBox vBox;
    private static ImageView image;

    public LoadProducts(){
        items = new ArrayList<>();
        names = new JSONArray();
    }
    
    public List<VBox> loadProducts() {
        try {
            names = (JSONArray) new JSONParser().parse((String)Server.sendGetRequest("names"));
            for(Object object : names){
                obj = (JSONObject) object;
                newNode = new Button();
                vBox = new VBox();

                newNode.setText(obj.get("name").toString().replace("_", " "));
                newNode.setOnAction(e -> {
                    try {
                        AppController.lastAction = e;
                        Stage detailsStage = new Stage();
                        AnchorPane detailsRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/view/DetailsScene.fxml"));
                        Scene dscene = new Scene(detailsRoot);
                        detailsStage.setScene(dscene);
                        detailsStage.show();
                    } catch (IOException er) {
                        er.printStackTrace();
                    }
                });

                image = new ImageView("http://3.133.84.199:3000/Products/images/"+obj.get("name"));
                image.setFitHeight(100);
                image.setFitWidth(100);

                vBox.getChildren().add(image);
                vBox.getChildren().add(newNode);
                vBox.setPadding(new Insets(25));

                items.add(vBox);
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return items;
    }  

    public VBox loadProductById(String id){
        try {
            obj = (JSONObject) new JSONParser().parse((String)Server.sendGetRequest(id));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        newNode = new Button();
        vBox = new VBox();

        newNode.setText(obj.get("name").toString().replace("_", " "));
        newNode.setOnAction(e -> {
            try {
                AppController.lastAction = e;
                Stage detailsStage = new Stage();
                AnchorPane detailsRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("/main/resources/view/DetailsScene.fxml"));
                Scene dscene = new Scene(detailsRoot);
                detailsStage.setScene(dscene);
                detailsStage.show();
            } catch (IOException er) {
                er.printStackTrace();
            }
        });

        image = new ImageView("http://3.133.84.199:3000/Products/images/"+obj.get("name"));
        image.setFitHeight(100);
        image.setFitWidth(100);

        vBox.getChildren().add(image);
        vBox.getChildren().add(newNode);
        vBox.setPadding(new Insets(25));

        return vBox;
    }
}

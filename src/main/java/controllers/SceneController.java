package main.java.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.services.LoadScenes;

public class SceneController implements Initializable{

    private List<VBox> products;
    private int pageNum;
    int row;
    int col;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane displayGrid;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;
    @FXML
    private Label lblPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        row = 0;
        col = 0;
        products = AppController.products;
        pageNum = LoadScenes.sceneNum;
        lblPage.setText(Integer.toString(pageNum));
        loadGrid();
    }

    public void loadGrid(){
        if(pageNum < LoadScenes.totalPages){
            btnNext.visibleProperty().set(true);
            btnNext.disableProperty().set(false);
            btnNext.setOnAction(e -> {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(LoadScenes.list.get(pageNum)); 
            });
        }

        if(pageNum != 1){
            btnPrev.visibleProperty().set(true);
            btnPrev.disableProperty().set(false);
            btnPrev.setOnAction(e -> {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(LoadScenes.list.get(pageNum-2)); 
            });
        }
        
        for(int i = 10*(pageNum-1); i < 10*(pageNum) && i < products.size(); i++){
            displayGrid.add(products.get(i),col,row);
            if(col == 4) {row++; col = 0;}
            else col++;
        }
    }
}

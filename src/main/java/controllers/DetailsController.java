package main.java.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.services.ProductService;
import main.java.services.Server;

public class DetailsController implements Initializable{
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    private JSONObject obj;
    private String id;
    
    @FXML
    ImageView detailsImage;
    @FXML
    Text detailsName;
    @FXML
    Label lblSale;
    @FXML
    TextArea detailsDescription;
    @FXML
    Text detailsEndingDate;
    @FXML
    Label lblEndingDate;
    @FXML
    Text detailsPrice;
    @FXML
    Text detailsColour;
    @FXML
    Text detailsManufacturer;
    @FXML
    Text detailsType;
    @FXML
    Text detailsStartDate;
    @FXML
    Button btnEdit;
    @FXML
    Button btnDelete;
    @FXML
    CheckBox chkbOnSale;
    @FXML
    DatePicker dpStartDate;
    @FXML
    DatePicker dpEndDate;
    @FXML
    TextField txtManufacturer;
    @FXML
    TextField txtColour;
    @FXML
    TextField txtPrice;
    @FXML
    TextField txtName;
    @FXML
    TextField txtType;
    @FXML
    Text detailsStock;
    @FXML
    TextField txtStock;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ActionEvent event = AppController.lastAction;
            String name = ((Button) event.getSource()).getText().replace(" ", "_");
            obj = (JSONObject) new JSONParser().parse((String)Server.sendGetRequest(name));
            loadDetails();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadDetails(){
        detailsImage.setImage(new Image("http://3.133.84.199:3000/Products/images/"+obj.get("name")));
        detailsName.setText(obj.get("name").toString().replace("_", " "));
        detailsPrice.setText("$"+obj.get("price").toString());
        detailsColour.setText(obj.get("colour").toString());
        detailsType.setText(obj.get("type").toString());
        detailsStock.setText(obj.get("stock").toString());
        detailsStartDate.setText((LocalDate.parse(obj.get("startingDateAvailable").toString(), dateFormat)).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        detailsManufacturer.setText(obj.get("manufacturer").toString());
        detailsDescription.setText(obj.get("description").toString());
        
        if((Boolean)obj.get("isOnSale") == true){
            lblSale.visibleProperty().set(true);
            lblEndingDate.visibleProperty().set(true);
            detailsEndingDate.setVisible(true);
            detailsEndingDate.setText((LocalDate.parse(obj.get("endingDateAvailable").toString(), dateFormat)).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        }
        
        id = obj.get("_id").toString();
    }

    @FXML
    public void btnEditClick(ActionEvent event){
        if(lblSale.isVisible()) {
            chkbOnSale.setSelected(true);
            dpEndDate.setValue(LocalDate.parse(obj.get("endingDateAvailable").toString(), dateFormat));
        }

        detailsName.setVisible(false);
        detailsColour.setVisible(false);
        detailsDescription.setEditable(true);
        detailsManufacturer.setVisible(false);
        detailsStartDate.setVisible(false);
        detailsType.setVisible(false);
        detailsPrice.setVisible(false);
        detailsStock.setVisible(false);
        lblSale.setVisible(true);
        chkbOnSale.setVisible(true);
        chkbOnSale.setDisable(false);
        lblEndingDate.setVisible(true);
        detailsEndingDate.setVisible(false);

        btnEdit.setText("Update");
        btnEdit.setOnAction(e -> {btnUpdateClick(e);});
        btnDelete.setText("Cancel");
        btnDelete.setOnAction(e -> {btnCancelClick(e);});

        dpStartDate.setVisible(true);
        dpStartDate.setValue(LocalDate.parse(obj.get("startingDateAvailable").toString().substring(0,10), dateFormat));
        dpStartDate.setDisable(false);

        dpEndDate.setVisible(true);
        dpEndDate.setDisable(false);

        txtManufacturer.setVisible(true);
        txtManufacturer.setText(detailsManufacturer.getText());
        txtManufacturer.setDisable(false);

        txtColour.setVisible(true);
        txtColour.setText(detailsColour.getText());
        txtColour.setDisable(false);

        txtPrice.setVisible(true);
        txtPrice.setText(detailsPrice.getText().substring(1));
        txtPrice.setDisable(false);

        txtName.setVisible(true);
        txtName.setText(detailsName.getText());
        txtName.setDisable(false);

        txtType.setVisible(true);
        txtType.setText(detailsType.getText());
        txtType.setDisable(false);

        txtStock.setVisible(true);
        txtStock.setText(detailsStock.getText());
        txtStock.setDisable(false);
    }

    @FXML
    public void btnDeleteClick(ActionEvent e){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure you want to delete this item?");
        alert.setContentText(detailsName.getText().replace("_", " "));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Server.sendDeleteRequest(id);

            AppController.products.removeIf(p -> {
                Button node = (Button)p.getChildren().get(1);
                return node.getText().equals(detailsName.getText());
            });
            AppController.pages = AppController.products.size();

            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            AppController.reloadScene();
        }
    }

    public void btnUpdateClick(ActionEvent e){
        Alert endDateNull = new Alert(AlertType.ERROR);
        endDateNull.setTitle("End date for sale not selected");
        endDateNull.setHeaderText("You must choose a ending date for the sale.");

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure you want to update this item?");
        alert.setContentText(detailsName.getText().replace("_", " "));
        
        if(chkbOnSale.isSelected() && dpEndDate.getValue() == null) endDateNull.showAndWait();

        else {
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                ProductService.updateItems(id, chkbOnSale.isSelected(), 
                    txtName.getText().replace(" ", "_"),
                    txtColour.getText(),
                    detailsDescription.getText(),
                    txtPrice.getText(),
                    dpStartDate.getValue().toString(),
                    txtType.getText(),
                    txtStock.getText(),
                    txtManufacturer.getText(),
                    dpEndDate.getValue()
                );

                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                AppController.reloadScene();
            }
        }
    }

    public void btnCancelClick(ActionEvent event){
        detailsName.setVisible(true);
        detailsColour.setVisible(true);
        detailsDescription.setEditable(false);
        detailsManufacturer.setVisible(true);
        detailsStartDate.setVisible(true);
        detailsType.setVisible(true);
        detailsStock.setVisible(true);
        detailsPrice.setVisible(true);
        if(detailsEndingDate.getText().isEmpty()) lblSale.setVisible(false);
        chkbOnSale.setVisible(false);
        chkbOnSale.setDisable(true);
        if(detailsEndingDate.getText().isEmpty()) lblEndingDate.setVisible(false);
        if(!detailsEndingDate.getText().isEmpty()) detailsEndingDate.setVisible(true);

        btnEdit.setText("Edit");
        btnEdit.setOnAction(e -> {btnEditClick(e);});
        btnDelete.setText("Delete");
        btnDelete.setOnAction(e -> {btnDeleteClick(e);});

        dpStartDate.setVisible(false);
        dpStartDate.setDisable(true);

        dpEndDate.setVisible(false);
        dpEndDate.setDisable(true);

        txtManufacturer.setVisible(false);
        txtManufacturer.setDisable(true);

        txtColour.setVisible(false);
        txtColour.setDisable(true);

        txtPrice.setVisible(false);
        txtPrice.setDisable(true);

        txtName.setVisible(false);
        txtName.setDisable(true);

        txtType.setVisible(false);
        txtType.setDisable(true);

        txtStock.setVisible(false);
        txtStock.setDisable(true);
    }
}

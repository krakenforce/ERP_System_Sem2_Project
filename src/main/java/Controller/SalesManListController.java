package Controller;

import App.App;
import Boxes.AlertBox;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalesManListController implements Initializable {

    private App app;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
    @FXML
    private Button btnAdd;
    @FXML
    private GridPane gpTable;
    @FXML
    private TableView<Salesman> tbSalemanList;
    @FXML
    private TableColumn<Long, Salesman> clID;

    @FXML
    private TableColumn<String, Salesman> clName;

    @FXML
    private TableColumn<String, Salesman> clAddress;

    @FXML
    private TableColumn<String, Salesman> clPhone;

    @FXML
    private TableColumn<?, Salesman> clFunction;

    public void setApp(App app){
        this.app = app;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbSalemanList.refresh();
        fetchDataToTable();
    }

    public void AddSeller(ActionEvent actionEvent) {
        if(emptyFieldWarning(tfName,tfAddress,tfPhone) == true){
            AlertBox alertBox = new AlertBox();
            alertBox.InsertAlert("Insert Seller", "Do you really want to insert this seller");
            Salesman salesman = new Salesman();
            salesman.setName(tfName.getText());
            salesman.setPhone(tfPhone.getText());
            salesman.setAddress(tfAddress.getText());

            SalesManDAO salesManDAO = new SalesManDAO();
            salesManDAO.saveSalesMan(salesman);
            tbSalemanList.refresh();
        }

    }

    public void fetchDataToTable(){
        ObservableList<Salesman> salesmenList = FXCollections.observableArrayList();
        SalesManDAO salesManDAO = new SalesManDAO();
        List<Salesman> salesmanList = salesManDAO.selectAll();
        Label label = new Label();
        for (Salesman value : salesmanList)
        {
            salesmenList.add(new Salesman(value.getId(), value.getName(), value.getAddress(), value.getPhone()));
        }
        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbSalemanList.setItems(salesmenList);

    }

    public boolean emptyFieldWarning(TextField tfName, TextField tfAddress, TextField tfPhone){
        if(tfName.getText().equals(null) ||tfAddress.getText().equals(null) || tfPhone.getText().equals(null) ){
            AlertBox alertBox = new AlertBox();
            alertBox.warningAlert("fail", "you must enter full infomation");
            return false;
        }
        return true;
    }
}

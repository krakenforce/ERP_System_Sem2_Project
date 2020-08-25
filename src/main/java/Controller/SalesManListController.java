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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalesManListController implements Initializable {

    private App app;

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
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

    public void setApp(App app) {
        this.app = app;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbSalemanList.setItems(getData());
        selectRowTable();
    }

    public void addSeller(ActionEvent actionEvent) {
        AlertBox alertBox = new AlertBox();
        if(alertBox.InsertAlert("Insert Seller", "Do you really want to insert this seller") == true){
            Salesman salesman = new Salesman();
            salesman.setName(tfName.getText());
            salesman.setPhone(tfPhone.getText());
            salesman.setAddress(tfAddress.getText());

            SalesManDAO salesManDAO = new SalesManDAO();
            salesManDAO.saveSalesMan(salesman);

            //refresh table after adding
            tbSalemanList.getItems().clear();
            tbSalemanList.setItems(getData());
        }
    }

    //retrieve salesman information from DB and return an ObservableList to setItems for table;
    public ObservableList<Salesman> getData() {
        ObservableList<Salesman> salesmenList = FXCollections.observableArrayList();
        SalesManDAO salesManDAO = new SalesManDAO();
        List<Salesman> list = salesManDAO.selectAll();
        for (Salesman value : list) {
            salesmenList.add(new Salesman(value.getId(), value.getName(), value.getAddress(), value.getPhone()));
        }
        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        return salesmenList;
    }

    public void selectRowTable(){
        tbSalemanList.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() > 1){
               Salesman salesman = getSelectedSalesMan();
               tfID.setText(salesman.getId().toString());
               tfName.setText(salesman.getName());
               tfPhone.setText(salesman.getPhone());
               tfAddress.setText(salesman.getAddress());
            }
        });
    }

    private Salesman getSelectedSalesMan() {
        Salesman selectedSalesman = null;
        if(tbSalemanList.getSelectionModel().getSelectedItem() != null){
            selectedSalesman = tbSalemanList.getSelectionModel().getSelectedItem();
        }
        return selectedSalesman;
    }

    //Delete selected salesman object and refresh the tableView;
    public void deleteSalesMan(ActionEvent event) {
        SalesManDAO salesManDAO = new SalesManDAO();
        salesManDAO.deleteSalesMan(getSelectedSalesMan());

        //refresh table after adding
        tbSalemanList.getItems().clear();
        tbSalemanList.setItems(getData());
    }

    //Update selected salesman object and refresh the tableview
    public void updateSalesMan(){
        Salesman salesman = new Salesman();
        salesman.setId(Long.parseLong(tfID.getText()));
        salesman.setName(tfName.getText());
        salesman.setAddress(tfAddress.getText());
        salesman.setPhone(tfPhone.getText());

        SalesManDAO salesManDAO = new SalesManDAO();
        salesManDAO.updateSalesMan(salesman);

        //refresh table after adding
        tbSalemanList.getItems().clear();
        tbSalemanList.setItems(getData());

    }

}

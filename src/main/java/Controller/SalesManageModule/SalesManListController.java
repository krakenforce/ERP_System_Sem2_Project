package Controller.SalesManageModule;

import App.App;
import Boxes.AlertBox;
import Repositories.Function;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalesManListController implements Initializable, Function {

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
    private TableColumn<Salesman, Long> clID;

    @FXML
    private TableColumn<Salesman, String> clName;

    @FXML
    private TableColumn<Salesman, String> clAddress;

    @FXML
    private TableColumn<Salesman, String> clPhone;

    @FXML
    private TableColumn<Salesman, Void> clFunction;

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
        if (alertBox.InsertAlert("Insert Seller", "Do you really want to insert this seller") == true) {
            Salesman salesman = new Salesman();
            salesman.setName(tfName.getText());
            salesman.setPhone(tfPhone.getText());
            salesman.setAddress(tfAddress.getText());

            SalesManDAO salesManDAO = new SalesManDAO();
            salesManDAO.saveSalesMan(salesman);

           refreshTable(tbSalemanList);
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
        addButtonToTable();
        return salesmenList;
    }

    public void selectRowTable() {
        tbSalemanList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
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
        if (tbSalemanList.getSelectionModel().getSelectedItem() != null) {
            selectedSalesman = tbSalemanList.getSelectionModel().getSelectedItem();
        }
        return selectedSalesman;
    }


    //Delete selected salesman object and refresh the tableView;
    public void deleteSalesMan(ActionEvent event) {
        SalesManDAO salesManDAO = new SalesManDAO();
        salesManDAO.deleteSalesMan(getSelectedSalesMan());

        //refresh table after adding
        refreshTable(tbSalemanList);
    }

    //Update selected salesman object and refresh the tableview
    public void updateSalesMan() {
        Salesman salesman = new Salesman();
        salesman.setId(Long.parseLong(tfID.getText()));
        salesman.setName(tfName.getText());
        salesman.setAddress(tfAddress.getText());
        salesman.setPhone(tfPhone.getText());

        SalesManDAO salesManDAO = new SalesManDAO();
        salesManDAO.updateSalesMan(salesman);

        refreshTable(tbSalemanList);
    }

    //Clear all information in Textfield when click on Cancel button
    public void cancelAction(ActionEvent event) {
        tfID.clear();
        tfName.clear();
        tfPhone.clear();
        tfAddress.clear();
    }

    public void openSellersProductGroup(ActionEvent event) throws IOException {
        if(getSelectedSalesMan() == null){
            AlertBox alertBox = new AlertBox();
            alertBox.nullSelectedSalesman();
        }else{
            Salesman selectedSalesman = getSelectedSalesMan();
            String salesmanName = selectedSalesman.getName();
            Long id = selectedSalesman.getId();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/SalesManProductGroup.fxml"));
            Parent root = loader.load();

            SalesManProductGroupController controller = loader.getController();
            controller.getSalesmanInfo(id, salesmanName);
            controller.setId(id);
            controller.setDataToTable(controller.getGroupProductList(id));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        }
    }


    public void openSalesmanCustomerList(ActionEvent actionEvent) throws IOException {
        if(getSelectedSalesMan() == null){
            AlertBox alertBox = new AlertBox();
            alertBox.nullSelectedSalesman();
        }else {
            Salesman selectedSalesman = getSelectedSalesMan();
            Long id = selectedSalesman.getId();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/CustomerSalerList.fxml"));
            Parent root = loader.load();

            CustomerSalerListController controller = loader.getController();
            controller.setSalesmanInfo(id);
            controller.getDataCustomer(id);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        }
    }

    public void openCommissionSummary(ActionEvent actionEvent) throws IOException {
        if(getSelectedSalesMan() == null){
            AlertBox alertBox = new AlertBox();
            alertBox.nullSelectedSalesman();
        }else {
            Salesman selectedSalesman = getSelectedSalesMan();
            Long id = selectedSalesman.getId();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/CommissionSummary.fxml"));
            Parent root = loader.load();

            CommissionSummaryController controller = loader.getController();
            controller.setSalesmanID(selectedSalesman.getId());
            controller.runTable(selectedSalesman.getName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        }
    }

    public void openCreateOrder(ActionEvent actionEvent) throws IOException {
        if(getSelectedSalesMan() == null){
            AlertBox alertBox = new AlertBox();
            alertBox.nullSelectedSalesman();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/CreateOrder.fxml"));
            Parent root = loader.load();

            CreateOrderController controller = loader.getController();
            controller.setSalesman(getSelectedSalesMan());
            controller.setTfSalesmanName();
            controller.getCustomerList(getSelectedSalesMan().getId());
            controller.getProductBySalesman();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        }

    }

    @Override
    public void refreshTable(TableView tableName) {
        tableName.getItems().clear();
        tableName.setItems(getData());
    }

    private void addButtonToTable(){
        Callback<TableColumn<Salesman, Void>, TableCell<Salesman, Void>> cellFactory = new Callback<TableColumn<Salesman, Void>, TableCell<Salesman, Void>>() {
            @Override
            public TableCell<Salesman, Void> call(TableColumn<Salesman, Void> salesmanVoidTableColumn) {
                final TableCell<Salesman, Void> cell = new TableCell<Salesman, Void>(){

                    private final Button btn = new Button("Delete");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Salesman selectedSalesman = getTableView().getItems().get(getIndex());
                            SalesManDAO dao = new SalesManDAO();
                            dao.deleteSalesMan(selectedSalesman);
                            refreshTable(tbSalemanList);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        clFunction.setCellFactory(cellFactory);
    }


    public void openCreateReceipt(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/ReceiptByType.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Second Window");
        stage.show();
    }
}
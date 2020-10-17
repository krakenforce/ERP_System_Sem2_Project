package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.Salesman_GroupProduct;
import TestController.ProductInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


public class SalesManProductGroupController implements Initializable {
    @FXML
    private TextField tfSellerID;
    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<String> cbProGroup;

    @FXML
    private TableView<GroupProduct> tbProductGroup;

    @FXML
    private TableColumn<GroupProduct, String> clGroupName;

    @FXML
    private TableColumn<GroupProduct, Double> clCommission;

    @FXML
    private ListView<String> lvProductList;

    private Long salesmanID;
    private Double commission = 0.0;
    private String productGroupName = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToComboBox();
        openProductInfo();
    }

    public void addProductGroup(ActionEvent event) {
        SalesManDAO salesManDAO = new SalesManDAO();
        Salesman selectedSalesman = salesManDAO.findById(salesmanID);

        //Get Product group ID
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        GroupProduct selectedGroup = groupProductDAO.findByName(cbProGroup.getValue());

        //After add show them to list view
        Salesman_GroupProduct salesman_groupProduct = new Salesman_GroupProduct();
        salesman_groupProduct.setSalesman(selectedSalesman);
        salesman_groupProduct.setGroupProduct(selectedGroup);
        Salesman_ProductGroupDAO salesmanProGroup = new Salesman_ProductGroupDAO();

        if(checkGroupExist(selectedSalesman, selectedGroup) == true){
            salesmanProGroup.saveGroup(salesman_groupProduct);
            refreshTable();
        }else{
            AlertBox alertBox = new AlertBox();
            alertBox.warningAlert("Cannot insert product group", "This group have existed, you don't need to add it again");
        }




    }
    public void updateSellerProGroup(ActionEvent event) {

    }

    public void deleteSellerProGroup(ActionEvent event) {
        SalesManDAO salesManDAO = new SalesManDAO();
        Salesman salesman = salesManDAO.findById(Long.parseLong(tfSellerID.getText()));

        GroupProductDAO groupProductDAO = new GroupProductDAO();
        GroupProduct groupProduct = groupProductDAO.findByName(tbProductGroup.getSelectionModel().getSelectedItem().getName());

        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        Salesman_GroupProduct salesman_groupProduct = salesman_productGroupDAO.selectBySalesmanAndGroupProductID(salesman.getId(),groupProduct.getId());
        salesman_productGroupDAO.deleteGroup(salesman_groupProduct);

        refreshTable();
    }

    public void cancelAction(ActionEvent event) {

    }

    public ObservableList<String> setDataToComboBox(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        List<GroupProduct> groupProductList  = groupProductDAO.getAll();
        for(GroupProduct value : groupProductList){
            observableList.add(value.getName());
        }
        cbProGroup.setItems(observableList);
        return observableList;
    }

    public ObservableList<GroupProduct> getGroupProductList(Long salesmanID){
        ObservableList<GroupProduct> observableList = FXCollections.observableArrayList();
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        List<Salesman_GroupProduct> salesman_groupProductsList = salesman_productGroupDAO.selectBySalesmanID(salesmanID);

        for(Salesman_GroupProduct items : salesman_groupProductsList){
            observableList.add(items.getGroupProduct());
            setCommission(items.getGroupProduct().getCommission());
            setProductGroupName(items.getGroupProduct().getName());
        }
        return observableList;
    }

    public void getSalesmanInfo(Long id, String salesmanName){
        tfSellerID.setText(id.toString());
        tfName.setText(salesmanName);
    }

    public void setDataToTable(ObservableList<GroupProduct> productList){
        clGroupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clCommission.setCellValueFactory(new PropertyValueFactory<>("commission"));
        tbProductGroup.setItems(productList);
    }

    public ObservableList<String> showProductListByClick(Long groupProductID){
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.findByGroupProductID(groupProductID);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for(Product items : list ){
            observableList.add(items.getName());
        }
        lvProductList.setItems(observableList);
        return observableList;
    }

    public void test(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            GroupProduct groupProduct = tbProductGroup.getSelectionModel().getSelectedItem();
            showProductListByClick(groupProduct.getId());
        }
    }
    public void refreshTable() {
        tbProductGroup.getItems().clear();
        tbProductGroup.setItems(getGroupProductList(salesmanID));
    }

    public void openProductInfo(){
        lvProductList.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() >= 2){
                ProductDAO dao = new ProductDAO();
                String productName = lvProductList.getSelectionModel().getSelectedItem();
                Product product = dao.findByName(productName);
                Long id, minInven, price, retail;
                Double ratePro;
                String name, groupName, barcode;

                id = product.getId();
                minInven = product.getMinimumInventory();
                price = product.getPrice();
                ratePro = product.getRateProfit();
                retail = product.getRetailPrice();
                barcode = product.getBarCode();
                name = product.getName();
                groupName = product.getGroupProduct().getName();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/TestForm/ProductInfo.fxml"));
                Parent root = loader.getRoot();

                try {
                    root = loader.load();
                    ProductInfoController controller = loader.getController();
                    controller.testSetField(id,name,minInven,price,ratePro,retail,groupName,barcode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Second Window");
                stage.show();
            }
        });
    }

    public Boolean checkGroupExist(Salesman salesman, GroupProduct groupProduct){
        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        List<Salesman_GroupProduct> salesman_groupProductList = salesman_productGroupDAO.selectBySalesmanID(salesman.getId());
        for(int i = 0; i< salesman_groupProductList.size(); i++){
            if(groupProduct.getId() == salesman_groupProductList.get(i).getGroupProduct().getId()){
                return false;
            }
        }
        return true;
    }

    public Long getId() {
        return salesmanID;
    }

    public void setId(Long salesmanID) {
        this.salesmanID = salesmanID;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public void LamMoi(){

    }


}

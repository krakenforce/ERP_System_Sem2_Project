package Controller.SalesManageModule;

import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.Salesman_GroupProduct;
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
        salesmanProGroup.saveGroup(salesman_groupProduct);

        refreshTable();

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


}

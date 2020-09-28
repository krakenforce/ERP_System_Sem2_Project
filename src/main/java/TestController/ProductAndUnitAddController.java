package TestController;

import Boxes.AlertBox;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.UnitDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductAndUnitAddController implements Initializable {

    @FXML
    private TextField tfPrimaryUnit;

    @FXML
    private TextField tfPrimaryUnitValue;

    @FXML
    private TextField tfExchangeUnit;

    @FXML
    private TextField tfExchangeUnitValue;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfRateProfit;

    @FXML
    private TextField tfRetailPrice;

    @FXML
    private TextField tfMinInventory;

    @FXML
    private ComboBox<String> cbGroupProduct;


    @FXML
    public void addProductAndUnit(ActionEvent actionEvent) {
        AlertBox alertBox = new AlertBox();
        if(alertBox.InsertAlert("Add Product and Unit","Are you sure to insert this") == true){
            GroupProductDAO dao = new GroupProductDAO();
            GroupProduct selectedGroupProduct = dao.findByName(cbGroupProduct.getSelectionModel().getSelectedItem());
            Product product = new Product();
            Unit unit = new Unit();
            unit.setUnitPrimary(tfPrimaryUnit.getText());
            unit.setValuePrimary(Long.parseLong(tfPrimaryUnitValue.getText()));
            unit.setUnitExchange(tfExchangeUnit.getText());
            unit.setValueExchange(Long.parseLong(tfExchangeUnitValue.getText()));
            UnitDAO unitDAO = new UnitDAO();
            unitDAO.saveUnit(unit);

            product.setGroupProduct(selectedGroupProduct);
            product.setName(tfName.getText());
            product.setPrice(Long.parseLong(tfPrice.getText()));
            product.setRateProfit(Double.parseDouble(tfRateProfit.getText()));
            product.setRetailPrice(Long.parseLong(tfRetailPrice.getText()));
            product.setMinimumInventory(Long.parseLong(tfMinInventory.getText()));
            product.setUnit(unit);

            ProductDAO productDAO = new ProductDAO();
            productDAO.saveProduct(product);

        }else{
            //do nothing
        }

    }

    @FXML
    public void clearField(ActionEvent actionEvent) {
    }

    public void setCbGroupProduct(){
        ObservableList<String> groupProductNameList = FXCollections.observableArrayList();
        GroupProductDAO dao = new GroupProductDAO();
        List<GroupProduct> groupProductList = dao.getAll();

        for(GroupProduct items : groupProductList){
            groupProductNameList.add(items.getName());
        }
        cbGroupProduct.setItems(groupProductNameList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCbGroupProduct();
    }
}

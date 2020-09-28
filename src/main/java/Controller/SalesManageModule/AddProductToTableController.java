package Controller.SalesManageModule;

import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.DAO.WarehousingDetailDAO;
import Services.Hibernate.EntityCombination.OrderProductDetailWareHousing;
import Services.Hibernate.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddProductToTableController implements Initializable {

    @FXML
    private ComboBox<String> cbProductName;

    @FXML
    private Spinner<Integer> spAmount;

    private Consumer<OrderProductDetailWareHousing> callback;

    long salesmanID;

    public void AddProductToTable(ActionEvent actionEvent) throws IOException {
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product selectedProduct = dao.findByName(productName);
        Long amount = getSpinnerValue();

        OrderProductDetailWareHousing object = new OrderProductDetailWareHousing();
        object.setProductID(selectedProduct.getId());
        object.setProductName(productName);
        object.setAmount(amount);
        object.setPrice(selectedProduct.getPrice());
        object.setTotal(selectedProduct.getPrice() * amount);
        object.setProduct(selectedProduct);
        checkAmountOfProduct(amount,selectedProduct,object);

        //create callback
        callback.accept(object);

    }

    public Product getProduct(){
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product product = dao.findByName(productName);
        return product;
    }
    public void getProductBySalesman(){
        ObservableList<String> obsProName = FXCollections.observableArrayList();
        long salesmanID = getSalesmanID();
        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = null;
        List<Salesman_GroupProduct> salesman_groupProductList = salesman_productGroupDAO.selectBySalesmanID(salesmanID);
        for(Salesman_GroupProduct items : salesman_groupProductList ){
            long productGroupID = items.getGroupProduct().getId();
            productList = productDAO.findByGroupProductID(productGroupID);
            for(Product product: productList){
                obsProName.add(product.getName());
            }
        }
        cbProductName.setItems(obsProName);
    }

    public void spinnerInit(){
        spAmount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99)
        );
    }
    public Long getSpinnerValue(){
        return (long)spAmount.getValue();
    }

    public Boolean checkAmountOfProduct(Long amount, Product product, OrderProductDetailWareHousing object){
        WarehousingDetailDAO warehousingDetailsDAO = new WarehousingDetailDAO();
        Long amountInStock = warehousingDetailsDAO.findByProductId(product.getId()).getAmount();
        Text enough;
        boolean status;
        if(amount <= amountInStock){
            enough = new Text("YES");
            enough.setFill(Color.GREEN);
            status = true;
        }else{
            enough = new Text("NO");
            enough.setFill(Color.RED);
            status = false;
        }
        object.setEnough(enough);
        object.setEnoughStatus(status);
        return status;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerInit();
    }

    public Consumer<OrderProductDetailWareHousing> getCallback() {
        return callback;
    }

    public void setCallback(Consumer<OrderProductDetailWareHousing> callback) {
        this.callback = callback;
    }

    public long getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(long salesmanID) {
        this.salesmanID = salesmanID;
    }
}

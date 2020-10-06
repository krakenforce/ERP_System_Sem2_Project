package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.*;
import Services.Hibernate.EntityCombination.OrderProductDetailWareHousing;
import Services.Hibernate.entity.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

public class CreateOrderController implements Initializable {

    @FXML
    private TextField tfSalesmanName;

    @FXML
    private TextField tfBarcode;

    @FXML
    private TextField tfCustomerName;

    @FXML
    private DatePicker dpCurrentDate;

    @FXML
    private ComboBox<Long> cbCustomerID;

    @FXML
    private ComboBox<String> cbProductName;

    @FXML
    private Spinner<Integer> spAmount;

    @FXML
    private TextField tfTotalCost;

    @FXML
    private TextField tfPaid;

    @FXML
    private TextField tfChange;

    @FXML
    private TableView<OrderProductDetailWareHousing> tbProductList;

    @FXML
    private TableColumn<?, ?> clProductID;

    @FXML
    private TableColumn<?, ?> clProductName;

    @FXML
    private TableColumn<?, ?> clAmount;

    @FXML
    private TableColumn<?, ?> clPrice;

    @FXML
    private TableColumn<?, ?> clSalePrice;

    @FXML
    private TableColumn<?, ?> clTotal;

    @FXML
    private TableColumn<?, ?> clEnough;

    Product product;
    Long amount;
    private ObservableList<OrderProductDetailWareHousing> orderProductList = FXCollections.observableArrayList();
    private Salesman salesman;
    ObservableList<OrderProductDetailWareHousing> observableList ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerInit();
        dpCurrentDate.setValue(LocalDate.now());
        testAddProduct();
    }

    @FXML
    void clearInfo(ActionEvent event) {
        cbCustomerID.getSelectionModel().clearSelection();
        tfTotalCost.clear();
        tfPaid.clear();
        tfChange.clear();
        tbProductList.getItems().clear();
        tfCustomerName.clear();
    }

    @FXML
    void saveOrder(ActionEvent event) {
        Salesman selectedSalesman = salesman;
        CustomerDAO customerDAO = new CustomerDAO();
        Customer selectedCustomer = customerDAO.findByID(cbCustomerID.getValue());
        DetailOrder selectedDetailOrder = new DetailOrder(getDate(),paidChecks(), selectedCustomer);
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        detailOrderDAO.saveDetailOrder(selectedDetailOrder);

        //fix this bug, it still insert into DB if another product is enough, when not enough, cannot create Order;
        for(int i = 0; i < orderProductList.size(); i++){
            if(orderProductList.get(i).getEnoughStatus() == false){
                AlertBox alertBox = new AlertBox();
                alertBox.warningAlert("Cannot create order", orderProductList.get(i).getProductName() + " is not enough, please warehousing more ");
            }else{
                OrderDAO orderDAO = new OrderDAO();
                Order order = new Order();
                Product productOfList = orderProductList.get(i).getProduct();
                order.setAmount(orderProductList.get(i).getAmount());
                order.setDetailOrder(selectedDetailOrder);
                order.setEnough(orderProductList.get(i).getEnoughStatus());
                order.setProduct(productOfList);
                order.setSalesman(selectedSalesman);
                orderDAO.saveOrder(order);
            }
        }
    }

    @FXML
    void addProductToOrderList(ActionEvent event) {
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product selectedProduct = dao.findByName(productName);
        Long amount = getSpinnerValue();
        Long salePrice = null;

        OrderProductDetailWareHousing object = new OrderProductDetailWareHousing();

        object.setProductID(selectedProduct.getId());
        object.setProductName(productName);
        object.setAmount(amount);
        object.setPrice(selectedProduct.getPrice());
        if(checkDiscount(selectedProduct.getId()) != null){
            salePrice = selectedProduct.getPrice() - checkDiscount(selectedProduct.getId());
            object.setSalePrice(salePrice);
            object.setTotal(salePrice * amount);
        }else{
            object.setSalePrice(selectedProduct.getPrice());
            object.setTotal(selectedProduct.getPrice() * amount);
        }
        object.setProduct(selectedProduct);
        if(checkAmountOfProduct(amount,selectedProduct,object) == false){
            AlertBox alertBox = new AlertBox();
            alertBox.warningAlert("Not enough product", "Please warehousing more product");
        };
        orderProductList.add(object);


        calculateTotalCost(orderProductList);
        setDataToTable(orderProductList);
    }

    public Boolean checkDuplicateProduct(){
        for(OrderProductDetailWareHousing items : orderProductList){
            String productName = cbProductName.getSelectionModel().getSelectedItem();
            ProductDAO dao = new ProductDAO();
            Product selectedProduct = dao.findByName(productName);
            Long amount = getSpinnerValue();

            OrderProductDetailWareHousing object = new OrderProductDetailWareHousing();
            object.setProductID(selectedProduct.getId());
            object.setProductName(productName);
            object.setAmount(amount);
            object.setPrice(selectedProduct.getPrice());
            object.setProduct(selectedProduct);
            if(object.getProductName().equals(items.getProductName())) {
                long amountTotal = items.getAmount();
                amountTotal += object.getAmount();
                items.setAmount(amountTotal);
                items.setTotal(amountTotal * items.getPrice());
                return true;
            }else{
                checkAmountOfProduct(amount,selectedProduct,object);
                orderProductList.add(object);
            }
        }
        return false;

    }

    public void calculateTotalCost(ObservableList<OrderProductDetailWareHousing> list){
        long totalCost = 0;
        for(OrderProductDetailWareHousing items: list){
            totalCost += items.getTotal();
        }
        tfTotalCost.setText(String.valueOf(totalCost));
    }

    public ObservableList<Customer> getCustomerList(Long salesmanID){
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        ObservableList<Long> customerIDList = FXCollections.observableArrayList();
        CustomerDAO dao = new CustomerDAO();
        List<Customer> list = dao.findBySalesmanID(salesmanID);

        for(Customer items : list){
            observableList.add(items);
            customerIDList.add(items.getId());
        }
        cbCustomerID.setItems(customerIDList);
        return observableList;
    }

    public void getProductBySalesman(){
        ObservableList<String> obsProName = FXCollections.observableArrayList();
        long salesmanID = getSalesman().getId();
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

    public Long checkDiscount(Long productID){
        LocalDate localDate = dpCurrentDate.getValue();
        Date selectedDay = Date.valueOf(localDate);
        DiscountDAO dao = new DiscountDAO();
        List<Discount> discountList = dao.findByProduct(productID) ;

        for(Discount discount : discountList){
            if((selectedDay.before(discount.getDateEnd()) || selectedDay.equals(discount.getDateEnd())) &&
                    (selectedDay.after(discount.getDateStarts()) || selectedDay.equals(discount.getDateStarts()))) {
                return discount.getMoneyReduction();
            }
        }
        return null;
    }
    public Boolean paidChecks(){
        long totalCost = Long.parseLong(tfTotalCost.getText());
        long isPayed = Long.parseLong(tfPaid.getText());
        if(totalCost == isPayed){
            return true;
        }else if(totalCost < isPayed){
            tfChange.setText(String.valueOf(isPayed - totalCost));
            return true;
        }else{
            return false;
        }
    }


    public void setTfSalesmanName(){
        tfSalesmanName.setText(salesman.getName());
    }

    @FXML
    void getCustomerName(ActionEvent event) {
        Long customerID = cbCustomerID.getSelectionModel().getSelectedItem();
        CustomerDAO dao  = new CustomerDAO();
        Customer selectedCustomer = dao.findByID(customerID);
        tfCustomerName.setText(selectedCustomer.getName());

    }

    public void setDataToTable(ObservableList obsList){
        clAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        clProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        clEnough.setCellValueFactory(new PropertyValueFactory<>("enough"));
        tbProductList.setItems(obsList);

    }

    public Date getDate(){
        LocalDate localDate = dpCurrentDate.getValue();
        return Date.valueOf(localDate);
    }

    public void spinnerInit(){
        spAmount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99)
        );
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

    @FXML
    void openAddProduct(ActionEvent event) throws IOException {
        getOrderProductList();
    }

    public ObservableList<OrderProductDetailWareHousing> getOrderProductList() throws IOException {
        orderProductList = FXCollections.observableArrayList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/AddProductToTable.fxml"));
        Parent root = loader.load();
        AddProductToTableController controller = loader.getController();
        controller.setSalesmanID(salesman.getId());
        controller.getProductBySalesman();
        Parent popup = loader.getRoot();
        AtomicLong totalCost = new AtomicLong();

        //set Callback
        controller.setCallback(
                (orderProductDetailWareHousingObj) -> { orderProductList.add(orderProductDetailWareHousingObj);

                    totalCost.addAndGet(orderProductDetailWareHousingObj.getTotal());
                    tfTotalCost.setText(String.valueOf(totalCost.get()));
                    setDataToTable(orderProductList);
                    tbProductList.refresh();
                });
        Stage stage = new Stage();
        stage.setScene(new Scene(popup));
        stage.show();
        return orderProductList;
    }

    public void AddProductToTable(){
    }

    public Long getSpinnerValue(){
        return (long)spAmount.getValue();
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    @FXML
    public void openAddCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/AddNewCustomer.fxml"));
        Parent root = fxmlLoader.load();

        AddNewCustomerController controller = fxmlLoader.getController();
        controller.setSalesman(getSalesman());
        controller.setSalesmanData();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void testAddProduct(){
        tfBarcode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                s = "";
                ProductDAO dao = new ProductDAO();
                Product selectedProduct = dao.findByBarcode(t1);
                Long amount = (long) 1;
                Long salePrice = null;

                OrderProductDetailWareHousing object = new OrderProductDetailWareHousing();

                object.setProductID(selectedProduct.getId());
                object.setProductName(selectedProduct.getName());
                object.setAmount(amount);
                object.setPrice(selectedProduct.getPrice());
                if(checkDiscount(selectedProduct.getId()) != null){
                    salePrice = selectedProduct.getPrice() - checkDiscount(selectedProduct.getId());
                    object.setSalePrice(salePrice);
                    object.setTotal(salePrice * amount);
                }else{
                    object.setSalePrice(selectedProduct.getPrice());
                    object.setTotal(selectedProduct.getPrice() * amount);
                }
                object.setProduct(selectedProduct);
                if(checkAmountOfProduct(amount,selectedProduct,object) == false){
                    AlertBox alertBox = new AlertBox();
                    alertBox.warningAlert("Not enough product", "Please warehousing more product");
                };
                orderProductList.add(object);


                calculateTotalCost(orderProductList);
                setDataToTable(orderProductList);
                tfBarcode.setText("");
            }
        });
    }

    public void testBarcode(InputMethodEvent inputMethodEvent) {

    }
}

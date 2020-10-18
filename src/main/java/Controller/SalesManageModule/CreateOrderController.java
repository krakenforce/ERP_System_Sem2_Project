package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.*;
import Services.Hibernate.EntityCombination.OrderProductDetailWareHousing;
import Services.Hibernate.entity.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
    private BorderPane bpMain;

    @FXML
    private TextField tfVoucherCode;

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

    @FXML
    private ComboBox<String> cbSearchType;

    @FXML
    private TextField tfSearchCustomer;

    @FXML
    private Label lbVoucherInfo;

    Product product;
    Long amount;
    private ObservableList<OrderProductDetailWareHousing> orderProductList = FXCollections.observableArrayList();
    private Salesman salesman;
    ObservableList<OrderProductDetailWareHousing> observableList ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerInit();
        dpCurrentDate.setValue(LocalDate.now());
        addProductByBarcodeReader();
        setDataForCBSearchType();
        voucherOption();
        removeProduct();
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
        DetailOrder selectedDetailOrder = new DetailOrder(getDate(),paidChecks(), selectedCustomer,getDebt(), getTotal());
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        detailOrderDAO.saveDetailOrder(selectedDetailOrder);
        WarehousingDetailDAO warehousingDetailDAO = new WarehousingDetailDAO();


        //làm sao để kiểm tra chỉ cần có 1 phần tử có trạng thái là NO thì ko cho tạo Order
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

                WarehousingDetails warehousingDetails = warehousingDetailDAO.findByProductId(orderProductList.get(i).getProductID());
                warehousingDetails.setAmount(warehousingDetails.getAmount() - orderProductList.get(i).getAmount());
                warehousingDetailDAO.updateWarehousingDetail(warehousingDetails);

                orderDAO.saveOrder(order);
            }
        }
        AlertBox alertBox = new AlertBox();
        alertBox.confirmAlert("Create Order Successfully", "complete creating order");
        clearInfo();
    }

    @FXML
    void addProductToOrderList(ActionEvent event) {
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product selectedProduct = dao.findByName(productName);
        Long amount = getSpinnerValue();
        Long salePrice = null;
        if(amount > 0){
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

            if(checkDuplicateProduct(object, orderProductList) == true){
                object.setProduct(selectedProduct);
                if(checkAmountOfProduct(amount,selectedProduct,object) == false){
                    AlertBox alertBox = new AlertBox();
                    alertBox.warningAlert("Not enough product", "Please warehousing more product");
                };
                orderProductList.add(object);
                tbProductList.refresh();
                setDataToTable(orderProductList);
            }else{
                for(int i = 0; i < orderProductList.size(); i++){
                    if(object.getProductID() == orderProductList.get(i).getProductID()){
                        orderProductList.get(i).setAmount(object.getAmount() + orderProductList.get(i).getAmount());
                        orderProductList.get(i).setTotal(object.getTotal() + orderProductList.get(i).getTotal());

                        if(checkAmountOfProduct(orderProductList.get(i).getAmount(),orderProductList.get(i).getProduct(),orderProductList.get(i)) == false ){
                            AlertBox alertBox = new AlertBox();
                            alertBox.warningAlert("Not enough product", "Please warehousing more product");
                            orderProductList.remove(i);
                        }
                    }
                }
                tbProductList.refresh();
                setDataToTable(orderProductList);
            }
            calculateTotalCost(orderProductList);
        }else{
            AlertBox alertBox = new AlertBox();
            alertBox.warningAlert("Please set amount", "You cannot add product because product amount is 0");
        }



    }

    public Long calculateTotalCost(ObservableList<OrderProductDetailWareHousing> list){
        long totalCost = 0;
        for(OrderProductDetailWareHousing items: list){
            totalCost += items.getTotal();
        }
        tfTotalCost.setText(String.valueOf(totalCost));
        return totalCost;
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


    public Long getTotal(){
        return Long.parseLong(tfTotalCost.getText());
    }

    public Long getDebt(){
        Long debt = Long.parseLong(tfPaid.getText()) - getTotal();
        if(debt < 0){
            return Math.abs(debt);
        }
        return 0L;
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
        stage.setTitle("Add customer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addProductByBarcodeReader(){
        tfBarcode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //String subString = t1.substring(0, t1.length()-1);
                //Will you when use barcode scanner

                if(t1.length() >= 12){
                    ProductDAO dao = new ProductDAO();
                    Product selectedProduct = dao.findByBarcode(t1);
                    Long amount = (long) 1;
                    Long salePrice = null;

                    OrderProductDetailWareHousing object = new OrderProductDetailWareHousing();

                    object.setProductID(selectedProduct.getId());
                    object.setProductName(selectedProduct.getName());
                    object.setAmount(amount);
                    object.setPrice(selectedProduct.getPrice());
                    object.setProductID(selectedProduct.getId());
                    object.setProductName(selectedProduct.getName());
                    object.setAmount(amount);
                    object.setPrice(selectedProduct.getPrice());

                    if(checkDuplicateProduct(object, orderProductList) == true){
                        orderProductList.add(object);
                        tbProductList.refresh();
                        setDataToTable(orderProductList);

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
                        }

                    }else{
                        for(int i = 0; i < orderProductList.size(); i++){
                            if(object.getProductID() == orderProductList.get(i).getProductID()){
                                orderProductList.get(i).setAmount(object.getAmount() + orderProductList.get(i).getAmount());
                                orderProductList.get(i).setTotal(object.getTotal() + orderProductList.get(i).getTotal());

                            }
                        }
                        tbProductList.refresh();
                        setDataToTable(orderProductList);
                    }


                    calculateTotalCost(orderProductList);
                    setDataToTable(orderProductList);
                    Platform.runLater(() -> {
                        tfBarcode.clear();
                    });
                }else if(t1.length() < 12){
                }

            }
        });
    }

    public boolean checkDuplicateProduct(OrderProductDetailWareHousing object, ObservableList<OrderProductDetailWareHousing> obsList){
        for (int i = 0; i < obsList.size(); i++){
            if(object.getProductID() == obsList.get(i).getProductID()){
                return false;
            }
        }
        return true;
    }

    public void clearInfo(){
        cbCustomerID.setPromptText("Please select customer");
        tfCustomerName.clear();
        cbProductName.setPromptText("Please select product");
        tfTotalCost.clear();
        tfPaid.clear();
        tfChange.clear();
        orderProductList.clear();
        tfVoucherCode.clear();
        lbVoucherInfo.setText("");
        setDataToTable(orderProductList);
    }

    public void setDataForCBSearchType(){
        ObservableList<String> observableList = FXCollections.observableArrayList("Name", "Phone");
        cbSearchType.setItems(observableList);
    }

    @FXML
    void searchCustomer(ActionEvent event) {
        AlertBox alertBox = new AlertBox();
        if(cbSearchType.getSelectionModel().getSelectedItem().equalsIgnoreCase("Name")){
            String customerName = tfSearchCustomer.getText();
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customerList = customerDAO.findBySalesmanIDAndCustomer(getSalesman().getId(),customerName);
            if(customerList.size() == 0){
                alertBox.warningAlert("Unknown Customer", "You can add this new customer");
            }else{
                alertBox.warningAlert("Customer have exist", "You don't need to add this customer");
            }
        }else if(cbSearchType.getSelectionModel().getSelectedItem().equals("Phone")){
            String phone = tfSearchCustomer.getText();
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customerList = customerDAO.findBySalesmanIDAndCustomerPhone(getSalesman().getId(),phone);
            if(customerList.size() == 0){
                alertBox.warningAlert("Unknown Customer", "You can add this new customer");
            }else{
                alertBox.warningAlert("Customer have exist", "You don't need to add this customer");
            }
        }else if(cbSearchType.getSelectionModel().getSelectedItem().equals(null)){
            alertBox.warningAlert("Cannot Search", "Please select search Type");
        }
    }

    public void voucherOption(){
            tfVoucherCode.textProperty().addListener((obs, oldText, newText) -> {
                if(newText.length() >= 9){
                    PaymentDAO paymentDAO = new PaymentDAO();
                    Payment payment = paymentDAO.findByVoucherCode(Long.parseLong(newText));
                    if(payment != null){

                        Long money = payment.getMoney();
                        lbVoucherInfo.setText("Money Reduction: " + money);
                        Long total = getTotal() - money;
                        if(total > 0){
                            tfTotalCost.setText(total.toString());
                        }
                    }
                }
                if(newText.length() < 9){
                    lbVoucherInfo.setText("");
                    tfTotalCost.setText(calculateTotalCost(orderProductList).toString());
                }
            });

    }

    public void removeProduct(){
        tbProductList.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                OrderProductDetailWareHousing object = tbProductList.getSelectionModel().getSelectedItem();
                if(keyEvent.getCode() == KeyCode.DELETE){
                    orderProductList.remove(object);
                    tfTotalCost.setText(calculateTotalCost(orderProductList).toString());
                }
            }
        });
    }
}

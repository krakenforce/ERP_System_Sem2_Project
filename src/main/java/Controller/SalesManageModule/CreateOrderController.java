package Controller.SalesManageModule;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.EntityCombination.OrderProductDetailWareHousing;
import Services.Hibernate.entity.*;
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
    private TextField tfCustomerName;

    @FXML
    private DatePicker dpCurrentDate;

    @FXML
    private ComboBox<Long> cbCustomerID;

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
    private TableColumn<?, ?> clTotal;

    @FXML
    private TableColumn<?, ?> clEnough;

    Product product;
    Long amount;
    private ObservableList<OrderProductDetailWareHousing> orderProductList;
    private Salesman salesman;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        for(int i = 0; i < orderProductList.size(); i++){
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
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        clEnough.setCellValueFactory(new PropertyValueFactory<>("enough"));
        tbProductList.setItems(obsList);

    }

    public Date getDate(){
        LocalDate localDate = dpCurrentDate.getValue();
        return Date.valueOf(localDate);
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
}

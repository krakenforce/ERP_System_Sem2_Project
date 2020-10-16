package Controller.StatisticModule;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.EntityCombination.CustomerTotalPayment;
import Services.Hibernate.EntityCombination.TradeDiscountCustomer;
import Services.Hibernate.EntityCombination.TradeDiscountPayment;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerTradeStatisticController implements Initializable {

    @FXML
    private BarChart<String, Number> bcTradeDiscount;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<CustomerTotalPayment> tbCusInfo;

    @FXML
    private TableColumn<?, ?> clCusID;

    @FXML
    private TableColumn<?, ?> clCusName;

    @FXML
    private TableColumn<?, ?> clTotalPayment;

    @FXML
    private TableView<TradeDiscountPayment> tbTradeDiscountInfo;

    @FXML
    private TableColumn<?, ?> clTDID;

    @FXML
    private TableColumn<?, ?> clStartDate;

    @FXML
    private TableColumn<?, ?> clEndDate;

    @FXML
    private TableColumn<?, ?> clPayment;

    @FXML
    public void searchTradeDiscount(ActionEvent actionEvent) {

    }

    public ObservableList<CustomerTotalPayment> getTotalPaymentCustomer(){
        bcTradeDiscount.getData().clear();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();
        PaymentDAO paymentDAO = new PaymentDAO();
        ObservableList<CustomerTotalPayment> observableList = FXCollections.observableArrayList();

        for(Customer customer : customerList){
            Long totalPayment = paymentDAO.totalPayment(customer.getId());
            if(totalPayment > 0){
                CustomerTotalPayment combination = new CustomerTotalPayment();
                combination.setCustomerID(customer.getId());
                combination.setCustomerName(customer.getName());
                combination.setTotalPayment(totalPayment);
                observableList.add(combination);
                initLineChart(customer.getName(),totalPayment);
            }


        }
        return observableList;
    }

    public void setDataToTableCustomerInfo(ObservableList observableList){
        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clTotalPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

        tbCusInfo.setItems(observableList);

    }

    public void setDataToTableTradeDiscount(){
        PaymentDAO paymentDAO = new PaymentDAO();

        tbCusInfo.setOnMouseClicked( event -> {
            ObservableList<TradeDiscountPayment> observableList = FXCollections.observableArrayList();
            if(event.getClickCount() == 2){
                CustomerTotalPayment customerTotalPayment = tbCusInfo.getSelectionModel().getSelectedItem();

                List<Payment> paymentList = paymentDAO.getPaymentByCustomer(customerTotalPayment.getCustomerID());
                for(Payment payment: paymentList){
                    TradeDiscountPayment combination = new TradeDiscountPayment();
                    combination.setTradeDiscountID(payment.getTradeDiscounts().getId());
                    combination.setPayment(payment.getMoney());
                    combination.setStartDate(payment.getTradeDiscounts().getDateStars());
                    combination.setEndDate(payment.getTradeDiscounts().getDateEnd());
                    observableList.add(combination);
                }
            }
            clTDID.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountID"));
            clStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            clEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            clPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
            tbTradeDiscountInfo.setItems(observableList);
        });
    }

    public void initLineChart(String customerName, Long totalPayment){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(customerName,totalPayment));

        bcTradeDiscount.getData().addAll(series);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTableCustomerInfo(getTotalPaymentCustomer());
        setDataToTableTradeDiscount();
    }
}

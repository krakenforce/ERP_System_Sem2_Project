package Controller.StatisticModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.EntityCombination.CustomerTotalPayment;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerTradeStatisticController implements Initializable {

    @FXML
    private BarChart<String, Number> bcTradeDiscount;

    @FXML
    private TextField tfSearch;

    private TableView<CustomerTotalPayment> tbCusInfo;

    private TableColumn<CustomerTotalPayment, Long> clCusID;

    private TableColumn<CustomerTotalPayment, String> clCusName;

    private TableColumn<CustomerTotalPayment, Long> clTotalPayment;

    private TableView<TradeDiscountPayment> tbTradeDiscountInfo;

    private TableColumn<TradeDiscountPayment, Long> clTDID;

    private TableColumn<TradeDiscountPayment, Date> clStartDate;

    private TableColumn<TradeDiscountPayment, Date> clEndDate;

    private TableColumn<TradeDiscountPayment, Long> clPayment;

    @FXML
    private Pagination pgCusInfo;

    @FXML
    private Pagination pgTradeDiscountInfo;

    PaginationService<CustomerTotalPayment> customerTotalPaymentPaginationService = new PaginationService<>();
    PaginationService<TradeDiscountPayment> tradeDiscountPaymentPaginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpCusInfoTable();
        setUpTradeDiscountInfo();

        setUpPaginationCusInfo(getTotalPaymentCustomer());
        setDataToTableTradeDiscount();
    }

    public void setUpPaginationCusInfo( ObservableList<CustomerTotalPayment> observableList){
        customerTotalPaymentPaginationService.setPagination(pgCusInfo);
        customerTotalPaymentPaginationService.setTableView(tbCusInfo);
        customerTotalPaymentPaginationService.setSopt(10);
        List<CustomerTotalPayment> list = observableList.stream().collect(Collectors.toList());
        customerTotalPaymentPaginationService.createPagination(list);
    }

    public void setUpPaginationTradeDiscount( ObservableList<TradeDiscountPayment> observableList){
        tradeDiscountPaymentPaginationService.setPagination(pgTradeDiscountInfo);
        tradeDiscountPaymentPaginationService.setTableView(tbTradeDiscountInfo);
        tradeDiscountPaymentPaginationService.setSopt(10);
        List<TradeDiscountPayment> list = observableList.stream().collect(Collectors.toList());
        tradeDiscountPaymentPaginationService.createPagination(list);
    }

    public void setUpCusInfoTable(){
        tbCusInfo = new TableView<CustomerTotalPayment>();
        clCusID = new TableColumn<CustomerTotalPayment, Long>("Customer ID");
        clCusName = new TableColumn<CustomerTotalPayment, String>("Customer Name");
        clTotalPayment = new TableColumn<CustomerTotalPayment, Long>("Total Payment");


        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clTotalPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

        tbCusInfo.getColumns().addAll(clCusID, clCusName, clTotalPayment);
    }

    public void setUpTradeDiscountInfo(){
        tbTradeDiscountInfo = new TableView<TradeDiscountPayment>();
        clTDID = new TableColumn<TradeDiscountPayment, Long>("TD ID");
        clStartDate = new TableColumn<TradeDiscountPayment, Date>("Start date");
        clEndDate = new TableColumn<TradeDiscountPayment, Date>("End date");
        clPayment = new TableColumn<TradeDiscountPayment, Long>("Payment");

        clTDID.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountID"));
        clStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        clEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));


        tbTradeDiscountInfo.getColumns().addAll(clTDID, clStartDate, clEndDate, clPayment);
    }




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

            setUpPaginationTradeDiscount(observableList);
        });
    }

    public void initLineChart(String customerName, Long totalPayment){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(customerName,totalPayment));

        bcTradeDiscount.getData().addAll(series);
    }
    public void LamMoi(){

    }



}

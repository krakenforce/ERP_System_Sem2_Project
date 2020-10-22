package Controller.SalesManageModule;

import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.EntityCombination.PaymentTradeCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentManageController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<PaymentTradeCustomer> tbPaymentTable;

    @FXML
    private TableColumn<?, ?> clPaymentID;

    @FXML
    private TableColumn<?, ?> clDateOfPay;

    @FXML
    private TableColumn<?, ?> clMoney;

    @FXML
    private TableColumn<?, ?> clTradeDisName;

    @FXML
    private TableColumn<?, ?> clLimit;

    @FXML
    private TableColumn<?, ?> clStartDay;

    @FXML
    private TableColumn<?, ?> clEndDay;

    @FXML
    private TableColumn<?, ?> clCusID;

    @FXML
    private TableColumn<?, ?> clCusName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableItems(getObservableList());
    }

    public ObservableList<PaymentTradeCustomer> getObservableList(){
        ObservableList<PaymentTradeCustomer> obsList = FXCollections.observableArrayList();
        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> paymentList = paymentDAO.getAll();
        Long paymentID,moneyLimit,customerID, money;
        String tradeDiscountName, customerName;
        Date paymentDate, startDate, endDate;

        for(Payment items : paymentList){
            paymentID = items.getId();
            moneyLimit = items.getTradeDiscounts().getLimitMoney();
            customerID = items.getCustomer().getId();
            money = items.getMoney();
            tradeDiscountName = items.getTradeDiscounts().getName();
           // customerName = items.getTradeDiscounts().getCustomer().getName();
            paymentDate = items.getDate();
            startDate = items.getTradeDiscounts().getDateStars();
            endDate = items.getTradeDiscounts().getDateEnd();

            //set parameters for PaymentTradeCustomer combination object
            PaymentTradeCustomer combination = new PaymentTradeCustomer();
//
//            //set value for object
            combination.setPaymentId(paymentID);
            combination.setPaymentDate(paymentDate);
            combination.setMoney(money);
            combination.setTradeDiscountName(tradeDiscountName);
            combination.setMoneyLimit(moneyLimit);
            combination.setStartDate(startDate);
            combination.setEndDate(endDate);
            combination.setCustomerID(customerID);
            combination.setCustomerName(items.getCustomer().getName());


            obsList.add(combination);
        }
        return obsList;
    }

    public void setTableItems(ObservableList obsList){
        clPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        clDateOfPay.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        clMoney.setCellValueFactory(new PropertyValueFactory<>("money"));
        clTradeDisName.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountName"));
        clLimit.setCellValueFactory(new PropertyValueFactory<>("moneyLimit"));
        clStartDay.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        clEndDay.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tbPaymentTable.setItems(obsList);
    }
    public void LamMoi(){

    }

    public void showAll(ActionEvent actionEvent) {
        setTableItems(getObservableList());
    }

    public void searchByDay(ActionEvent actionEvent) {
        setTableItems(getObservableListByDate());
    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public ObservableList<PaymentTradeCustomer> getObservableListByDate(){
        ObservableList<PaymentTradeCustomer> obsList = FXCollections.observableArrayList();
        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> paymentList = paymentDAO.findPaymentByDate(getDay(dpStartDay), getDay(dpEndDay));
        Long paymentID,moneyLimit,customerID, money;
        String tradeDiscountName, customerName;
        Date paymentDate, startDate, endDate;

        for(Payment items : paymentList){
            paymentID = items.getId();
            moneyLimit = items.getTradeDiscounts().getLimitMoney();
            customerID = items.getCustomer().getId();
            money = items.getMoney();
            tradeDiscountName = items.getTradeDiscounts().getName();
            // customerName = items.getTradeDiscounts().getCustomer().getName();
            paymentDate = items.getDate();
            startDate = items.getTradeDiscounts().getDateStars();
            endDate = items.getTradeDiscounts().getDateEnd();

            //set parameters for PaymentTradeCustomer combination object
            PaymentTradeCustomer combination = new PaymentTradeCustomer();
//
//            //set value for object
            combination.setPaymentId(paymentID);
            combination.setPaymentDate(paymentDate);
            combination.setMoney(money);
            combination.setTradeDiscountName(tradeDiscountName);
            combination.setMoneyLimit(moneyLimit);
            combination.setStartDate(startDate);
            combination.setEndDate(endDate);
            combination.setCustomerID(customerID);
            combination.setCustomerName(items.getCustomer().getName());


            obsList.add(combination);
        }
        return obsList;
    }
}

package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class PaymentCreationController {

    @FXML
    private TextField tfCusID;

    @FXML
    private TextField tfCusName;

    @FXML
    private TextField tfTradeID;

    @FXML
    private TextField tfTradeName;

    @FXML
    private TextField tfCusSpent;

    @FXML
    private TextField tfMoneyLimit;

    @FXML
    private TextField tfMoney;

    @FXML
    private DatePicker dpDate;


    DetailOrderCustomer detailOrderCustomer;
    TradeDiscounts tradeDiscounts;
    Customer selectedCustomer;


    @FXML
    public void createPayment(){
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.findByID(detailOrderCustomer.getCustomerID());
        Payment payment = new Payment();
        PaymentDAO paymentDAO = new PaymentDAO();
        payment.setDate(getDay(dpDate));
        payment.setCustomer(customer);
        payment.setTradeDiscounts(tradeDiscounts);
        payment.setMoney(Long.parseLong(tfMoney.getText()));
        payment.setStatus(true);
        payment.setVoucherCode(Long.parseLong("20348082"));

        AlertBox alertBox = new AlertBox();
        if(alertBox.InsertAlert("Create Payment", "Do you want to create this payment") == true){
            paymentDAO.savePayment(payment);
        };
    }

    public void setData(){
        tfCusID.setText(detailOrderCustomer.getCustomerID().toString());
        tfCusName.setText(detailOrderCustomer.getCustomerName());
        tfCusSpent.setText(detailOrderCustomer.getTotalSpent().toString());
        tfMoneyLimit.setText(tradeDiscounts.getLimitMoney().toString());
        tfTradeID.setText(tradeDiscounts.getId().toString());
        tfTradeName.setText(tradeDiscounts.getName());

    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }


    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public DetailOrderCustomer getDetailOrderCustomer() {
        return detailOrderCustomer;
    }

    public void setDetailOrderCustomer(DetailOrderCustomer detailOrderCustomer) {
        this.detailOrderCustomer = detailOrderCustomer;
    }

    public TradeDiscounts getTradeDiscounts() {
        return tradeDiscounts;
    }

    public void setTradeDiscounts(TradeDiscounts tradeDiscounts) {
        this.tradeDiscounts = tradeDiscounts;
    }
}

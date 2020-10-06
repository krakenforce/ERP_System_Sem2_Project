package TestController;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestTradeDiscountController implements Initializable {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfCusName;

    @FXML
    private ComboBox<Long> cbCusID;

    @FXML
    private TextField tfDiscountPercent;

    @FXML
    private TextField tfMoneyLimit;

    @FXML
    private DatePicker dpStart;

    @FXML
    private DatePicker dpEnd;

    @FXML
    void addTradeDiscount(ActionEvent event) {
        TradeDiscounts tradeDiscounts = new TradeDiscounts();
        tradeDiscounts.setName(tfName.getText());
        tradeDiscounts.setDateStars(getStartDate());
        tradeDiscounts.setDateEnd(getEndDate());
        tradeDiscounts.setLimitMoney(Long.parseLong(tfMoneyLimit.getText()));
        tradeDiscounts.setDiscountPercentage(Long.parseLong(tfDiscountPercent.getText()));

        TradeDiscountDAO tradeDiscountDAO = new TradeDiscountDAO();
        tradeDiscountDAO.saveTradeDiscount(tradeDiscounts);
    }

    @FXML
    public void getCustomer(ActionEvent actionEvent) {
        Customer customer = getSelectedCustomer(cbCusID.getSelectionModel().getSelectedItem());
        tfCusName.setText(customer.getName());
    }


    public void getCustomerList(){
        ObservableList<Long> customerList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> list = customerDAO.selectAllCustomer();

        for(Customer items: list){
            customerList.add(items.getId());
        }
        cbCusID.setItems(customerList);
    }
    public Long getCustomerId(){
        return cbCusID.getSelectionModel().getSelectedItem();
    }

    public Customer getSelectedCustomer(Long id){
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer  = customerDAO.findByID(id);
        return customer;
    }


    public Date getStartDate(){
        return Date.valueOf(dpStart.getValue());
    }
    public Date getEndDate(){
        return Date.valueOf(dpEnd.getValue());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerList();
    }


}

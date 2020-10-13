package Controller.module2;

import Boxes.AddCustomerToDiscount;
import Boxes.AlertBox;
import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.DAO.TradeDiscountDaoImpl;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import Utils.Crypto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class AddCustomerToDiscountController implements Initializable {

    public TextField amountBox;
    public Label promptLabel;
    public String promptText = "";
    private AddCustomerToDiscount box;
    public ChoiceBox<Customer> customerChoiceBox;
    private List<Customer> customerData;

    private List<Customer> createData() {
        List<Customer> rs = new ArrayList<>();
        System.out.println("RUNNING");
        if (box != null) {
            Date end = box.tcol.getEnd();
            Date start = box.tcol.getStart();
            Long limit = box.tcol.getLimit();
            CustomerDaoImpl ci = new CustomerDaoImpl();
            List<Customer> cs = ci.getAllCustomers();

            for (Customer c : cs) {
                System.out.println("spent"+ c.totalSpent(start,end));
                if (c.totalSpent(start, end) > limit){
                    rs.add(c);
                    }

            }

        }

//        CustomerDaoImpl ci = new CustomerDaoImpl();
//        List<Customer> cs = ci.getAllCustomers();
        return rs;

    }

    public void setBox(AddCustomerToDiscount addCustomerToDiscount) {
        this.box = addCustomerToDiscount;
    }

    public void giveDiscount(ActionEvent event) {
        Long discountCode = 0L;
        try {

            Long amount = Long.parseLong(amountBox.getText());
            System.out.println(amount);
            TradeDiscountDaoImpl ti = new TradeDiscountDaoImpl();
            CustomerDaoImpl ci = new CustomerDaoImpl();
            PaymentDAO pi = new PaymentDAO();

            TradeDiscounts t = ti.findById(box.tcol.getId());
            Customer c = customerChoiceBox.getSelectionModel().getSelectedItem();


            Payment voucher = new Payment();
            voucher.setCustomer(c);
            voucher.setTradeDiscounts(t);

            Date today = Date.valueOf(LocalDate.now());
            voucher.setDate(today);

            voucher.setMoney(amount);
            voucher.setStatus(true);
            // get random characters for voucher:
            int voucherLength = 5;
            Long voucherCode = (long) Crypto.generateRandomNumber(voucherLength);
            voucher.setVoucherCode(voucherCode);
            pi.savePayment(voucher);

            discountCode = voucher.getVoucherCode();
            customerChoiceBox.getItems().remove(customerChoiceBox.getSelectionModel().getSelectedItem());

        } catch (NumberFormatException e1) {
            System.out.println("invalid amount");
        } catch (Exception e)
        {
            System.out.println("error");
        }
        if (!discountCode.equals(0L)) {
            AlertBox ab = new AlertBox();
            ab.display("Your Discount Code is: " + discountCode + "\nPlease remember!", "Success!");

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void my_initialize() {
        customerData = createData();
        customerChoiceBox.getItems().addAll(FXCollections.observableList(customerData));
        customerChoiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });
        promptText = promptLabel.getText();
    }
}

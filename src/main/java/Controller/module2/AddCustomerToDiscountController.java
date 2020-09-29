package Controller.module2;

import Boxes.AddCustomerToDiscount;
import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.DAO.TradeDiscountDaoImpl;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class AddCustomerToDiscountController implements Initializable {

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
        try {
            TradeDiscountDaoImpl ti = new TradeDiscountDaoImpl();
            CustomerDaoImpl ci = new CustomerDaoImpl();

            TradeDiscounts t = ti.findById(box.tcol.getId());
            Customer c = customerChoiceBox.getSelectionModel().getSelectedItem();
            Set<TradeDiscounts> tradeDiscounts = new HashSet<>();
            tradeDiscounts.add(t);
            c.setTradeDiscountsSet(tradeDiscounts);

            ci.updateCustomer(c);
            t.setCustomer(customerChoiceBox.getSelectionModel().getSelectedItem());
            ti.updateTradeDiscount(t);
            System.out.println("success");
            customerChoiceBox.getItems().remove(customerChoiceBox.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("error");
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

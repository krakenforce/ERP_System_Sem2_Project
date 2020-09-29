package Controller.module2;

import Services.Hibernate.DAO.*;
import Services.Hibernate.entity.*;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.apache.tools.ant.taskdefs.condition.Or;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class PurchaseOrderController implements Initializable {

    CustomerController customerController;

    SimpleLongProperty sum = new SimpleLongProperty(0L);
    SimpleLongProperty discount = new SimpleLongProperty(0L);
    Long previous_discount = 0L;

    ProductDaoImpl pi = new ProductDaoImpl();
    SalesManDaoImpl si = new SalesManDaoImpl();
    CustomerDaoImpl ci = new CustomerDaoImpl();
    public Label promtText;
    public ChoiceBox<Product> productChoiceBox;
    public ChoiceBox<Salesman> salesmanChoiceBox;
    public ChoiceBox<Customer> customerChoiceBox;
    public TextField quantityTextField;
    public TableView<Item> itemsTable;
    public CheckBox customerCheckBox;
    public CheckBox paidCheckBox;
    public TextField customerNameField;
    public TextField phoneTextField;
    public Label nameLabel;
    public Label phoneLabel;
    public Label totalLabel;
    public Label sumAmount;
    public Label customerLabel;
    public Label discountAmount;

    public TableColumn<Item, Long> totalCol;
    public TableColumn<Item, Long> qtyCol;
    public TableColumn<Item, Long> priceCol;
    public TableColumn<Item, String> nameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // bind sum & discount text to the variables
        sumAmount.textProperty().bind(sum.asString());
        discountAmount.textProperty().bind(discount.asString().concat("%"));

        List<Salesman> sms = si.getAllSalesman();
        salesmanChoiceBox.setItems(FXCollections.observableList(sms));
        salesmanChoiceBox.setConverter(new StringConverter<Salesman>() {
            @Override
            public String toString(Salesman salesman) {
                return salesman.getName();
            }

            @Override
            public Salesman fromString(String s) {
                return null;
            }
        });

        salesmanChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(salesmanChoiceBox.getValue().getId());
            }
        });

        List<Product> ps = pi.getAllProducts();
        productChoiceBox.setItems(FXCollections.observableList(ps));
        productChoiceBox.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product.getName();
            }

            @Override
            public Product fromString(String s) {
                return null;
            }
        });

        List<Customer> cs = ci.getAllCustomers();
        customerChoiceBox.setItems(FXCollections.observableList(cs));
        customerChoiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getName() + "- phone: " + customer.getPhone();
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });
        customerChoiceBox.setOnAction(event -> {
            // check to calculate discount
            Long id = customerChoiceBox.getValue().getId();
            Customer c = ci.findByID(id);
            Set<TradeDiscounts> tradeDiscounts = c.getTradeDiscountsSet();
            List<TradeDiscounts>ts = ci.getAllDiscounts(id);
            Long percentDiscount = 0L;
            if (tradeDiscounts.size() > 0) {
                percentDiscount = tradeDiscounts.iterator().next().getDiscountPercentage();
            }
            discount.set(percentDiscount);
            calculateSum();

        });


        itemsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("qty"));
        itemsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        itemsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        itemsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("total"));


        // context menu for row:
        MenuItem mi = new MenuItem("remove");
        mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Item i = itemsTable.getSelectionModel().getSelectedItem();
                itemsTable.getItems().remove(i);
                calculateSum();
            }
        });

        ContextMenu cm = new ContextMenu();
        cm.getItems().add(mi);
        itemsTable.setContextMenu(cm);


    }

    public void generateBtn(ActionEvent event) {
        Customer c = null;
        // if new customer:
        if (customerCheckBox.isSelected()) {
            String name = customerNameField.getText();
            String phone = phoneTextField.getText();
            if (name.isBlank() || phone.isBlank()) {
                promtText.setText("Name or Phone of New-Customer is invalid!");
                return;
            }
            Long salesman = 0L;
            if (!(salesmanChoiceBox.getValue().equals(null))) {
                salesman = salesmanChoiceBox.getSelectionModel().getSelectedItem().getId();

            }

            Long cid  = ci.addCustomer(name, phone, salesman);
            c = ci.findByID(cid);
        } else {
            c = customerChoiceBox.getValue();
        }

        // new order detail:
        DetailOrderDAO di = new DetailOrderDAO();
        OrderDAO oi = new OrderDAO();
        ProductDaoImpl ip = new ProductDaoImpl();


        DetailOrder order_d = new DetailOrder();
        Date today = Date.valueOf(LocalDate.now());
        order_d.setCustomer(c);
        order_d.setDate(today);
        System.out.println("order date" + today.toString());
        order_d.setPay(paidCheckBox.isSelected()? true : false);

        di.saveDetailOrder(order_d);

        // make the order for each product
        Salesman sm = salesmanChoiceBox.getValue();
        Set<Order> orders = new HashSet<>();
        for (Item item : itemsTable.getItems()) {
            Order order = new Order();
            Long amount = item.getQty();
            order.setDetailOrder(order_d);
            order.setAmount(amount);
            order.setSalesman(sm);
            order.setProduct(ip.findById(item.getId()));
            oi.saveOrder(order);
            orders.add(order);
            // is Enough ?? -- don't know how to calculate, need a method
        }

        order_d.setOrderSet(orders);
       di.updateDetailOrder(order_d);

        // update the customer pane:
        if (customerController != null) {
            customerController.searchByName(new ActionEvent());
        }

    }

    public void addToItems(ActionEvent event) {
        Long qty = 0L;
        try {
            qty = Long.parseLong(quantityTextField.getText());
            if (productChoiceBox.getValue() != null && qty >= 0L ) {
                Long id = productChoiceBox.getValue().getId();
                Long price = productChoiceBox.getValue().getPrice();
                String name = productChoiceBox.getValue().getName();
                for (Item item : itemsTable.getItems()) {

                    if ( item.getId().equals(id)) {
                        itemsTable.getItems().remove(item);
                        qty += item.getQty();
                        break;
                    }
                }
                Long total = price * qty;
                Item i = new Item(id, qty, name, price, total);

                itemsTable.getItems().add(i);

                calculateSum();
            }

        } catch (NumberFormatException e) {
            System.out.println("must be a number larger than 0");
            return;
        }
    }

    private void calculateSum() {
        Long s = 0L;
        for (Item item : itemsTable.getItems()) {
            s += item.getTotal();
        }
        s = (s * (100L - discount.getValue())) / 100L;
        sum.set(s);

    }


    public void checkSelected(MouseEvent event) {
        if (customerCheckBox.isSelected()) {
            // reset Discount to 0:
            previous_discount = discount.get();
            discount.set(0L);
            customerNameField.setDisable(false);
            phoneTextField.setDisable(false);
            nameLabel.setDisable(false);
            phoneLabel.setDisable(false);
            customerChoiceBox.setDisable(true);
            customerLabel.setDisable(true);
        } else {

            discount.set(previous_discount);

            customerNameField.setDisable(true);
            phoneTextField.setDisable(true);
            nameLabel.setDisable(true);
            phoneLabel.setDisable(true);
            customerChoiceBox.setDisable(false);
            customerLabel.setDisable(false);
        }

    }

    public void setCustomerController(CustomerController ctrl) {
        this.customerController = ctrl;
    }
}

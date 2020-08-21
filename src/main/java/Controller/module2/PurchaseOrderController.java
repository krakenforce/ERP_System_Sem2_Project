package Controller.module2;

import Repositories.ProductDao;
import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.DAO.ProductDaoImpl;
import Services.Hibernate.DAO.SalesManDaoImpl;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseOrderController  implements Initializable {

    ProductDaoImpl pi = new ProductDaoImpl();
    SalesManDaoImpl si = new SalesManDaoImpl();
    CustomerDaoImpl ci = new CustomerDaoImpl();

    public ChoiceBox<Product> productChoiceBox;
    public ChoiceBox<Salesman> salesmanChoiceBox;
    public ChoiceBox<Customer> customerChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    public void generateBtn(ActionEvent event) {
    }

    public void addToItems(ActionEvent event) {
    }

}

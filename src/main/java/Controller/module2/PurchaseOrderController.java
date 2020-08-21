package Controller.module2;

import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.DAO.ProductDaoImpl;
import Services.Hibernate.DAO.SalesManDaoImpl;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Salesman;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
        productChoiceBox.setItems((ObservableList<Product>) pi.getAllProducts());


    }
    public void checkAmount(MouseEvent event) {
        System.out.println("sdflksdj");
    }

    public void generateBtn(ActionEvent event) {
    }

    public void addToItems(ActionEvent event) {
    }

}

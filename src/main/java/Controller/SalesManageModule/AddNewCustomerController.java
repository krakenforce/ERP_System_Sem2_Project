package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Salesman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewCustomerController {

    @FXML
    private TextField tfSalesmanID;

    @FXML
    private TextField tfSalesmanName;

    @FXML
    private TextField tfCustomerName;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private Button btnAddCustomer;

    Salesman salesman;

    @FXML
    void AddCustomerBySalesman(ActionEvent event) {
        AlertBox alertBox = new AlertBox();
        CustomerDAO dao = new CustomerDAO();
        Customer customer = new Customer();
        customer.setSalesman(getSalesman());
        customer.setName(tfCustomerName.getText());
        customer.setAddress(tfAddress.getText());
        customer.setPhone(tfPhone.getText());

        if(alertBox.InsertAlert("Insert new customer", "Do you really want to insert this customer") == true){
            dao.saveCustomer(customer);
            Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
            stage.close();
        }

    }

    public void setSalesmanData(){
        tfSalesmanID.setText(getSalesman().getId().toString());
        tfSalesmanName.setText(getSalesman().getName());
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }
}

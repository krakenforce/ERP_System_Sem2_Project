package Controller;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerSalerListController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfID;

    @FXML
    private Pagination pgPage;

    @FXML
    private TableView<Customer> tbCusInfo;

    @FXML
    private TableColumn<Long, Customer> clCusID;

    @FXML
    private TableColumn<String, Customer> clCusName;

    @FXML
    private TableColumn<String, Customer> clCusPhone;

    @FXML
    private TableColumn<String, Customer> clCusAddress;



    public void addCustomer(ActionEvent actionEvent) {
        SalesManDAO salesmanDAO = new SalesManDAO();
        Salesman selectedSalesman = salesmanDAO.findById(Long.parseLong(tfID.getText()));
        Customer customer = new Customer();
        customer.setSalesman(selectedSalesman);
        customer.setName(tfName.getText());
        customer.setPhone(tfPhone.getText());
        customer.setAddress(tfAddress.getText());

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.saveCustomer(customer);

        tbCusInfo.getItems().clear();
        tbCusInfo.setItems(getDataCustomer(Long.parseLong(tfID.getText())));
    }

    public void updateCustomer(ActionEvent actionEvent) {


    }

    public void deleteCustomer(ActionEvent actionEvent) {

    }

    public void cancelAction(ActionEvent actionEvent) {

    }

    public void openCustomerList(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSalesmanInfo(Long id){
        tfID.setText(id.toString());
    }

    public ObservableList<Customer> getDataCustomer(Long salesmanID){
        ObservableList<Customer> list = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.findBySalesmanID(salesmanID);
        for(Customer items: customerList){
            list.add(new Customer(items.getId(), items.getName(), items.getPhone(), items.getAddress()));
        }
        clCusID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clCusPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbCusInfo.setItems(list);
        return list;
    }




}

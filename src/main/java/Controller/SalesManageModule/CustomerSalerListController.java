package Controller.SalesManageModule;

import Repositories.Function;
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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerSalerListController implements Initializable,Function{

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectRowTable();
    }

    private void selectRowTable() {
        tbCusInfo.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                Customer customer = getSelectedCustomer();
                tfName.setText(customer.getName());
                tfPhone.setText(customer.getPhone());
                tfAddress.setText(customer.getAddress());
            }
        });
    }

    private Customer getSelectedCustomer() {
        Customer selectedCustomer = null;
        if (tbCusInfo.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = tbCusInfo.getSelectionModel().getSelectedItem();
        }
        return selectedCustomer;
    }

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

        refreshTable(tbCusInfo);
    }

    public void updateCustomer(ActionEvent actionEvent) {
        SalesManDAO salesmanDAO = new SalesManDAO();
        Salesman selectedSalesman = salesmanDAO.findById(Long.parseLong(tfID.getText()));
        Customer customer = new Customer();
        customer.setId(getSelectedCustomer().getId());
        customer.setSalesman(selectedSalesman);
        customer.setName(tfName.getText());
        customer.setAddress(tfAddress.getText());
        customer.setPhone(tfPhone.getText());

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.updateCustomer(customer);

        //refresh table after adding
        refreshTable(tbCusInfo);

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


    @Override
    public void refreshTable(TableView tableName) {
        tableName.getItems().clear();
        tableName.setItems(getDataCustomer(Long.parseLong(tfID.getText())));
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.deleteCustomer(getSelectedCustomer());

        refreshTable(tbCusInfo);
    }

    public void cancelAction(ActionEvent actionEvent) {
        tfName.clear();
        tfPhone.clear();
        tfAddress.clear();
    }

    public void setSalesmanInfo(Long id){
        tfID.setText(id.toString());
    }

}

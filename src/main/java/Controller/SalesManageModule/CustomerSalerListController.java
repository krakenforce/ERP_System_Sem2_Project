package Controller.SalesManageModule;

import NodeService.PaginationService;
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

    private TableView<Customer> tbCusInfo;

    private TableColumn<Customer, Long> clCusID;

    private TableColumn<Customer, String> clCusName;

    private TableColumn<Customer, String> clCusPhone;

    private TableColumn<Customer, String> clCusAddress;

    private Pagination pgCustomerList;

    PaginationService<Customer> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
        selectRowTable();
    }

    public void setUpTableView() {
        tbCusInfo = new TableView<Customer>();
        clCusID = new TableColumn<Customer, Long>("Customer ID");
        clCusName = new TableColumn<Customer, String>("Customer Name");
        clCusPhone = new TableColumn<Customer, String>("Phone");
        clCusAddress = new TableColumn<Customer, String>("Address");

        clCusID.setCellValueFactory(new PropertyValueFactory<Customer, Long>("id"));
        clCusName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        clCusPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        clCusAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));

        tbCusInfo.getColumns().addAll(clCusID,clCusName, clCusPhone, clCusAddress);
    }

    public void setUpPagination(Long salesmanID){
        paginationService.setPagination(pgCustomerList);
        paginationService.setTableView(tbCusInfo);
        paginationService.setSopt(10);
        paginationService.createPagination(getDataCustomer(salesmanID));
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

package Controller.CustomerManageModule;

import Controller.SalesManageModule.ReceiptByTypeController;
import NodeService.PaginationService;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerListController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Pagination pgCustomerList;

    private TableView<DetailOrderCustomer> tbCustomerList;

    private TableColumn<DetailOrderCustomer, Long> clID;
    private TableColumn<DetailOrderCustomer, String> clName;
    private TableColumn<DetailOrderCustomer, Long> clTotalDebt;
    private TableColumn<DetailOrderCustomer, Long> clTotalSpent;

    PaginationService<DetailOrderCustomer> paginationService = new PaginationService<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        getSelectedCustomer();
    }

    public void setUpTable(){
        tbCustomerList = new TableView<DetailOrderCustomer>();
        clID = new TableColumn<DetailOrderCustomer, Long>("Customer ID");
        clName = new TableColumn<DetailOrderCustomer, String>("Customer Name");
        clTotalDebt = new TableColumn<DetailOrderCustomer, Long>("Total Debt");
        clTotalSpent = new TableColumn<DetailOrderCustomer, Long>("Total Spent");

        clID.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("customerID"));
        clName.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, String>("customerName"));
        clTotalDebt.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("totalDebt"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("totalSpent"));

        tbCustomerList.getColumns().addAll(clID, clName, clTotalDebt, clTotalSpent);
    }

    public void setUpPagination(ObservableList<DetailOrderCustomer> observableList){
        paginationService.setPagination(pgCustomerList);
        paginationService.setTableView(tbCustomerList);
        paginationService.setSopt(17);
        List<DetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void showAllCustomer(){
        CustomerDAO customerDAO = new CustomerDAO();
        
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
    }




    @FXML
    void searchCustomerByDate(ActionEvent event) {
        setUpPagination(getCustomerListByDate(getDay(dpStartDay), getDay(dpEndDate)));
    }

    @FXML
    void showAll(ActionEvent event) {
        setUpPagination(getCustomerListByAll());
    }

    public ObservableList<DetailOrderCustomer> getCustomerListByDate(Date startDay, Date endDay){
        ObservableList<DetailOrderCustomer> observableList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        for(Customer customer : customerList){
            DetailOrderCustomer object = new DetailOrderCustomer();
            Long totalDebt = detailOrderDAO.sumTotalDebt(customer.getId(),startDay, endDay);
            Long totalSpent = detailOrderDAO.sumTotalSpent(customer.getId(), startDay, endDay);

            if(totalDebt != null || totalSpent != null){
                object.setCustomerID(customer.getId());
                object.setCustomerName(customer.getName());
                object.setTotalDebt(totalDebt);
                object.setTotalSpent(totalSpent);
                observableList.add(object);
            }
        }

        return observableList;
    }

    public ObservableList<DetailOrderCustomer> getCustomerListByAll(){
        ObservableList<DetailOrderCustomer> observableList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        for(Customer customer : customerList){
            DetailOrderCustomer object = new DetailOrderCustomer();
            Long totalDebt = detailOrderDAO.sumTotalDebtByAll(customer.getId());
            Long totalSpent = detailOrderDAO.sumTotalSpentByAll(customer.getId());

            object.setCustomerID(customer.getId());
            object.setCustomerName(customer.getName());
            object.setTotalDebt(totalDebt);
            object.setTotalSpent(totalSpent);
            observableList.add(object);
        }

        return observableList;
    }


    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public void getSelectedCustomer(){
        tbCustomerList.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2){
                DetailOrderCustomer object = tbCustomerList.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Form/CustomerManageModule/CustomerDetailDebtInfo.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Customer Debt Detail");
                    stage.show();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                CustomerDetailDebtInfoController controller = fxmlLoader.getController();
                controller.setUpPagination(controller.getDetailOrderList(object.getCustomerID(), getDay(dpStartDay), getDay(dpEndDate)));
                controller.setDataForLabel("Customer name: " + object.getCustomerName() + " - From " + getDay(dpStartDay).toString() + " To " + getDay(dpEndDate).toString());

            }
        });
    }

    public void LamMoi(){

    }


}

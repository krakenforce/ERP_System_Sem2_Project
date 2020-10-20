package Controller.SalesManageModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.EntityCombination.TradeDiscountCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeDiscountCustomerReachController implements Initializable {

    @FXML
    private TextField tfTDName;

    @FXML
    private Label lblInfo;

    @FXML
    private Pagination pgCustomerList;

    private TableView<DetailOrderCustomer> tbCustomerList;
    private TableColumn<DetailOrderCustomer, Long> clCusID;
    private TableColumn<DetailOrderCustomer, String> clCusName;
    private TableColumn<DetailOrderCustomer, Long> clTotalSpent;
    private TableColumn<DetailOrderCustomer, Void> clCreatePayment;

    PaginationService<TradeDiscountCustomer> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUpTable(){
        tbCustomerList = new TableView<DetailOrderCustomer>();
        clCusID = new TableColumn<DetailOrderCustomer, Long>("Customer ID");
        clCusName = new TableColumn<DetailOrderCustomer, String>("Customer Name");
        clTotalSpent = new TableColumn<DetailOrderCustomer, Long>("Total Spent");
        clCreatePayment = new TableColumn<DetailOrderCustomer, Void>("Function");

        clCusID.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, String>("customerName"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("totalSpent"));


        tbCustomerList.getColumns().addAll(clCusID, clCusName, clTotalSpent);
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

    public void setDataToLabel(String tradeDiscountNames, Date startDate, Date endDate){
        tfTDName.setText(tradeDiscountNames);
        lblInfo.setText("Result is from " + startDate.toString() + " to " + endDate.toString());
    }



}

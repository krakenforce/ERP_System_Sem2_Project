package Controller.StatisticModule;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Salesman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SalesStatisticController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<DetailOrderCustomer> tbSalesmanList;

    @FXML
    private TableColumn<?, ?> clSalesmanID;

    @FXML
    private TableColumn<?, ?> clSalesmanName;

    @FXML
    private TableColumn<?, ?> clInvoiceAmount;

    @FXML
    private TableColumn<?, ?> clTotalSpent;

    @FXML
    private TableColumn<?, ?> clShowDetail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void searchByDay(ActionEvent actionEvent) {
        setDataToTable(getSalesmanList());
    }

    @FXML
    public void showAll(ActionEvent actionEvent) {

    }

    public ObservableList<DetailOrderCustomer> getSalesmanList(){
        ObservableList<DetailOrderCustomer> detailOrderCustomerObservableList = FXCollections.observableArrayList();
        SalesManDAO salesManDAO = new SalesManDAO();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();

        List<Salesman> salesmanList = salesManDAO.selectAll();
        for(Salesman salesman : salesmanList){
            Long count = countCustomerDetailOrder(salesman.getId(), getDay(dpStartDay),getDay(dpEndDay));
            Long totalMoney = calculateTotalMoney(salesman.getId());
            DetailOrderCustomer combination = new DetailOrderCustomer(salesman.getId(),count, salesman.getName(),totalMoney);
            detailOrderCustomerObservableList.add(combination);
        }

        return detailOrderCustomerObservableList;
    }

    public Long countCustomerDetailOrder(Long salesmanID, Date startDate, Date endDate){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        Long detailOrderCount = (long) 0;
        List<Customer> customerList = customerDAO.findBySalesmanID(salesmanID);
        for(Customer customer: customerList){
            detailOrderCount += detailOrderDAO.countDetailOrderByCustomerIDAndDate(customer.getId(),getDay(dpStartDay), getDay(dpEndDay));
        }
        return detailOrderCount;
    }

    public Long calculateTotalMoney(Long salesmanID){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        Long totalMoney = (long) 0;
        List<Customer> customerList = customerDAO.findBySalesmanID(salesmanID);
        for(Customer customer: customerList){
            List<DetailOrder> detailOrderList = detailOrderDAO.getDetailOrderListByDate(customer.getId(),getDay(dpStartDay),getDay(dpEndDay));
            for(DetailOrder detailOrder : detailOrderList){
                totalMoney += detailOrder.getTotal();
            }
        }
        return totalMoney;
    }



    public void setDataToTable(ObservableList observableList){
        clSalesmanID.setCellValueFactory(new PropertyValueFactory<>("salesmanID"));
        clSalesmanName.setCellValueFactory(new PropertyValueFactory<>("salesmanName"));
        clInvoiceAmount.setCellValueFactory(new PropertyValueFactory<>("amountOfInvoice"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<>("total"));

        tbSalesmanList.setItems(observableList);
    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }


}

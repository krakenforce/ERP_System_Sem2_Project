package Controller.StatisticModule;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerListStatisticModuleController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<DetailOrderCustomer> tbCustomerList;

    @FXML
    private TableColumn<?, ?> clCusID;

    @FXML
    private TableColumn<?, ?> clCusName;

    @FXML
    private TableColumn<?, ?> clInvoiceAmount;

    @FXML
    private TableColumn<?, ?> clTotalSpent;

    @FXML
    private TableColumn<?, ?> clShowDetail;

    @FXML
    private BarChart<String, Number> bcCustomerList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable(showAllCustomerList());
    }


    // Bị bug khi reset barchart thì tên bị chồng
    public ObservableList<DetailOrderCustomer> showAllCustomerList(){

        bcCustomerList.getData().clear();

        ObservableList<DetailOrderCustomer> detailOrderCustomerObservableList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();


        for(Customer items: customerList){
            DetailOrderCustomer combination = new DetailOrderCustomer();
            combination.setCustomerID(items.getId());
            combination.setCustomerName(items.getName());
            combination.setCount(countAmountOfDetailOrder(items.getId()));
            combination.setTotalSpent(totalSpentByCustomer(items.getId()));

            initLineChart(items.getName(),totalSpentByCustomer(items.getId()));

            detailOrderCustomerObservableList.add(combination);
        }

        return detailOrderCustomerObservableList;
    }

    public Long countAmountOfDetailOrder(Long customerID){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        Long count = detailOrderDAO.countDetailOrderByCustomerID(customerID);
        return count;
    }

    public Long totalSpentByCustomer(Long customerId){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrdersList = null;
        Long totalSpent = (long) 0;

        detailOrdersList = detailOrderDAO.findByCustomerID(customerId);
        for(DetailOrder detailOrder: detailOrdersList){
            totalSpent += detailOrder.getTotal();
        }
        return totalSpent;
    }

    @FXML
    void searchByDay(ActionEvent event) {
        setDataToTable(getCustomerListByDate());
    }

    @FXML
    void showAll(ActionEvent event) {
        setDataToTable(showAllCustomerList());
        dpStartDay.setValue(null);
        dpEndDay.setValue(null);
    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public ObservableList<DetailOrderCustomer> getCustomerListByDate(){
        bcCustomerList.getData().clear();

        ObservableList<DetailOrderCustomer> detailOrderCustomerObservableList = FXCollections.observableArrayList();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();

        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();

        for(Customer items : customerList){
            DetailOrderCustomer detailOrderCustomer = new DetailOrderCustomer();
            List<DetailOrder> detailOrderList = detailOrderDAO.findByDateRange(getDay(dpStartDay), getDay(dpEndDay), items.getId());
            Long count = detailOrderDAO.countDetailOrderByCustomerIDAndDate(items.getId(),getDay(dpStartDay), getDay(dpEndDay));
            Long totalSpent = 0L;
            for(DetailOrder detailOrder: detailOrderList){
                totalSpent += detailOrder.getTotal();
            }

            detailOrderCustomer.setCustomerID(items.getId());
            detailOrderCustomer.setCustomerName(items.getName());
            detailOrderCustomer.setCount(count);
            detailOrderCustomer.setTotalSpent(totalSpent);

            detailOrderCustomerObservableList.add(detailOrderCustomer);
            initLineChart(items.getName(), totalSpent);
        }

        return detailOrderCustomerObservableList;
    }


    public void setDataToTable(ObservableList observableList){
        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clInvoiceAmount.setCellValueFactory(new PropertyValueFactory<>("count"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<>("totalSpent"));

        tbCustomerList.setItems(observableList);
    }

    public void initLineChart(String customerName, Long amount){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(customerName,amount));

        bcCustomerList.getData().addAll(series);
    }


}

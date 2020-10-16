package Controller.StatisticModule;

import Boxes.AlertBox;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

    @FXML
    private BarChart<String, Number> bcSaleStatis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbSalesmanList.setPlaceholder(new Label("Please select start day & end day to see result"));

    }

    @FXML
    public void searchByDay(ActionEvent actionEvent) {
        if(dpStartDay.getValue() == null || dpEndDay.getValue() == null){
            AlertBox alertBox = new AlertBox();
            alertBox.warningAlert("Please select date", "Cannot search because you have not selected date yet");
        }else{
            setDataToTable(getSalesmanStatisticsInfo());
        }
    }

    @FXML
    public void showAll(ActionEvent actionEvent) {

    }

    public ObservableList<DetailOrderCustomer> getSalesmanStatisticsInfo() {

        bcSaleStatis.getData().clear();
        //Get all salesman
        SalesManDAO salesManDAO = new SalesManDAO();
        List<Salesman> salesmanList = salesManDAO.selectAll();
        CustomerDAO customerDAO = new CustomerDAO();
        ObservableList<DetailOrderCustomer> observableList = FXCollections.observableArrayList();
        for (Salesman salesman : salesmanList) {

            DetailOrderCustomer combination = new DetailOrderCustomer();
            combination.setSalesmanID(salesman.getId());
            combination.setSalesmanName(salesman.getName());

            Long amountOfDetailOrder = 0L;
            Long totalSpent = 0L;
            List<Customer> customerList = customerDAO.findBySalesmanID(salesman.getId());
            for (Customer customer : customerList) {
                amountOfDetailOrder += countAmountOfDetailOrder(customer.getId(), getDay(dpStartDay), getDay(dpEndDay));
                totalSpent += calculateTotalSpent(customer.getId(), getDay(dpStartDay), getDay(dpEndDay));
            }
            combination.setAmountOfInvoice(amountOfDetailOrder);
            combination.setTotal(totalSpent);
            observableList.add(combination);
            initLineChart(salesman.getName(), totalSpent);
        }
        return observableList;

    }

    public Long countAmountOfDetailOrder(Long customerID, Date startDate, Date endDate) {
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.getDetailOrderListByDate(customerID, startDate, endDate);
        Long detailOrderCounter = 0L;
        detailOrderCounter = detailOrderDAO.countDetailOrderByCustomerIDAndDate(detailOrderList);
        return detailOrderCounter;
    }

    public Long calculateTotalSpent(Long customerID, Date startDate, Date endDate) {
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        Long totalSpent = 0L;

        totalSpent = detailOrderDAO.sumTotalSpent(customerID, startDate, endDate);
        if(totalSpent != null){
            return totalSpent;
        }
        return 0L;
    }


    public void setDataToTable(ObservableList observableList) {
        clSalesmanID.setCellValueFactory(new PropertyValueFactory<>("salesmanID"));
        clSalesmanName.setCellValueFactory(new PropertyValueFactory<>("salesmanName"));
        clInvoiceAmount.setCellValueFactory(new PropertyValueFactory<>("amountOfInvoice"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<>("total"));

        tbSalesmanList.setItems(observableList);
    }

    public Date getDay(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public void initLineChart(String salesmanName, Long sales) {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(salesmanName, sales));

        bcSaleStatis.getData().addAll(series);
    }


}

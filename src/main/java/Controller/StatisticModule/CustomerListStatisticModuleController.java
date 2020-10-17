package Controller.StatisticModule;

import NodeService.PaginationService;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerListStatisticModuleController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    private TableView<DetailOrderCustomer> tbCustomerList;

    private TableColumn<DetailOrderCustomer, Long> clCusID;

    private TableColumn<DetailOrderCustomer, String> clCusName;

    private TableColumn<DetailOrderCustomer, Long> clInvoiceAmount;

    private TableColumn<DetailOrderCustomer, Long> clTotalSpent;


    @FXML
    private Pagination pgCustomerStatistic;

    @FXML
    private BarChart<String, Number> bcCustomerList;

    PaginationService<DetailOrderCustomer> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
        setUpPagination(showAllCustomerList());
    }

    public void setUpPagination(ObservableList<DetailOrderCustomer> observableList){
        paginationService.setPagination(pgCustomerStatistic);
        paginationService.setTableView(tbCustomerList);
        paginationService.setSopt(10);
        List<DetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void setUpTableView(){
        tbCustomerList = new TableView<DetailOrderCustomer>();
        clCusID = new TableColumn<DetailOrderCustomer, Long>("Customer ID");
        clCusName = new TableColumn<DetailOrderCustomer, String>("Customer Name");
        clInvoiceAmount = new TableColumn<DetailOrderCustomer, Long>("Invoice Amount");
        clTotalSpent = new TableColumn<DetailOrderCustomer, Long>("Total Spent");

        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clInvoiceAmount.setCellValueFactory(new PropertyValueFactory<>("count"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<>("totalSpent"));

        tbCustomerList.getColumns().addAll(clCusID, clCusName, clInvoiceAmount, clTotalSpent);
    }

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

    public Long totalSpentByCustomer(Long customerID){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrdersList = null;
        Long totalSpent = (long) 0;

        detailOrdersList = detailOrderDAO.findByCustomerID(customerID);
        for(DetailOrder detailOrder: detailOrdersList){
            totalSpent += detailOrder.getTotal();
        }
        return totalSpent;
    }

    @FXML
    void searchByDay(ActionEvent event) {
        setUpPagination(getCustomerListByDate());
    }

    @FXML
    void showAll(ActionEvent event) {
        setUpPagination(showAllCustomerList());
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
            Long count = detailOrderDAO.countDetailOrderByCustomerIDAndDate(detailOrderList);
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

    public void initLineChart(String customerName, Long amount){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(customerName,amount));

        bcCustomerList.getData().addAll(series);
    }

    public void LamMoi(){

    }


}

package Controller.StatisticModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.EntityCombination.GroupProductDetailStatistic;
import Services.Hibernate.EntityCombination.GroupProductStatis;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductSoldStatisticController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    private TableView<GroupProductDetailStatistic> tbProductList;

    private TableColumn<GroupProductDetailStatistic, Long> clProductID;

    private TableColumn<GroupProductDetailStatistic, String> clProductName;

    private TableColumn<GroupProductDetailStatistic, Long> clTotalSold;

    @FXML
    private Pagination pgProductSold;

    @FXML
    private BarChart<String, Number> bcProductSold;

    PaginationService<GroupProductDetailStatistic> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
        setUpPagination(getAllSoldInfo());
    }

    public void setUpTableView(){
        tbProductList = new TableView<GroupProductDetailStatistic>();
        clProductID = new TableColumn<GroupProductDetailStatistic, Long>("Product ID");
        clProductName = new TableColumn<GroupProductDetailStatistic, String>("Product Name");
        clTotalSold = new TableColumn<GroupProductDetailStatistic, Long>("Total Sold");

        clProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        clProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clTotalSold.setCellValueFactory(new PropertyValueFactory<>("soldAmount"));
        tbProductList.getColumns().addAll(clProductID, clProductName, clTotalSold);
    }

    public void setUpPagination(ObservableList<GroupProductDetailStatistic> observableList){
        paginationService.setPagination(pgProductSold);
        paginationService.setTableView(tbProductList);
        paginationService.setSopt(15);
        List<GroupProductDetailStatistic> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }


    public void searchByDate(ActionEvent actionEvent) {
        showInfoByDate();
    }

    public void showAll(ActionEvent actionEvent) {
        setUpPagination(getAllSoldInfo());
    }

    public ObservableList<GroupProductDetailStatistic> getAllSoldInfo(){
        bcProductSold.getData().clear();

        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getAll();
        ObservableList<GroupProductDetailStatistic> obsList = FXCollections.observableArrayList();

        for(Product product : productList){
            Long soldAmount = orderDAO.countAllProductSold(product.getId());
            if(soldAmount != null){
                GroupProductDetailStatistic combination = new GroupProductDetailStatistic(product.getId(),soldAmount,product.getName() );
                obsList.add(combination);
                initLineChart(product.getName(), orderDAO.countAllProductSold(product.getId()) );
            }
        }
        return obsList;
    }

    public void showInfoByDate(){

        bcProductSold.getData().clear();

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.findByDate(getDate(dpStartDay), getDate(dpEndDay));
        OrderDAO orderDAO = new OrderDAO();
        List<Order> totalOrderList = new ArrayList<>();
        ObservableList<GroupProductDetailStatistic> obsList = FXCollections.observableArrayList();

        for(DetailOrder detailOrder : detailOrderList){
            List<Order> OrderList = orderDAO.findByDetailOrderID(detailOrder.getId());
            for(Order order: OrderList){
                totalOrderList.add(order);
            }
        }

        for(Order order : totalOrderList){
            Long soldAmount = 0L;
            soldAmount = orderDAO.countProductSoldByOrderList(totalOrderList,order.getProduct().getId());

            if(soldAmount != null){
                GroupProductDetailStatistic combination = new GroupProductDetailStatistic(order.getProduct().getId(), soldAmount, order.getProduct().getName());
                if(checkDuplicate(combination, obsList) == true){
                    obsList.add(combination);
                    initLineChart(order.getProduct().getName(), soldAmount);
                }
            }
        }
        setUpPagination(obsList);
    }

    public Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public boolean checkDuplicate(GroupProductDetailStatistic object, ObservableList<GroupProductDetailStatistic> obsList){
        for (int i = 0; i < obsList.size(); i++){
            if(object.getProductID() == obsList.get(i).getProductID()){
                return false;
            }
        }
        return true;
    }


    public void initLineChart(String productName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(productName,totalSold));

        bcProductSold.getData().addAll(series);
    }


}

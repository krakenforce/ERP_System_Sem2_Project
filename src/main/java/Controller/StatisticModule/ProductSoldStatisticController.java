package Controller.StatisticModule;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductSoldStatisticController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<?> tbProductList;

    @FXML
    private TableColumn<?, ?> clProductID;

    @FXML
    private TableColumn<?, ?> clProductName;

    @FXML
    private TableColumn<?, ?> clTotalSold;

    @FXML
    private BarChart<String, Number> bcProductSold;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable(getAllSoldInfo());
    }

    public void searchByDate(ActionEvent actionEvent) {
        showInfoByDate();
    }

    public void showAll(ActionEvent actionEvent) {
        setDataToTable(getAllSoldInfo());
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
        setDataToTable(obsList);
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



    public void setDataToTable(ObservableList observableList){
        clProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        clProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clTotalSold.setCellValueFactory(new PropertyValueFactory<>("soldAmount"));

        tbProductList.setItems(observableList);
    }

    public void initLineChart(String productName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(productName,totalSold));

        bcProductSold.getData().addAll(series);
    }


}

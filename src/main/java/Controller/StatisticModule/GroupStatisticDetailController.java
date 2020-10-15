package Controller.StatisticModule;

import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.EntityCombination.GroupProductDetailStatistic;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GroupStatisticDetailController {

    @FXML
    private Label lbDateInfo;

    @FXML
    private TextField tfFloorLimit;

    @FXML
    private Label lbGroupName;

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

    ObservableList<GroupProductDetailStatistic> obsList = FXCollections.observableArrayList();


    //chỉ xoá mỗi lần 1 cái
    @FXML
    void filter(ActionEvent event) {
        Long limit  = Long.parseLong(tfFloorLimit.getText());
        for(int i = 0; i<obsList.size(); i++){
            if(obsList.get(i).getSoldAmount() < limit){
                obsList.remove(obsList.get(i));
            }
        }
        setDataToTable(obsList);
    }

    @FXML
    void showAll(ActionEvent event) {

    }

    public void setTextForComponent(String text, Date startDay, Date endDay){
        lbGroupName.setText(text);
        lbDateInfo.setText("Results are searched from " + startDay.toString() + " to "+ endDay.toString());
    }

    public void getProductList(Long productGroupID, List<DetailOrder> detailOrderList){
        bcProductSold.getData().clear();

        OrderDAO orderDAO = new OrderDAO();
        List<Order> totalOrderList = new ArrayList<>();

        for(DetailOrder detailOrder : detailOrderList){
            List<Order> orderList = orderDAO.findByDetailOrderID(detailOrder.getId());
            for(Order order: orderList){
                totalOrderList.add(order);
            }
        }
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.findByGroupProductID(productGroupID);
        for(Product product: productList){

            Long soldAmount = orderDAO.countProductSoldByOrderList(totalOrderList, product.getId());
            GroupProductDetailStatistic object = new GroupProductDetailStatistic(product.getId(), soldAmount, product.getName() );
            if(soldAmount != null){
                GroupProductDetailStatistic combination = new GroupProductDetailStatistic(product.getId(), soldAmount, product.getName());
                if(checkDuplicate(combination, obsList) == true){
                    obsList.add(combination);
                    initLineChart(product.getName(), soldAmount);
                }
            }
        }
        setDataToTable(obsList);


    }


    public Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public void initLineChart(String productName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(productName,totalSold));

        bcProductSold.getData().addAll(series);
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
}

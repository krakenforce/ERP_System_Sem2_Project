package Controller.StatisticModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.EntityCombination.GroupProductDetailStatistic;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GroupStatisticDetailController implements Initializable {

    @FXML
    private Label lbDateInfo;

    @FXML
    private TextField tfFloorLimit;

    @FXML
    private Label lbGroupName;

    private TableView<GroupProductDetailStatistic> tbProductList;

    private TableColumn<GroupProductDetailStatistic, Long> clProductID;

    private TableColumn<GroupProductDetailStatistic, String> clProductName;

    private TableColumn<GroupProductDetailStatistic, Long> clTotalSold;

    @FXML
    private BarChart<String, Number> bcProductSold;

    ObservableList<GroupProductDetailStatistic> obsList = FXCollections.observableArrayList();

    @FXML
    private Pagination pgGroupStatisDetail;
    PaginationService<GroupProductDetailStatistic> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
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
        paginationService.setPagination(pgGroupStatisDetail);
        paginationService.setTableView(tbProductList);
        paginationService.setSopt(10);
        List<GroupProductDetailStatistic> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    @FXML
    void filter(ActionEvent event) {
        Long limit  = Long.parseLong(tfFloorLimit.getText());
        ObservableList<GroupProductDetailStatistic> list2 = FXCollections.observableArrayList();
        for(int i = 0; i<obsList.size(); i++){
            if(obsList.get(i).getSoldAmount() >= limit){
                list2.add(obsList.get(i));
            }
        }
        setUpPagination(list2);
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
        setUpPagination(obsList);


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

    public void LamMoi(){

    }


    public void reset(ActionEvent actionEvent) {
        tfFloorLimit.setText("");
        setUpPagination(obsList);
    }
}

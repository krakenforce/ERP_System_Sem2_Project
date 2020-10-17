package Controller.StatisticModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.EntityCombination.GroupProductStatis;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Product;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GroupProductStatisticController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    private TableView<GroupProductStatis> tbGroupProductList;

    private TableColumn<GroupProductStatis, Long> clGroupID;

    private TableColumn<GroupProductStatis, String> clGroupName;

    private TableColumn<GroupProductStatis, Long> clTotalSold;


    @FXML
    private Pagination pgProductGroupStat;

    PaginationService<GroupProductStatis> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        selectGroupTableItem();
    }

    public void setUpPagination(ObservableList<GroupProductStatis> observableList){
        paginationService.setPagination(pgProductGroupStat);
        paginationService.setTableView(tbGroupProductList);
        paginationService.setSopt(10);
        List<GroupProductStatis> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void setUpTable(){
        tbGroupProductList = new TableView<GroupProductStatis>();
        clGroupID = new TableColumn<GroupProductStatis, Long>("Group ID");
        clGroupName = new TableColumn<GroupProductStatis, String>("Group Name");
        clTotalSold = new TableColumn<GroupProductStatis, Long>("Total Sold");

        clGroupID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clGroupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clTotalSold.setCellValueFactory(new PropertyValueFactory<>("totalSold"));

        tbGroupProductList.getColumns().addAll(clGroupID,clGroupName, clTotalSold);
    }

    @FXML
    private BarChart<String, Number> bcGroupProductStatis;

    @FXML
    void searchByDay(ActionEvent event) {
        getDetailOrderList(getDate(dpStartDay), getDate(dpEndDay));
    }


    @FXML
    void showAll(ActionEvent event) {

    }

    private List<DetailOrder> getDetailOrderList(Date startDay, Date endDay){
        bcGroupProductStatis.getData().clear();

        ObservableList<GroupProductStatis> groupProductStatisObservableList = FXCollections.observableArrayList();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.findByDate(startDay, endDay);
        for(DetailOrder detailOrder: detailOrderList){
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orderList = orderDAO.findByDetailOrderID(detailOrder.getId());
            Long totalSold = 0L;
            for(Order order: orderList){
                totalSold = orderDAO.countAmount(order.getProduct().getGroupProduct().getId(), startDay, endDay);
                GroupProductStatis combination = new GroupProductStatis(order.getProduct().getGroupProduct().getName(),
                        order.getProduct().getGroupProduct().getId(), totalSold);

                if(checkDuplicate(combination, groupProductStatisObservableList) == true){
                    groupProductStatisObservableList.add(combination);
                    initLineChart(combination.getName(), combination.getTotalSold());
                }

            }
        }
        setUpPagination(groupProductStatisObservableList);
        return detailOrderList;
    }

    public boolean checkDuplicate(GroupProductStatis groupProductStatis, ObservableList<GroupProductStatis> groupProductStatisObservableList){
        for (int i = 0; i < groupProductStatisObservableList.size(); i++){
            if(groupProductStatis.getId() == groupProductStatisObservableList.get(i).getId()){
                return false;
            }
        }
        return true;
    }

    public Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }



    public void initLineChart(String groupName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(groupName,totalSold));

        bcGroupProductStatis.getData().addAll(series);
    }

    public void selectGroupTableItem(){
        tbGroupProductList.setOnMouseClicked(event ->{
            if (event.getClickCount() == 2) {
                GroupProductStatis object = tbGroupProductList.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/StatisticModule/GroupStatisticDetail.fxml"));
                try {
                    Parent root = loader.load();
                    GroupStatisticDetailController controller = loader.getController();
                    DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
                    List<DetailOrder> detailOrderList = detailOrderDAO.findByDate(getDate(dpStartDay), getDate(dpEndDay));
                    controller.setTextForComponent("Group Product: " + object.getName(),getDate(dpStartDay), getDate(dpEndDay));
                    controller.getProductList(object.getId(),detailOrderList);

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Product Group Detail Statistic");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void LamMoi(){

    }


}

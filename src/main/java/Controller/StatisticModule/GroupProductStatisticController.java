package Controller.StatisticModule;

import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
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
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GroupProductStatisticController {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<?> tbGroupProductList;

    @FXML
    private TableColumn<?, ?> clGroupID;

    @FXML
    private TableColumn<?, ?> clGroupName;

    @FXML
    private TableColumn<?, ?> clTotalSold;

    @FXML
    private TableColumn<?, ?> clShowDetail;

    @FXML
    private BarChart<String, Number> bcGroupProductStatis;

    @FXML
    void searchByDay(ActionEvent event) {
        getDetailOrderList(getDate(dpStartDay), getDate(dpEndDay));
    }


    @FXML
    void showAll(ActionEvent event) {

    }

    private void getDetailOrderList(Date startDay, Date endDay){
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
        setDataToTable(groupProductStatisObservableList);

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

    public void setDataToTable(ObservableList observableList){
        clGroupID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clGroupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clTotalSold.setCellValueFactory(new PropertyValueFactory<>("totalSold"));

        tbGroupProductList.setItems(observableList);
    }



    public void initLineChart(String groupName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(groupName,totalSold));

        bcGroupProductStatis.getData().addAll(series);
    }
}

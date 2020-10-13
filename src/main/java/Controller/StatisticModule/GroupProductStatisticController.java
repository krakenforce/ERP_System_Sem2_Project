package Controller.StatisticModule;

import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Product;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    void searchByDay(ActionEvent event) {
        getDetailOrderList(getDate(dpStartDay), getDate(dpEndDay));

    }


    @FXML
    void showAll(ActionEvent event) {

    }

    private void getDetailOrderList(Date startDay, Date endDay){
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.findByDate(startDay, endDay);
        for(DetailOrder detailOrder: detailOrderList){
            getOrderList(detailOrder.getId());
        }

    }

    private void getOrderList(Long detailOrderID){
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.findByDetailOrderID(detailOrderID);
        Long amount = 0L;
        for(Order order: orderList){
            //amount += order.getAmount();
            getGroupProduct(order.getProduct().getId());
        }
    }

    private void getGroupProduct(Long productID){
         OrderDAO orderDAO = new OrderDAO();
         ProductDAO productDAO = new ProductDAO();
         Product product = productDAO.findById(productID);
         GroupProduct groupProduct = product.getGroupProduct();

        System.out.println(orderDAO.countAmount(groupProduct.getId()));

    }


    public Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public void setDataToTable(ObservableList observableList){
        clGroupID.setCellValueFactory(new PropertyValueFactory<>(""));
        clGroupName.setCellValueFactory(new PropertyValueFactory<>(""));
        clTotalSold.setCellValueFactory(new PropertyValueFactory<>(""));

        tbGroupProductList.setItems(observableList);
    }
}

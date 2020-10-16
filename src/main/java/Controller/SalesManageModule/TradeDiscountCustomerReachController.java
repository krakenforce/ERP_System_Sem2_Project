package Controller.SalesManageModule;

import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TradeDiscountCustomerReachController {
    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<?> tbTradeCustomer;

    @FXML
    private TableColumn<?, ?> clCustomerID;

    @FXML
    private TableColumn<?, ?> clCustomerName;

    @FXML
    private TableColumn<?, ?> clAmountInvoice;

    @FXML
    private TableColumn<?, ?> clTotalInvoiceMoney;

    @FXML
    private TableColumn<?, ?> clTradeDiscountID;

    @FXML
    private TableColumn<?, ?> clTradeDiscountName;

    @FXML
    private TableColumn<?, ?> clCreatePayment;

    Date startDate = null, endDate = null;

    @FXML
    void showCustomerTradeDiscount(ActionEvent event) {
        //countDetailOrders((long) 15);
    }

//    public List<DetailOrder> getTradeDiscount(Date startDate, Date endDate){
//        DetailOrderDAO dao = new DetailOrderDAO();
//        //List<DetailOrder> detailOrdersList = dao.findByDateRange(startDate, endDate);
//        List<Customer> customerList = FXCollections.observableArrayList();
//        return detailOrdersList;
//    }

    public void countDetailOrders(Long customerID){
        DetailOrderDAO dao = new DetailOrderDAO();
        List<DetailOrder> detailOrdersList = dao.findByCustomerID(customerID);
        OrderDAO orderDAO = new OrderDAO();

        long totalCostOrder = 0;
        long total = 0;
        for(DetailOrder detailOrder : detailOrdersList){
            List<Order> orderList = orderDAO.findByDetailOrderID(detailOrder.getId());
            for(Order order: orderList){
                total = order.getAmount() * order.getProduct().getPrice();
            }
            totalCostOrder += total;
            System.out.println("total: " + totalCostOrder);

        }
        System.out.println(dao.countDetailOrderByCustomerID(customerID));

    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

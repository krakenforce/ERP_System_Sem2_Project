package Controller.SalesManageModule;

import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ReceiptsDAO;
import Services.Hibernate.EntityCombination.ReceiptDetailOrderCustomer;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Receipts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReceiptListManageController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;


    @FXML
    private TextField tfReceiptAmount;

    @FXML
    private TableView<?> tbRecieptList;

    @FXML
    private TableColumn<?, ?> clID;

    @FXML
    private TableColumn<?, ?> clDate;

    @FXML
    private TableColumn<?, ?> clMoneyPay;

    @FXML
    private TableColumn<?, ?> clDetailOrderID;

    @FXML
    private TableColumn<?, ?> clCustomerID;

    @FXML
    private TableColumn<?, ?> clCustomerName;

    @FXML
    private TableColumn<?, ?> clTotalCost;

    @FXML
    private TableColumn<?, ?> clRemaining;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableItems(getObservableList(showAllReceipts()));
    }

    public ObservableList<ReceiptDetailOrderCustomer> getObservableList(List<Receipts> receiptsList){
        ObservableList<ReceiptDetailOrderCustomer> obsList = FXCollections.observableArrayList();
        Long receiptID, detailOrderID, customerID, moneyPay, totalCost, remaining;
        Date date;
        String customerName;

        for(Receipts items: receiptsList){

            receiptID = items.getId();
            detailOrderID = items.getDetailOrder().getId();
            customerID = items.getDetailOrder().getCustomer().getId();
            moneyPay = items.getMoneyPay();
            date = items.getDate();
            customerName = items.getDetailOrder().getCustomer().getName();

            totalCost = items.getDetailOrder().getTotal();
            remaining = totalCost - moneyPay;

            Receipts receipts = new Receipts(receiptID, date , moneyPay);
            DetailOrder detailOrder = new DetailOrder(detailOrderID);
            Customer customer = new Customer(customerID,customerName);

            ReceiptDetailOrderCustomer combination = new ReceiptDetailOrderCustomer(receipts,customer,detailOrder);
            combination.setCustomer(customer);
            combination.setCustomerID(customerID);
            combination.setCustomerName(customerName);
            combination.setDate(date);
            combination.setDetailOrder(detailOrder);
            combination.setDetailOrderID(detailOrderID);
            combination.setMoneyPay(moneyPay);
            combination.setReceiptID(receiptID);
            combination.setReceipts(receipts);
            combination.setTotalCost(totalCost);
            combination.setRemaining(remaining);

            obsList.add(combination);
        }
        return obsList;
    }

    public void setTableItems(ObservableList obsList){
        clID.setCellValueFactory(new PropertyValueFactory<>("receiptID"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clDetailOrderID.setCellValueFactory(new PropertyValueFactory<>("detailOrderID"));
        clMoneyPay.setCellValueFactory(new PropertyValueFactory<>("moneyPay"));
        clCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        clRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        tbRecieptList.setItems(obsList);
    }

    public Long calculateTotalCost(Long detailOrderID){
        long totalCost = 0, remaining = 0;
        OrderDAO orderDAO = new OrderDAO();
        List<Order> list = orderDAO.findByDetailOrderID(detailOrderID);
        for(Order items: list){
            totalCost += items.getProduct().getPrice() * items.getAmount();
        }
        return totalCost;
    }

    public List<Receipts> showAllReceipts(){
        ReceiptsDAO dao = new ReceiptsDAO();
        List<Receipts> receiptsList = dao.getAll();
        tfReceiptAmount.setText(dao.countReceipt().toString());
        return receiptsList;
    }

    public List<Receipts> showByDate(){
        LocalDate localStartDay, localEndDay;
        localStartDay = dpStartDay.getValue();
        localEndDay = dpEndDay.getValue();

        Date startDate, endDate;
        startDate = Date.valueOf(localStartDay);
        endDate = Date.valueOf(localEndDay);

        ReceiptsDAO dao = new ReceiptsDAO();
        List<Receipts> list = dao.searchByDate(startDate,endDate);
        tfReceiptAmount.setText(dao.countReceiptByDate(startDate,endDate).toString());
        return list;
    }

    public void searchReceipt(ActionEvent actionEvent) {
        setTableItems(getObservableList(showByDate()));
    }

    public void showAll(ActionEvent actionEvent) {
        setTableItems(getObservableList(showAllReceipts()));
    }
}

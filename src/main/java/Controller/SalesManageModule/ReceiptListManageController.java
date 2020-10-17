package Controller.SalesManageModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ReceiptsDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
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
import java.util.stream.Collectors;

public class ReceiptListManageController implements Initializable {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;


    @FXML
    private TextField tfReceiptAmount;


    private TableView<ReceiptDetailOrderCustomer> tbRecieptList;


    private TableColumn<ReceiptDetailOrderCustomer, Long> clID;

    private TableColumn<ReceiptDetailOrderCustomer, Date> clDate;

    private TableColumn<ReceiptDetailOrderCustomer, Long> clMoneyPay;

    private TableColumn<ReceiptDetailOrderCustomer, Long> clDetailOrderID;

    private TableColumn<ReceiptDetailOrderCustomer, Long> clCustomerID;

    private TableColumn<ReceiptDetailOrderCustomer, String> clCustomerName;

    private TableColumn<ReceiptDetailOrderCustomer, Long> clTotalCost;

    private TableColumn<ReceiptDetailOrderCustomer, Long> clRemaining;
    @FXML
    private Pagination pgReceiptList;

    PaginationService<ReceiptDetailOrderCustomer> paginationService = new PaginationService<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        setUpPagination(getObservableList(showAllReceipts()));
    }

    public void setUpPagination(ObservableList<ReceiptDetailOrderCustomer> observableList){
        paginationService.setPagination(pgReceiptList);
        paginationService.setTableView(tbRecieptList);
        paginationService.setSopt(10);
        List<ReceiptDetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }
    public void setUpTable(){
        tbRecieptList = new TableView<ReceiptDetailOrderCustomer>();
        clID = new TableColumn<ReceiptDetailOrderCustomer, Long>("Receipt ID");
        clDate = new TableColumn<ReceiptDetailOrderCustomer, Date>("Date");
        clDetailOrderID = new TableColumn<ReceiptDetailOrderCustomer, Long>("Invoice ID");
        clMoneyPay = new TableColumn<ReceiptDetailOrderCustomer, Long>("Money Pay");
        clCustomerID = new TableColumn<ReceiptDetailOrderCustomer, Long>("Customer ID");
        clCustomerName = new TableColumn<ReceiptDetailOrderCustomer, String>("Customer Name");
        clTotalCost = new TableColumn<ReceiptDetailOrderCustomer, Long>("Total Cost");
        clRemaining = new TableColumn<ReceiptDetailOrderCustomer, Long>("Remaining");

        clID.setCellValueFactory(new PropertyValueFactory<>("receiptID"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clDetailOrderID.setCellValueFactory(new PropertyValueFactory<>("detailOrderID"));
        clMoneyPay.setCellValueFactory(new PropertyValueFactory<>("moneyPay"));
        clCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        clRemaining.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        tbRecieptList.getColumns().addAll(clID, clDate, clDetailOrderID, clMoneyPay, clCustomerID, clCustomerName, clTotalCost, clRemaining );
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
        setUpPagination(getObservableList(showByDate()));
    }

    public void showAll(ActionEvent actionEvent) {
        setUpPagination(getObservableList(showAllReceipts()));
    }
}

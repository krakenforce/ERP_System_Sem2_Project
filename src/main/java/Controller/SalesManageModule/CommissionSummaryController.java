package Controller.SalesManageModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.EntityCombination.DetailOrderProductGroup;
import Services.Hibernate.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CommissionSummaryController implements Initializable{

    @FXML
    private TextField tfSellerName;

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TextField tfSummary;

    private TableView<DetailOrderProductGroup> tbCommissionSum;

    private TableColumn<DetailOrderProductGroup, Long> clProductID;

    private TableColumn<DetailOrderProductGroup, String> clProductName;

    private TableColumn<DetailOrderProductGroup, Long> clAmount;

    private TableColumn<DetailOrderProductGroup, Long> clDetailOrderID;

    private TableColumn<DetailOrderProductGroup, Date> clDetailOrderDate;

    private TableColumn<DetailOrderProductGroup, Long> clPrice;

    private TableColumn<DetailOrderProductGroup, Long> clTotalCost;

    private TableColumn<DetailOrderProductGroup, Long> clCommission;

    @FXML
    private Pagination pgCommissionSum;
    PaginationService<DetailOrderProductGroup> paginationService = new PaginationService<>();

    Long salesmanID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
    }

    public void setUpPagination(List list){
        paginationService.setPagination(pgCommissionSum);
        paginationService.setTableView(tbCommissionSum);
        paginationService.setSopt(10);
        paginationService.createPagination(list);
    }

    public void setUpTableView(){
        tbCommissionSum = new TableView<DetailOrderProductGroup>();
        clProductID = new TableColumn<DetailOrderProductGroup, Long>("Product ID");
        clProductName = new TableColumn<DetailOrderProductGroup, String>("Product Name");
        clAmount = new TableColumn<DetailOrderProductGroup, Long>("Amount");
        clDetailOrderID = new TableColumn<DetailOrderProductGroup, Long>("Invoice ID");
        clDetailOrderDate = new TableColumn<DetailOrderProductGroup, Date>("Date");
        clPrice = new TableColumn<DetailOrderProductGroup, Long>("Price");
        clTotalCost = new TableColumn<DetailOrderProductGroup, Long>("Total");
        clCommission = new TableColumn<DetailOrderProductGroup, Long>("Commission");

        clProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        clProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        clDetailOrderID.setCellValueFactory(new PropertyValueFactory<>("detailOrderID"));
        clDetailOrderDate.setCellValueFactory(new PropertyValueFactory<>("detailOrderDate"));
        clAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        clCommission.setCellValueFactory(new PropertyValueFactory<>("commission"));

        tbCommissionSum.getColumns().addAll(clProductID, clProductName, clDetailOrderID
                , clDetailOrderDate, clAmount, clPrice, clTotalCost, clCommission);

    }

    @FXML
    void searchCommissionByDate(ActionEvent event) {
        setUpPagination(getDetailOrderProductObsList(showByDate()));
    }

    @FXML
    void showAllCommission(ActionEvent event) {
        setUpPagination(getDetailOrderProductObsList(showAllOrderBySalesman()));
    }

    public void runTable(String salesmanName){
        tfSellerName.setText(salesmanName);
        setUpPagination(getDetailOrderProductObsList(showAllOrderBySalesman()));

    }

    public ObservableList<DetailOrderProductGroup> getDetailOrderProductObsList(List<Order> orderList){

        ObservableList<DetailOrderProductGroup> obsList = FXCollections.observableArrayList();
        Long productID, amount, detailOrderID, price, totalCost;
        Double commission, totalCommissions = 0.0;
        String productName;
        Date detailOrderDate;
        OrderDAO dao = new OrderDAO();

        for(Order items: orderList){
            productID = items.getProduct().getId();
            amount = items.getAmount();
            detailOrderID = items.getDetailOrder().getId();
            detailOrderDate = items.getDetailOrder().getDate();
            price = items.getProduct().getPrice();
            totalCost = calculateTotalCost(items.getId());
            commission = calculateSumCommission(items.getProduct().getGroupProduct().getCommission(), items.getProduct().getPrice(), items.getAmount());
            productName = items.getProduct().getName();

            DetailOrder detailOrder = items.getDetailOrder();
            Product product = items.getProduct();
            GroupProduct groupProduct = items.getProduct().getGroupProduct();

            DetailOrderProductGroup combinations = new DetailOrderProductGroup(detailOrder,items,product,groupProduct);
            combinations.setProduct(product);
            combinations.setCommission(commission);
            combinations.setProductID(productID);
            combinations.setAmount(amount);
            combinations.setDetailOrderID(detailOrderID);
            combinations.setDetailOrderDate(detailOrderDate);
            combinations.setPrice(price);
            combinations.setTotalCost(totalCost);
            combinations.setProductName(productName);
            combinations.setDetailOrder(detailOrder);
            combinations.setGroupProduct(groupProduct);

            obsList.add(combinations);
            totalCommissions += commission;
        }
        tfSummary.setText(totalCommissions.toString());
        return obsList;
    }


    public Long calculateTotalCost(Long OrderID){
        long totalCost = 0;
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.findByID(OrderID);
        totalCost = order.getProduct().getPrice() * order.getAmount();
        return totalCost;
    }

    public Double calculateSumCommission(Double commissionRate, Long price, Long amount ){
        double commission = (price/100) * commissionRate;
        double totalCommission = commission * amount;
        return totalCommission;
    }

    public List<Order> showAllOrderBySalesman(){
        OrderDAO dao = new OrderDAO();
        List<Order> receiptsList = dao.findBySalesmanID(salesmanID);
        return receiptsList;
    }

    public List<Order> showByDate(){
        LocalDate localStartDay, localEndDay;
        localStartDay = dpStartDay.getValue();
        localEndDay = dpEndDay.getValue();

        Date startDate, endDate;
        startDate = Date.valueOf(localStartDay);
        endDate = Date.valueOf(localEndDay);

        OrderDAO dao = new OrderDAO();
        List<Order> list = dao.findByDate(salesmanID,startDate,endDate);
        return list;
    }


    public Long getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(Long salesmanID) {
        this.salesmanID = salesmanID;
    }

    public void LamMoi(){

    }
}

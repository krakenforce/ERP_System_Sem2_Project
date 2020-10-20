package Controller.StatisticModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.EntityCombination.GroupProductDetailStatistic;
import Services.Hibernate.EntityCombination.TradeDiscountCustomer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.PropertyValue;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TradeDiscountStatisticController implements Initializable {

    private TableView<TradeDiscountCustomer> tbTradeDiscountList;

    private TableColumn<TradeDiscountCustomer, Long> clID;

    private TableColumn<TradeDiscountCustomer, String> clName;

    private TableColumn<TradeDiscountCustomer, Date> clStartDate;

    private TableColumn<TradeDiscountCustomer, Date> clEndDate;

    private TableColumn<TradeDiscountCustomer, Long> clCustomerAmount;

    private TableColumn<TradeDiscountCustomer, Long> clTotalPayment;

    @FXML
    private Pagination pgTradeDiscountStat;

    @FXML
    private BarChart<String, Number> bcTradeDiscount;

    @FXML
    private TextField tfSearch;

    PaginationService<TradeDiscountCustomer> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
        setUpPagination(getTradeDiscountList());
    }

    public void setUpTableView(){
        tbTradeDiscountList = new TableView<TradeDiscountCustomer>();
        clID = new TableColumn<TradeDiscountCustomer, Long>("TD ID");
        clName = new TableColumn<TradeDiscountCustomer, String>("Name");
        clStartDate = new TableColumn<TradeDiscountCustomer, Date>("Start Date");
        clEndDate = new TableColumn<TradeDiscountCustomer, Date>("End Date");
        clCustomerAmount = new TableColumn<TradeDiscountCustomer, Long>("Customer Amount");
        clTotalPayment = new TableColumn<TradeDiscountCustomer, Long>("Total Payment");

        clID.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountID"));
        clName.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountName"));
        clStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        clEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("customerAmount"));
        clTotalPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

        tbTradeDiscountList.getColumns().addAll(clID, clName, clStartDate, clEndDate, clCustomerAmount, clTotalPayment);
    }

    public void setUpPagination(ObservableList<TradeDiscountCustomer> observableList){
        paginationService.setPagination(pgTradeDiscountStat);
        paginationService.setTableView(tbTradeDiscountList);
        paginationService.setSopt(19);
        List<TradeDiscountCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    @FXML
    void searchTradeDiscount(ActionEvent event) {
        String name = tfSearch.getText();
        ObservableList<TradeDiscountCustomer> list = getTradeDiscountList();
        ObservableList<TradeDiscountCustomer> secondList = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++ ){
            if(list.get(i).getTradeDiscountName().equalsIgnoreCase(name) == true){
                secondList.add(list.get(i));
            }
        }
        setUpPagination(secondList);

    }

    public ObservableList<TradeDiscountCustomer> getTradeDiscountList(){
        bcTradeDiscount.getData().clear();
        TradeDiscountDAO tradeDiscountDAO = new TradeDiscountDAO();
        List<TradeDiscounts> tradeDiscountsList = tradeDiscountDAO.getAll();
        PaymentDAO paymentDAO = new PaymentDAO();
        ObservableList<TradeDiscountCustomer> obsList = FXCollections.observableArrayList();

        for(TradeDiscounts tradeDiscounts : tradeDiscountsList){
            Long count = paymentDAO.countPayments(tradeDiscounts);
            Long sum = paymentDAO.sumTotalMoney(tradeDiscounts);

            if(count != null && sum != null){
                TradeDiscountCustomer combination = new TradeDiscountCustomer(tradeDiscounts.getId(),tradeDiscounts.getName()
                        ,count, sum, tradeDiscounts.getDateStars(), tradeDiscounts.getDateEnd());
                initLineChart(tradeDiscounts.getName(), sum);
                obsList.add(combination);
            }

        }
        return obsList;
    }

    public void initLineChart(String tradeDiscountName, Long totalPayment){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(tradeDiscountName,totalPayment));

        bcTradeDiscount.getData().addAll(series);
    }

    public void LamMoi(){

    }

}

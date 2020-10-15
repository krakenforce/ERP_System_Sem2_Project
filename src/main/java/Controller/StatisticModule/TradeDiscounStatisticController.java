package Controller.StatisticModule;

import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.PropertyValue;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeDiscounStatisticController implements Initializable {

    @FXML
    private TableView<TradeDiscountCustomer> tbTradeDiscountList;

    @FXML
    private TableColumn<?, ?> clID;

    @FXML
    private TableColumn<?, ?> clName;

    @FXML
    private TableColumn<?, ?> clStartDate;

    @FXML
    private TableColumn<?, ?> clEndDate;

    @FXML
    private TableColumn<?, ?> clCustomerAmount;

    @FXML
    private TableColumn<?, ?> clTotalPayment;

    @FXML
    private BarChart<String, Number> bcTradeDiscount;

    @FXML
    private TextField tfSearch;

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
        setDataToTable(secondList);

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
            TradeDiscountCustomer combination = new TradeDiscountCustomer(tradeDiscounts.getId(),tradeDiscounts.getName()
                    ,count, sum, tradeDiscounts.getDateStars(), tradeDiscounts.getDateEnd());
            initLineChart(tradeDiscounts.getName(), sum);
            obsList.add(combination);
        }
        return obsList;
    }



    public void setDataToTable(ObservableList observableList){
        clID.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountID"));
        clName.setCellValueFactory(new PropertyValueFactory<>("tradeDiscountName"));
        clStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        clEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clCustomerAmount.setCellValueFactory(new PropertyValueFactory<>("customerAmount"));
        clTotalPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

        tbTradeDiscountList.setItems(observableList);
    }

    public void initLineChart(String tradeDiscountName, Long totalPayment){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(tradeDiscountName,totalPayment));

        bcTradeDiscount.getData().addAll(series);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable(getTradeDiscountList());
    }
}

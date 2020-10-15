package Controller.StatisticModule;

import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeDiscounStatisticController implements Initializable {

    @FXML
    private TableView<?> tbTradeDiscountList;

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
    private BarChart<?, ?> bcTradeDiscount;

    @FXML
    void searchTradeDiscount(ActionEvent event) {

    }

    public void getTradeDiscountList(){
        TradeDiscountDAO tradeDiscountDAO = new TradeDiscountDAO();
        List<TradeDiscounts> tradeDiscountsList = tradeDiscountDAO.getAll();
        PaymentDAO paymentDAO = new PaymentDAO();

        for(TradeDiscounts tradeDiscounts : tradeDiscountsList){
            List<Payment> paymentList = paymentDAO.findPaymentByDate(tradeDiscounts.getDateStars(), tradeDiscounts.getDateEnd());
            for(Payment payment : paymentList){
                System.out.println("Đây là ngày lập phiếu chi " + payment.getDate());
            }
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTradeDiscountList();
    }
}

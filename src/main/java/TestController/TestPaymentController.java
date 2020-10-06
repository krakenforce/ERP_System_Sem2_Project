package TestController;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.PaymentDAO;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.Payment;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TestPaymentController implements Initializable {
    @FXML
    private DatePicker dpPayment;

    @FXML
    private TextField tfMoney;

    @FXML
    private ComboBox<Long> cbTradeID;

    @FXML
    private TextField tfTradeName;

    @FXML
    void addPayment(ActionEvent event) {
        Payment payment = new Payment();
        payment.setDate(getSeletedDate());
        payment.setMoney(Long.parseLong(tfMoney.getText()));
        payment.setTradeDiscounts(getSelectedTradeDiscount(cbTradeID.getSelectionModel().getSelectedItem()));

        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.savePayment(payment);
    }
    @FXML
    void setDataToTf(ActionEvent event) {
        TradeDiscounts selectedTradeDisc = getSelectedTradeDiscount(cbTradeID.getSelectionModel().getSelectedItem());
        tfTradeName.setText(selectedTradeDisc.getName());
    }

    public List<TradeDiscounts> getTradeDiscountsList(){
        ObservableList<Long> customerList = FXCollections.observableArrayList();
        TradeDiscountDAO tradeDiscountDAO = new TradeDiscountDAO();
        List<TradeDiscounts> list = tradeDiscountDAO.getAll();

        for(TradeDiscounts items: list){
            customerList.add(items.getId());
        }
        cbTradeID.setItems(customerList);
        return list;
    }

    private TradeDiscounts getSelectedTradeDiscount(Long id){
        TradeDiscountDAO tradeDiscountDAO = new TradeDiscountDAO();
        TradeDiscounts selectedTradeDiscount = tradeDiscountDAO.findById(id);
        return selectedTradeDiscount;
    }

    private Date getSeletedDate(){
        return Date.valueOf(dpPayment.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTradeDiscountsList();
    }
}

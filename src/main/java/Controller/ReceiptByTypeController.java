package Controller;

import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Receipts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Date;

public class ReceiptByTypeController {
    @FXML
    private TextField tfPayerName;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfReason;

    @FXML
    private TextField tfMoney;

    @FXML
    private TextField tfMoneyInWord;

    @FXML
    private ComboBox<String> cbTypeOfDocument;

    @FXML
    private TextField tfNumOfDoc;

    @FXML
    private TextField tfLiability;

    @FXML
    private ComboBox<String> cbCustomerName;

    @FXML
    private DatePicker dpDate;

    @FXML
    void AddReceipt(ActionEvent event) {
        Receipts receipts = new Receipts();
        DetailOrder detailOrder = new DetailOrder();
        LocalDate date = dpDate.getValue();
        System.out.println(date);
    }




}

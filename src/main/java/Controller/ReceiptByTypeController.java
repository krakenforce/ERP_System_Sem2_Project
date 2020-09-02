package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    void AddReceipt(ActionEvent event) {

    }


}

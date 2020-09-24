package Controller;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.ReceiptsDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import Services.Hibernate.entity.Receipts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ReceiptByTypeController implements Initializable {
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
    private TextField tfLiability;

    @FXML
    private TextField tfCustomerName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<Long> cbDetailOrderID;

//    public void setDataTocbType(){
//        List<String> documentTypes = new ArrayList<>();
//        documentTypes.add("Order");
//        documentTypes.add("Debt");
//        documentTypes.add("Others");
//        ObservableList<String> list = FXCollections.observableArrayList(documentTypes);
//        cbTypeOfDocument.setItems(list);
//    }

    public void setDataToCbDetailOrder(){
        ObservableList<Long> obsDetailOrderList = FXCollections.observableArrayList();
        DetailOrderDAO dao = new DetailOrderDAO();
        List<DetailOrder> list = dao.getAll();
        for(DetailOrder items : list){
            obsDetailOrderList.add(items.getId());
        }
        cbDetailOrderID.setItems(obsDetailOrderList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToCbDetailOrder();
    }

    @FXML
    public void addReceipt(ActionEvent actionEvent) {
        Long moneyPay = Long.parseLong(tfMoney.getText());
        LocalDate selectedDate = dpDate.getValue();
        Date date = Date.valueOf(selectedDate);
        DetailOrder detailOrder = getSelectedDetailOrder();
        String payerName = tfPayerName.getText();
        String address = tfAddress.getText();
        String reason = tfReason.getText();
        String moneyInWord = tfMoneyInWord.getText();

        ReceiptsDAO dao = new ReceiptsDAO();
        Receipts receipts = new Receipts(payerName, address, reason, moneyPay, moneyInWord, date, detailOrder);
        dao.saveReceipt(receipts);
    }

    private DetailOrder getSelectedDetailOrder(){
        Long detailOrderID = cbDetailOrderID.getSelectionModel().getSelectedItem();
        DetailOrderDAO dao = new DetailOrderDAO();
        DetailOrder detailOrder = dao.findByID(detailOrderID);
        return detailOrder;
    }

    public void getCustomer(ActionEvent actionEvent) {
        CustomerDAO cusDAO = new CustomerDAO();
        Customer customer = cusDAO.findByID(getSelectedDetailOrder().getCustomer().getId());
        tfCustomerName.setText(customer.getName());


    }

    public void openReceiptManage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/RecieptListManage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void clearField(ActionEvent actionEvent) {
        tfCustomerName.clear();
        tfAddress.clear();
        tfLiability.clear();
        tfMoney.clear();
        tfReason.clear();
        tfPayerName.clear();
        tfMoneyInWord.clear();
        cbDetailOrderID.getSelectionModel().clearSelection();
    }
}

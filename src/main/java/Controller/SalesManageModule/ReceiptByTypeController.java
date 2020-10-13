package Controller.SalesManageModule;

import Boxes.AlertBox;
import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.ReceiptsDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TextField tfDetailOrderID;

    @FXML
    private TableView<DetailOrderCustomer> tbDetailOrderList;

    @FXML
    private TableColumn<?, ?> clID;

    @FXML
    private TableColumn<?, ?> clDate;

    @FXML
    private TableColumn<?, ?> clPaidStatus;

    @FXML
    private TableColumn<?, ?> clCusID;

    @FXML
    private TableColumn<?, ?> clCusName;

    @FXML
    private TableColumn<?, ?> clDebt;

    @FXML
    private TableColumn<?, ?> clTotal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSelectedDetailOrder();
        setTableItems(getDetailOrdersObservableList());
    }


    @FXML
    public void addReceipt(ActionEvent actionEvent) {

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        Long moneyPay = Long.parseLong(tfMoney.getText());
        LocalDate selectedDate = dpDate.getValue();
        Date date = Date.valueOf(selectedDate);
        DetailOrder detailOrder = detailOrderDAO.findByID(Long.parseLong(tfDetailOrderID.getText()));
        String payerName = tfPayerName.getText();
        String address = tfAddress.getText();
        String reason = tfReason.getText();
        String moneyInWord = tfMoneyInWord.getText();

        Long remainingDebt =  Long.parseLong(tfLiability.getText()) - moneyPay;
        if(remainingDebt <= 0){
            detailOrder.setPay(true);
        }else{
            detailOrder.setPay(false);
        }
        detailOrder.setDebt(remainingDebt);

        ReceiptsDAO dao = new ReceiptsDAO();
        Receipts receipts = new Receipts(payerName, address, reason, moneyPay, moneyInWord, date, detailOrder);
        dao.saveReceipt(receipts);
        detailOrderDAO.updateDetailOrder(detailOrder);
    }

    public void getSelectedDetailOrder(){
        tbDetailOrderList.setOnMouseClicked((MouseEvent event) ->{
            if(event.getClickCount() == 2){
                if(tbDetailOrderList.getSelectionModel().getSelectedItem().getStatus() == true){
                    AlertBox alertBox = new AlertBox();
                    alertBox.warningAlert("Warning", "You cannot create receipt for paid detail order");
                }else{
                    DetailOrderCustomer detailOrderCustomer = tbDetailOrderList.getSelectionModel().getSelectedItem();
                    DetailOrder selectedDetailOrder = detailOrderCustomer.getDetailOrder();
                    tfDetailOrderID.setText(selectedDetailOrder.getId().toString());
                    tfCustomerName.setText(selectedDetailOrder.getCustomer().getName());
                    tfLiability.setText(selectedDetailOrder.getDebt().toString());
                }
            }
        });
    }

    public void openReceiptManage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/ReceiptListManage.fxml"));
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
    }

    public ObservableList<DetailOrderCustomer> getDetailOrdersObservableList(){
        ObservableList<DetailOrderCustomer> detailOrderCustomerObservableList = FXCollections.observableArrayList();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.getAll();

        for(DetailOrder item: detailOrderList){
            DetailOrderCustomer detailOrderCustomer = new DetailOrderCustomer();
            detailOrderCustomer.setDetailOrderID(item.getId());
            detailOrderCustomer.setDate(item.getDate());
            detailOrderCustomer.setStatus(item.getPay());
            detailOrderCustomer.setCustomerID(item.getCustomer().getId());
            detailOrderCustomer.setCustomerName(item.getCustomer().getName());
            detailOrderCustomer.setDebt(item.getDebt());
            detailOrderCustomer.setTotal(item.getTotal());
            detailOrderCustomer.setCustomer(item.getCustomer());
            detailOrderCustomer.setDetailOrder(item);

            detailOrderCustomerObservableList.add(detailOrderCustomer);
        }

        return detailOrderCustomerObservableList;
    }
    public void setTableItems(ObservableList obsList){
        clID.setCellValueFactory(new PropertyValueFactory<>("detailOrderID"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clPaidStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clDebt.setCellValueFactory(new PropertyValueFactory<>("debt"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tbDetailOrderList.setItems(obsList);
    }

}

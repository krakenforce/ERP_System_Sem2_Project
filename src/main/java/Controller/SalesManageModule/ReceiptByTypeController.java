package Controller.SalesManageModule;

import Boxes.AlertBox;
import NodeService.PaginationService;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.ReceiptsDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private ComboBox<String> cbStatus;

    @FXML
    private TextField tfCustomerName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfDetailOrderID;

    private TableView<DetailOrderCustomer> tbDetailOrderList;

    private TableColumn<DetailOrderCustomer, Long> clID;

    private TableColumn<DetailOrderCustomer, Date> clDate;

    private TableColumn<DetailOrderCustomer, Boolean> clPaidStatus;

    private TableColumn<DetailOrderCustomer, Long> clCusID;

    private TableColumn<DetailOrderCustomer, String> clCusName;

    private TableColumn<DetailOrderCustomer, Long> clDebt;

    private TableColumn<DetailOrderCustomer, Long> clTotal;

    @FXML
    private TextField tfSearchName;

    @FXML
    private Pagination pgReceiptList;

    PaginationService<DetailOrderCustomer> paginationService = new PaginationService<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableView();
        setUpPagination(getDetailOrdersObservableList());
        setDataForCbStatus();
        getSelectedDetailOrder();
    }

    public void setUpPagination(ObservableList<DetailOrderCustomer> observableList){
        paginationService.setPagination(pgReceiptList);
        paginationService.setTableView(tbDetailOrderList);
        paginationService.setSopt(6);
        List<DetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void setUpTableView(){
        tbDetailOrderList = new TableView<DetailOrderCustomer>();
        clID = new TableColumn<DetailOrderCustomer, Long>("Invoice ID");
        clDate = new TableColumn<DetailOrderCustomer, Date>("Date");
        clCusID = new TableColumn<DetailOrderCustomer, Long>("Customer ID");
        clCusName = new TableColumn<DetailOrderCustomer, String>("Customer Name");
        clPaidStatus = new TableColumn<DetailOrderCustomer, Boolean>("Status");
        clDebt = new TableColumn<DetailOrderCustomer, Long>("Debt");
        clTotal = new TableColumn<DetailOrderCustomer, Long>("Total");

        clID.setCellValueFactory(new PropertyValueFactory<>("detailOrderID"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clPaidStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clDebt.setCellValueFactory(new PropertyValueFactory<>("debt"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tbDetailOrderList.getColumns().addAll(clID, clDate, clCusID, clCusName, clPaidStatus, clDebt, clTotal);

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
        if(remainingDebt <= 0L){
            detailOrder.setPay(true);
            remainingDebt = 0L;
        }else{
            detailOrder.setPay(false);
        }
        detailOrder.setDebt(remainingDebt);

        ReceiptsDAO dao = new ReceiptsDAO();
        Receipts receipts = new Receipts(payerName, address, reason, moneyPay, moneyInWord, date, detailOrder);
        dao.saveReceipt(receipts);
        detailOrderDAO.updateDetailOrder(detailOrder);

        // update the table -- Tuan added:
        tfLiability.setText(remainingDebt.toString());
        tbDetailOrderList.getSelectionModel().getSelectedItem().setDebt(remainingDebt);
        tbDetailOrderList.refresh();
        // and also update on the main window:
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

    public void setDataToReceiptCreation(DetailOrder detailOrder){
        tfDetailOrderID.setText(detailOrder.getId().toString());
        tfCustomerName.setText(detailOrder.getCustomer().getName());
        tfLiability.setText(detailOrder.getDebt().toString());
    }

    public void openReceiptManage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/ReceiptListManage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
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

    public void searchCustomerName(ActionEvent actionEvent) {
        String name = tfSearchName.getText();
        ObservableList<DetailOrderCustomer> list = getDetailOrdersObservableList();
        ObservableList<DetailOrderCustomer> secondList = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++ ){
            if(list.get(i).getCustomerName().equalsIgnoreCase(name) == true){
                secondList.add(list.get(i));
            }
        }
        setUpPagination(secondList);
    }

    public void setDataForCbStatus(){
        ObservableList<String> statusList = FXCollections.observableArrayList("true", "false");
        cbStatus.setItems(statusList);
    }

    public void showAll(ActionEvent actionEvent) {
        setUpPagination(getDetailOrdersObservableList());
    }

    public void searchByStatus(ActionEvent actionEvent) {

        Boolean status = Boolean.parseBoolean(cbStatus.getSelectionModel().getSelectedItem());
        ObservableList<DetailOrderCustomer> list = getDetailOrdersObservableList();
        ObservableList<DetailOrderCustomer> secondList = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++ ){
            if(Boolean.compare(list.get(i).getStatus(), status) == 0){
                secondList.add(list.get(i));
            }
        }
        setUpPagination(secondList);
    }

    public void LamMoi(){

    }

    /// for module2:
    public Long getNewDebt(Long id) {
        Long rs = 0L;
        for (DetailOrderCustomer o: tbDetailOrderList.getItems()) {
            if (o.getCustomerID().equals(id)) {
                if (!o.getStatus()) {
                    if (o.getDebt() != null) {
                        Long debt = o.getDebt();
                        rs += debt;

                    }
                }
            }
        }
        System.out.println("tes: " +rs);
        return rs;
    }

}

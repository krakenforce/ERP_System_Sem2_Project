package Controller.CustomerManageModule;

import Boxes.AlertBox;
import Controller.SalesManageModule.ReceiptByTypeController;
import NodeService.PaginationService;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.DetailOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerDetailDebtInfoController implements Initializable {

    @FXML
    private Label lbCustomerName;

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Pagination pgDetailOrderList;

    private TableView<DetailOrderCustomer> tbDetailOrderList;

    private TableColumn<DetailOrderCustomer, Long> clDetailOderID;
    private TableColumn<DetailOrderCustomer, Date> clDate;
    private TableColumn<DetailOrderCustomer, Long> clDebt;
    private TableColumn<DetailOrderCustomer, Long> clTotal;

    PaginationService<DetailOrderCustomer> paginationService = new PaginationService<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        setEventForDebtCell();
    }

    public void setUpPagination(ObservableList<DetailOrderCustomer> observableList){
        paginationService.setPagination(pgDetailOrderList);
        paginationService.setTableView(tbDetailOrderList);
        paginationService.setSopt(17);
        List<DetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void setUpTable(){
        tbDetailOrderList = new TableView<DetailOrderCustomer>();
        clDetailOderID = new TableColumn<DetailOrderCustomer, Long>("Detail Order ID");
        clDate = new TableColumn<DetailOrderCustomer, Date>("Date");
        clDebt = new TableColumn<DetailOrderCustomer, Long>("Debt");
        clTotal = new TableColumn<DetailOrderCustomer, Long>("Total");

        clDetailOderID.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("detailOrderID"));
        clDate.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Date>("date"));
        clDebt.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("debt"));
        clTotal.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("total"));

        tbDetailOrderList.getColumns().addAll(clDetailOderID, clDate, clDebt, clTotal);
    }

    public ObservableList<DetailOrderCustomer> getDetailOrderList(Long customerID, Date startDay, Date endDay){
        ObservableList<DetailOrderCustomer> observableList = FXCollections.observableArrayList();
        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        List<DetailOrder> detailOrderList = detailOrderDAO.findByDateRange(startDay, endDay, customerID);

        for(DetailOrder detailOrder : detailOrderList ){
            DetailOrderCustomer object = new DetailOrderCustomer();
            object.setDetailOrderID(detailOrder.getId());
            object.setDate(detailOrder.getDate());
            object.setDebt(detailOrder.getDebt());
            object.setTotal(detailOrder.getTotal());
            object.setDetailOrder(detailOrder);

            observableList.add(object);
        }

        return observableList;
    }

    public void setDataForLabel(String text){
        lbCustomerName.setText(text);
    }

    public void setEventForDebtCell(){
        tbDetailOrderList.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2){
                DetailOrderCustomer object = tbDetailOrderList.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/ReceiptByType.fxml"));

                ReceiptByTypeController controller = fxmlLoader.getController();
                if(object.getDebt() == 0){
                    AlertBox alertBox = new AlertBox();
                    alertBox.warningAlert("Cannot create Receipt", "Cannot create Receipt for paid invoice");
                }else{
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    controller.setDataToReceiptCreation(tbDetailOrderList.getSelectionModel().getSelectedItem().getDetailOrder());
                }
            }
        });
    }


}

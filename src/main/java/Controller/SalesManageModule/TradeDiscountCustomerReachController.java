package Controller.SalesManageModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.*;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.EntityCombination.TradeDiscountCustomer;
import Services.Hibernate.entity.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TradeDiscountCustomerReachController implements Initializable {

    @FXML
    private TextField tfTDName;

    @FXML
    private Label lblInfo;

    @FXML
    private Pagination pgCustomerList;

    private TableView<DetailOrderCustomer> tbCustomerList;

    private TableColumn<DetailOrderCustomer, Long> clCusID;
    private TableColumn<DetailOrderCustomer, String> clCusName;
    private TableColumn<DetailOrderCustomer, Long> clTotalSpent;
    private TableColumn<DetailOrderCustomer, Void> clCreatePayment;

    PaginationService<DetailOrderCustomer> paginationService = new PaginationService<>();

    TradeDiscounts selectedTradeDiscounts = new TradeDiscounts();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
    }

    public void setUpTable(){
        tbCustomerList = new TableView<DetailOrderCustomer>();
        clCusID = new TableColumn<DetailOrderCustomer, Long>("Customer ID");
        clCusName = new TableColumn<DetailOrderCustomer, String>("Customer Name");
        clTotalSpent = new TableColumn<DetailOrderCustomer, Long>("Total Spent");
        clCreatePayment = new TableColumn<DetailOrderCustomer, Void>("Function");

        clCusID.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("customerID"));
        clCusName.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, String>("customerName"));
        clTotalSpent.setCellValueFactory(new PropertyValueFactory<DetailOrderCustomer, Long>("totalSpent"));

        Callback<TableColumn<DetailOrderCustomer, Void>, TableCell<DetailOrderCustomer, Void>> cellFactory1 = new Callback<TableColumn<DetailOrderCustomer, Void>, TableCell<DetailOrderCustomer, Void>>() {
            @Override
            public TableCell<DetailOrderCustomer, Void> call(TableColumn<DetailOrderCustomer, Void> detailOrderCustomerVoidTableColumn) {
                final TableCell<DetailOrderCustomer, Void> cell = new TableCell<DetailOrderCustomer, Void>() {

                    private final Button btn = new Button("Create Payment");
                    {
                        btn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/PaymentCreation.fxml"));
                                Parent root = loader.load();
                                TradeDiscounts tradeDiscounts = getSelectedTradeDiscounts();
                                PaymentCreationController controller = loader.getController();
                                DetailOrderCustomer detailOrderCustomer = tbCustomerList.getItems().get(getIndex());
                                controller.setDetailOrderCustomer(detailOrderCustomer);
                                controller.setTradeDiscounts(tradeDiscounts);
                                controller.setData();

                                Stage stage  = new Stage();
                                stage.setScene(new Scene(root));
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.show();
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        clCreatePayment.setCellFactory(cellFactory1);


        tbCustomerList.getColumns().addAll(clCusID, clCusName, clTotalSpent, clCreatePayment);
    }

    public void setUpPagination(ObservableList<DetailOrderCustomer> observableList){
        paginationService.setPagination(pgCustomerList);
        paginationService.setTableView(tbCustomerList);
        paginationService.setSopt(17);
        List<DetailOrderCustomer> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }



    public ObservableList<DetailOrderCustomer> getCustomerListByDate(Date startDay, Date endDay, Long limit){
        ObservableList<DetailOrderCustomer> observableList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.selectAllCustomer();

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        for(Customer customer : customerList){
            DetailOrderCustomer object = new DetailOrderCustomer();
            Long totalDebt = detailOrderDAO.sumTotalDebt(customer.getId(),startDay, endDay);
            Long totalSpent = detailOrderDAO.sumTotalSpent(customer.getId(), startDay, endDay);

            if(totalSpent != null && totalSpent >= limit){
                object.setCustomerID(customer.getId());
                object.setCustomerName(customer.getName());
                object.setTotalSpent(totalSpent);
                observableList.add(object);
            }
        }
        return observableList;
    }

    public void setDataToLabel(String tradeDiscountNames, Date startDate, Date endDate){
        tfTDName.setText(tradeDiscountNames);
        lblInfo.setText("Result is from " + startDate.toString() + " to " + endDate.toString());
    }

    public TradeDiscounts getSelectedTradeDiscounts() {
        return selectedTradeDiscounts;
    }

    public void setSelectedTradeDiscounts(TradeDiscounts selectedTradeDiscounts) {
        this.selectedTradeDiscounts = selectedTradeDiscounts;
    }
}

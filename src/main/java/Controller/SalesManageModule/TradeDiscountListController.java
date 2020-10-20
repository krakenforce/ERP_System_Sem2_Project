package Controller.SalesManageModule;

import NodeService.PaginationService;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TradeDiscountListController implements Initializable {
    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<TradeDiscounts> tbTradeDiscountList;

    @FXML
    private TableColumn<TradeDiscounts, Long> clTradeDiscountID;

    @FXML
    private TableColumn<TradeDiscounts, String> clTradeDiscountName;

    @FXML
    private TableColumn<TradeDiscounts, Date> clStartDay;

    @FXML
    private TableColumn<TradeDiscounts, Date> clEndDay;

    @FXML
    private TableColumn<TradeDiscounts, Long> clMoneyLimit;

    @FXML
    private TableColumn<TradeDiscounts, Long> clDiscountPercentage;

    @FXML
    private TableColumn<TradeDiscounts, ?> clCreatePayment;

    @FXML
    private Pagination pgTradeDiscountList;

    PaginationService<TradeDiscounts> paginationService = new PaginationService<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTable();
        setUpPagination(getDiscountList());
        openCustomerDiscountList();
    }


    public void setUpTable(){
        tbTradeDiscountList = new TableView<TradeDiscounts>();
        clTradeDiscountID = new TableColumn<TradeDiscounts, Long>("Trade Discount ID");
        clTradeDiscountName = new TableColumn<TradeDiscounts, String>("Trade Discount Name");
        clStartDay = new TableColumn<TradeDiscounts, Date>("Start Day");
        clEndDay = new TableColumn<TradeDiscounts, Date>("End Day");
        clMoneyLimit = new TableColumn<TradeDiscounts, Long>("Money Limit");
        clDiscountPercentage = new TableColumn<TradeDiscounts, Long>("Discount Percentage");

        clTradeDiscountID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clTradeDiscountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clStartDay.setCellValueFactory(new PropertyValueFactory<>("dateStars"));
        clEndDay.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clMoneyLimit.setCellValueFactory(new PropertyValueFactory<>("limitMoney"));
        clDiscountPercentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));

        tbTradeDiscountList.getColumns().addAll(clTradeDiscountID, clTradeDiscountName, clStartDay, clEndDay, clMoneyLimit, clDiscountPercentage);
    }

    public void setUpPagination(ObservableList<TradeDiscounts> observableList){
        paginationService.setPagination(pgTradeDiscountList);
        paginationService.setTableView(tbTradeDiscountList);
        paginationService.setSopt(17);
        List<TradeDiscounts> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }


    @FXML
    void showTradeDiscount(ActionEvent event) {
        setUpPagination(getDiscountListByDateRange(getDay(dpStartDay), getDay(dpEndDay)));
    }

    public ObservableList<TradeDiscounts> getDiscountListByDateRange(Date startDay, Date endDay){
        ObservableList<TradeDiscounts> observableList = FXCollections.observableArrayList();
        TradeDiscountDAO dao = new TradeDiscountDAO();
        List<TradeDiscounts> tradeDiscountsList = dao.findTradeDiscountsByDateRange(startDay, endDay);

        for(TradeDiscounts items: tradeDiscountsList){
            TradeDiscounts tradeDiscounts = new TradeDiscounts(items.getId(),items.getName(),items.getLimitMoney(),items.getDateStars(),items.getDateEnd(),items.getDiscountPercentage());
            observableList.add(tradeDiscounts);
        }
        return observableList;
    }




    public ObservableList<TradeDiscounts> getDiscountList(){
        ObservableList<TradeDiscounts> observableList = FXCollections.observableArrayList();
        TradeDiscountDAO dao = new TradeDiscountDAO();
        List<TradeDiscounts> tradeDiscountsList = dao.getAll();

        for(TradeDiscounts items: tradeDiscountsList){
            TradeDiscounts tradeDiscounts = new TradeDiscounts(items.getId(),items.getName(),items.getLimitMoney(),items.getDateStars(),items.getDateEnd(),items.getDiscountPercentage());
            observableList.add(tradeDiscounts);
        }
        return observableList;
    }

    @FXML
    void openAddTradeDiscount(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Form/CustomerManageModule/AddDiscount.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Add Trade Discount");
        stage.show();
    }


    public void openCustomerDiscountList() {
        tbTradeDiscountList.setOnMouseClicked((event) -> {
            if(event.getClickCount() >= 2){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/TradeDiscountCustomerReach.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TradeDiscountCustomerReachController controller = fxmlLoader.getController();
                TradeDiscounts selectedTradeDiscount = tbTradeDiscountList.getSelectionModel().getSelectedItem();
                Date startDate = selectedTradeDiscount.getDateStars();
                Date endDate = selectedTradeDiscount.getDateEnd();
                controller.setDataToLabel(selectedTradeDiscount.getName(), startDate, endDate);
                controller.setUpPagination(controller.getCustomerListByDate(startDate, endDate, selectedTradeDiscount.getLimitMoney()));
                controller.setSelectedTradeDiscounts(selectedTradeDiscount);

                Stage stage = new Stage();
                stage.setTitle("Customer Discount List");
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }
    public void LamMoi(){

    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }


}

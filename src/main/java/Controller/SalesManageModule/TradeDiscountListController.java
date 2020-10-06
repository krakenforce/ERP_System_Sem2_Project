package Controller.SalesManageModule;

import Services.Hibernate.DAO.TradeDiscountDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TradeDiscountListController implements Initializable {
    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<TradeDiscounts> tbTradeDiscountList;

    @FXML
    private TableColumn<?, Long> clTradeDiscountID;

    @FXML
    private TableColumn<?, String> clTradeDiscountName;

    @FXML
    private TableColumn<?, Date> clStartDay;

    @FXML
    private TableColumn<?, Date> clEndDay;

    @FXML
    private TableColumn<?, Long> clMoneyLimit;

    @FXML
    private TableColumn<?, Long> clDiscountPercentage;

    @FXML
    private TableColumn<?, ?> clCreatePayment;


    @FXML
    void showCustomerTradeDiscount(ActionEvent event) {

    }

    public ObservableList<TradeDiscounts> getDiscountList(){
        ObservableList<TradeDiscounts> observableList = FXCollections.observableArrayList();
        TradeDiscountDAO dao = new TradeDiscountDAO();
        List<TradeDiscounts> tradeDiscountsList = dao.getAll();

        for(TradeDiscounts items: tradeDiscountsList){
            TradeDiscounts tradeDiscounts = new TradeDiscounts(items.getId(),items.getName(),items.getLimitMoney(),items.getDateStars(),items.getDateEnd(),items.getDiscountPercentage());
            observableList.add(tradeDiscounts);
        }
        openCustomerDiscountList();
        return observableList;
    }

    public void setDataToTable(ObservableList observableList){
        clTradeDiscountID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clTradeDiscountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clStartDay.setCellValueFactory(new PropertyValueFactory<>("dateStars"));
        clEndDay.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clMoneyLimit.setCellValueFactory(new PropertyValueFactory<>("limitMoney"));
        clDiscountPercentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));
        tbTradeDiscountList.setItems(observableList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToTable(getDiscountList());
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
                controller.getTradeDiscount(startDate, endDate);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }


}

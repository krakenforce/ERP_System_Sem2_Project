package Controller;

import App.App;
import Boxes.ConfirmBox;
import NodeService.TabPaneService;
import NodeService.TabService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    private App app;
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private MenuItem mnitAbout;

    @FXML
    private TabPane tpMain;

    TabPaneService tabPaneService = new TabPaneService();
    TabService saleManageTab = new TabService("Sale Manage");
    TabService customerStatisticTab = new TabService("Customer Statistic");
    TabService paymentManageTab = new TabService("Payment Manage");
    TabService saleStatisticTab = new TabService("Sale Statistic");
    TabService productGroupStatisticTab = new TabService("Product Group Statistic");
    TabService productSaleStatisticsTab = new TabService("Product Sale Statistic");
    TabService tradeDiscountStatisticTab = new TabService("Trade Discount Statistic");
    TabService customerTradeDiscountStatTab = new TabService("Customer Trade Discount Statistic");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void openSaleManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,saleManageTab,"/Form/SalesmanModule/SalesManList.fxml");
    }

    public void openStatistic(ActionEvent actionEvent) {
    }

    public void openPayment(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,paymentManageTab,"/Form/SalesmanModule/PaymentManage.fxml");
    }


    public void openAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource("/Form/About.fxml"));
        Parent root = loader.load();



        Stage stage = new Stage();
        stage = (Stage) root.getScene().getWindow();
        stage.show();
    }

    public void openCustomerStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,customerStatisticTab,"/Form/StatisticModule/CustomerListStatistic.fxml");
    }

    public void openSaleStatistic(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,saleStatisticTab,"/Form/StatisticModule/SalesStatistic.fxml");
    }

    public void openProductGroupStatistic(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,productGroupStatisticTab,"/Form/StatisticModule/GroupProductStatistic.fxml");
    }

    public void openProductSaleStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,productSaleStatisticsTab,"/Form/StatisticModule/ProductSoldStatistic.fxml");
    }

    @FXML
    void openTradeDiscountStatis(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,tradeDiscountStatisticTab,"/Form/StatisticModule/TradeDiscountStatistic.fxml");
    }

    public void openCustomerTradeDiscountStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,customerTradeDiscountStatTab,"/Form/StatisticModule/CustomerTradeStatistic.fxml");
    }

    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                boolean answer = ConfirmBox.display("Closing Application", "Do you want to quit?");
                if (answer) {
                    stage.close();
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

}

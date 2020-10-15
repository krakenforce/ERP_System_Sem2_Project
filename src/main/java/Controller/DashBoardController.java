package Controller;

import App.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    private App app;
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private TabPane tpMain;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void openSaleManage(ActionEvent actionEvent) {
        createTab("Sale Manage","Form/SalesmanModule/SalesManList.fxml" );
    }

    public void openStatistic(ActionEvent actionEvent) {
        createTab("Statistic","Form/StatisticModule/CustomerListStatistic.fxml" );
    }

    public void openPayment(ActionEvent actionEvent) {
        createTab("Sale Manage","Form/SalesmanModule/PaymentManage.fxml" );
    }


    public void openAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource("/Form/About.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, Color.TRANSPARENT));
        stage.show();
    }

    public void openCustomerStatis(ActionEvent actionEvent) {
        createTab("Customer Statistic","Form/StatisticModule/CustomerListStatistic.fxml" );
    }

    public void openSaleStatistic(ActionEvent actionEvent) {
        createTab("Sale Statistic","Form/StatisticModule/SalesStatistic.fxml" );
    }

    public void openProductGroupStatistic(ActionEvent actionEvent) {
        createTab("Product Group Statistic","Form/StatisticModule/GroupProductStatistic.fxml" );
    }

    public void openProductSaleStatis(ActionEvent actionEvent) {
        createTab("Product Sold Statistic","Form/StatisticModule/ProductSoldStatistic.fxml" );
    }

    public void createTab(String title, String fxmlPath){
        try {
            Tab tab = new Tab();
            tab.setText(title);
            tpMain.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/" +  fxmlPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openCustomersManage(ActionEvent event) {
        createTab("Customer Management", "Form/Module2/Module2_Main.fxml");
    }
}

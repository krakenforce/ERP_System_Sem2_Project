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
        openSaleManageTab();
        openStatisticsTab();
    }


    public void openSaleManage(ActionEvent actionEvent) {
        openSaleManageTab();
    }

    public void openSaleManageTab(){
        try {
            Tab tab = new Tab();
            tab.setText("Sale Manage");
            tpMain.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/Form/SalesmanModule/SalesManList.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStatistic(ActionEvent actionEvent) {
        openStatisticsTab();
    }

    public void openStatisticsTab(){
        try {
            Tab tab = new Tab();
            tab.setText("Statistic");
            tpMain.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/Form/StatisticModule/CustomerListStatistic.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource("/Form/About.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, Color.TRANSPARENT));
        stage.show();
    }
}

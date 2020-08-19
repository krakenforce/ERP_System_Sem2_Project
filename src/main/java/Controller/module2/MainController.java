package Controller.module2;

import App.App;
import Boxes.PasswordChangeWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    App app;
    Pane currentLeftPane;
    Button currentBtn;
    @FXML
    Button discountPaneBtn;

    @FXML
    Button customersPaneBtn;
    @FXML
    BorderPane purchaseOrderWin;
    @FXML
    BorderPane discounts_pane;
    @FXML
    BorderPane customers_pane;


    public void setApp(App app) {
        this.app = app;
    }

    public void logOut(ActionEvent event) {
        if (app != null) app.logOut();
    }

    public void changePassword(ActionEvent event) throws IOException {
        PasswordChangeWindow cpw = new PasswordChangeWindow(app);
        cpw.display();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../Form/Module2/PurchaseOrder.fxml"));
        try {
            Node purchaseOrderContents = loader.load();
            PurchaseOrderController purchaseOrderController = loader.getController();
            purchaseOrderWin.setCenter(purchaseOrderContents);


        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../../Form/Module2/CustomerPane.fxml"));
        AnchorPane customerContents = null;
        try {
            customerContents = (AnchorPane) loader1.load();
            customers_pane.setCenter(customerContents);
        } catch (IOException e) {
            e.printStackTrace();

        }
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("../../Form/Module2/DiscountPane.fxml"));
        AnchorPane discountContents = null;
        try {
            discountContents = (AnchorPane) loader2.load();
            discounts_pane.setCenter(discountContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        currentBtn = customersPaneBtn;
//        currentLeftPane = customers_pane;
        setCurrentPane(customers_pane, customersPaneBtn);


    }

    public void handleClicks(ActionEvent event) {
        if (event.getSource() == customersPaneBtn) {
            setCurrentPane(customers_pane, customersPaneBtn);
        }
        if (event.getSource() == discountPaneBtn) {
            setCurrentPane(discounts_pane, discountPaneBtn);
        }
    }

    private void setCurrentPane(Pane pane, Button btn) {
        if (currentBtn != null) {
//            currentBtn.setStyle("-fx-font-weight: normal");
            currentBtn.setStyle("-fx-background-color: grey");
        }

        currentBtn = btn;
//        currentBtn.setStyle("-fx-font-weight: bold");
        currentBtn.setStyle("-fx-background-color: white");
        if (currentLeftPane != null) {

            currentLeftPane.setVisible(false);
        }
        currentLeftPane = pane;
        currentLeftPane.toFront();
        currentLeftPane.setVisible(true);

    }
}

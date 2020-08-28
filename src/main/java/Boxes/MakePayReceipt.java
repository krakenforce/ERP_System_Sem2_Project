package Boxes;

import Controller.module2.AddCustomerToDiscountController;
import Controller.module2.CustomerController;
import Controller.module2.MakeAPayReceiptController;
import Controller.module2.TradeDiscountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MakePayReceipt {

    public Stage window;
    private CustomerController cc;

    public void display() throws IOException {

        window = new Stage();
        window.setTitle("Make a Pay Receipt");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Form/Module2/MakeAPayReceipt.fxml"));

        Parent root = loader.load();
        window.setScene(new Scene(root));


        // set the Utils:
        MakeAPayReceiptController ctrl = loader.getController();
        ctrl.setBox(this);

        window.setOnCloseRequest(e -> {
            e.consume();
            close();
        });

        window.showAndWait();
    }

    private void close() {
        window.close();
        Platform.runLater(() -> {

        });
    }
}

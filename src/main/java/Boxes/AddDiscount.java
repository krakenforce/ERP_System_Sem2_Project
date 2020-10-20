package Boxes;

import Controller.CustomerManageModule.AddDiscountController;
import Controller.module2.TradeDiscountController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddDiscount {
    public Stage window;
    private TradeDiscountController tc;

    public void display() throws IOException {

        window = new Stage();
        window.setTitle("Add Trade Discount");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Form/Module2/AddDiscount.fxml"));

        Parent root = loader.load();
        window.setScene(new Scene(root));


        // set the Utils:
        AddDiscountController ctrl = loader.getController();
        ctrl.setBox(this);

        window.setOnCloseRequest(e -> {
            e.consume();
            close();
        });

        window.showAndWait();
    }

    public void close() {
        window.close();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tc.searchByName(new ActionEvent());

            }
        });
    }

    public void setUp(TradeDiscountController tradeDiscountController) {
        this.tc = tradeDiscountController;
    }
}

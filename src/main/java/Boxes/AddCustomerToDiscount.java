package Boxes;

import Controller.module2.AddCustomerToDiscountController;
import Controller.module2.AddDiscountController;
import Controller.module2.TradeDiscountCols;
import Controller.module2.TradeDiscountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerToDiscount {

    public Stage window;
    private TradeDiscountController tc;
    public TradeDiscountCols tcol;

    public void display() throws IOException {

        window = new Stage();
        window.setTitle("Add Customer To a Trade-Discount");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Form/Module2/AddCustomerToDiscount.fxml"));

        Parent root = loader.load();
        window.setScene(new Scene(root));


        // set the Utils:
        AddCustomerToDiscountController ctrl = loader.getController();
        ctrl.setBox(this);
        ctrl.my_initialize();

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

    public void setUp(TradeDiscountController tc, TradeDiscountCols tcol) {
        this.tc = tc;
        this.tcol = tcol;
    }
}

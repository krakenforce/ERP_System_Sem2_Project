package Boxes;

import Controller.module2.*;
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
    public CustomerCols cl;
    private MakeAPayReceiptController ctrl;

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
        ctrl = (MakeAPayReceiptController) loader.getController();
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
            Long debt = cl.getDebt();
            Long currentDebt = debt > ctrl.amountPaidSoFar ? debt - ctrl.amountPaidSoFar : 0;
            cl.setDebt(currentDebt);
        });
    }

    public void setUp(CustomerController cc, CustomerCols cl) {
        this.cc = cc;
        this.cl = cl;
    }
}

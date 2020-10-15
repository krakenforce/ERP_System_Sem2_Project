package Boxes;

import Controller.SalesManageModule.ReceiptByTypeController;
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
    public CustomerController cc;
    public CustomerCols cl;
    private ReceiptByTypeController ctrl;

    public void display() throws IOException {

        window = new Stage();
        window.setTitle("Make a Pay Receipt");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Form/SalesmanModule/ReceiptByType.fxml"));
        Parent root = loader.load();
        window.setScene(new Scene(root));


         // set the Utils:
        ctrl = (ReceiptByTypeController) loader.getController();

        window.setOnCloseRequest(e -> {
            e.consume();
            close();
        });

        window.showAndWait();
    }

    private void close() {
        window.close();
        Platform.runLater(() -> {
            System.out.println(ctrl);
            cl.setDebt(ctrl.getNewDebt(cl.getId()));
        });
    }

    public void setUp(CustomerController cc, CustomerCols cl) {
        this.cc = cc;
        this.cl = cl;
    }
}

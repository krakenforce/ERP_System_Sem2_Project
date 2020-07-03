package Boxes;

import App.App;
import Controller.ForgetPWController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPassword {
    public Stage window;
    private App app;

    public ForgetPassword(App app) {
        this.app = app;
    }
    public void display() throws IOException {
        window = new Stage();
        window.setTitle("Reset Password");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Fxml/ForgetPW.fxml"));
        Parent root = loader.load();
        window.setScene(new Scene(root));


        // set the Utils:
        ForgetPWController ctrl = loader.getController();
        ctrl.setApp(app);

        window.showAndWait();
    }
}

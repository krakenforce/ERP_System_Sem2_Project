package Boxes;

import App.App;
import Controller.ChangePasswordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordChangeWindow {
    public Stage window;
    private App app;

    public PasswordChangeWindow(App app) {
        setApp(app);
    }

    public void display() throws IOException {
        window = new Stage();
        window.setTitle("Reset Password");
        window.initModality(Modality.APPLICATION_MODAL);

        // set loader:
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ForgetPassword.class.getResource("../Fxml/ChangePassword.fxml"));
        Parent root = loader.load();

        ChangePasswordController ctrl = (ChangePasswordController) loader.getController();
        ctrl.setUp(app);
        window.setScene(new Scene(root));


        window.showAndWait();
    }

    public void close() {
        window.close();
    }

    private void setApp(App app) {
        this.app = app;
    }


}

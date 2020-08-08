package Controller;

import App.App;
import Boxes.PasswordChangeWindow;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    App app;
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

    }
}

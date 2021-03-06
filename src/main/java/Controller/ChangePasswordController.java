package Controller;

import App.App;
import Boxes.PasswordChangeWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    public ImageView correctImg;
    private PasswordChangeWindow window;
    private App app;

    public Label prompt;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfOldPw;
    @FXML
    private PasswordField pfNewPw;
    @FXML
    private PasswordField pfReNewPw;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    public void setUp(App app) {
        this.app = app;
        btnConfirm.setDefaultButton(true);

        pfNewPw.textProperty().addListener(((observableValue, s, t1) -> {
            correctImg.setVisible(false);
            if (checkIfMatches()) {
                correctImg.setVisible(true);
            };

        }));

        pfReNewPw.textProperty().addListener((observable, oldVal, newVal) -> {
            correctImg.setVisible(false);
            if (checkIfMatches()) {
                correctImg.setVisible(true);
            };
        });
    }

    @FXML
    public void changePassword(ActionEvent event) {
        String user = app.getLoggedUser().getUsername();


        if (checkIfMatches()) {
            // ...
        }
    }
    @FXML
    public void exit(ActionEvent event) {
        window.close();
    }

    private boolean checkIfMatches() {
        String newPw = pfNewPw.getText().trim();
        String reNewPw = pfReNewPw.getText().trim();
        if (!reNewPw.isEmpty() && !newPw.isEmpty()) {
            if (newPw.equals(reNewPw)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

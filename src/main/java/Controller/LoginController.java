package Controller;

import App.App;
import Boxes.ForgetPassword;
import Services.Hibernate.DAO.UserDaoImpl;
import Services.Hibernate.entity.LoginInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    UserDaoImpl userDao;
    private App app;
    @FXML
    private Label pwLabel;

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPW;
    @FXML
    private Button btnLogin;
    @FXML
    private CheckBox cbShowPW;
    @FXML
    private Hyperlink hlForgetPW;
    @FXML
    private TextField tfShowPW;

    @FXML
    private Label prompt;

    public void setApp(App app) {
        this.app = app;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDao = new UserDaoImpl();

        // setup the 2 password fields:
        //pfPW.textProperty().bindBidirectional(tfShowPW.textProperty());
        //showPassword();

        btnLogin.setDefaultButton(true);

        Platform.runLater(() -> {
            tfUsername.requestFocus();
        });
    }

    @FXML
    public void showPassword() {
        if (cbShowPW.isSelected()) {
            pfPW.setVisible(false);
            tfShowPW.setVisible(true);
            pwLabel.setLabelFor(tfShowPW);
        } else {
            pfPW.setVisible(true);
            tfShowPW.setVisible(false);
            pwLabel.setLabelFor(pfPW);
        }

    }


    public void logIn(ActionEvent event) {
        String username = tfUsername.getText();
        String pw = pfPW.getText();

        if (username.isBlank() | pw.isBlank()) {
            prompt.setText("Empty username or password");
            return;
        }

        System.out.println(pw);
        LoginInfo u = userDao.getUserByUsername(username);
        if (u != null) {
            app.setLoggedUser(u);
            app.goToMainWindow();
        } else {
            prompt.setText("Wrong username or password");
        }


    }

    public void forgetPW(ActionEvent event) throws IOException {
        ForgetPassword fpw_window = new ForgetPassword(app);
        fpw_window.display();
    }

    public void createAccount(ActionEvent event) {

    }
}

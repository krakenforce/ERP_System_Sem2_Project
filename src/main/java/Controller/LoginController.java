package Controller;

import App.App;
import Boxes.AlertBox;
import Boxes.ForgetPassword;
import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.DAO.UserDaoImpl;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.utils.HibernateUtil;
import Utils.Security;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    UserDaoImpl userDao;
    private App app;
    @FXML
    private Label pwLabel;

    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField pfPW;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXCheckBox cbShowPW;
    @FXML
    private ToggleButton tgbtnShowPW;
    @FXML
    private JFXTextField tfShowPW;
    @FXML
    private JFXCheckBox cbRememberMe;

    @FXML
    private Label prompt;

    public void setApp(App app) {
        this.app = app;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();
        LoginInfo u;

        try {
            s.beginTransaction();
            s.getTransaction().commit();
        }finally {
            s.close();
        }

         //setup the 2 password fields:
        pfPW.textProperty().bindBidirectional(tfShowPW.textProperty());
        showPassword();

        btnLogin.setDefaultButton(true);

        Platform.runLater(() -> {
            tfUsername.requestFocus();
        });
    }

    @FXML
    public void showPassword() {
        if (tgbtnShowPW.isSelected()) {
            pfPW.setVisible(false);
            tfShowPW.setVisible(true);
            tfShowPW.setText(pfPW.getText());
           // pwLabel.setLabelFor(tfShowPW);
        } else {
            pfPW.setVisible(true);
            tfShowPW.setVisible(false);
            pfPW.setText(tfShowPW.getText());
            //pwLabel.setLabelFor(pfPW);
        }

    }

    @FXML
    public void logIn(ActionEvent event) {
        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        AlertBox alertBox = new AlertBox();
        String username = tfUsername.getText();
        String pw = pfPW.getText();
        AlertBox alertbox = new AlertBox();

        if (username.isBlank() | pw.isBlank()) {
            alertBox.warningAlert("Cannot Log in", "Please enter username and password");
            return;
        }

        //System.out.println(pw);
        LoginInfo user = loginInfoDAO.findByUsername(username);
        Security security = new Security();
        if (user != null && security.checkPassword(pw, user.getHashpw())) {
            app.setLoggedUser(user);
            app.goToMainWindow();
        } else {
            alertBox.warningAlert("Cannot Log in","Wrong username or password" );
        }


    }

    public void forgetPW(ActionEvent event) throws IOException {
        ForgetPassword fpw_window = new ForgetPassword(app);
        fpw_window.display();
    }

    public void createAccount(ActionEvent event) {

    }

    public void loadDashBoard(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/MainForm/DashBoard.fxml"));
    }
}

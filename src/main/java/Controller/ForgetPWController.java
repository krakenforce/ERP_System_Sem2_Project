package Controller;

import App.App;
import Services.Hibernate.DAO.UserDaoImpl;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPWController implements Initializable {

    private App app;
    private ScheduledService<?> svc;
    private UserDaoImpl userDao = new UserDaoImpl();

    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnGetPw;
    @FXML
    private Label prompt;
    @FXML
    private TextField tfUsername;


    @FXML
    public void resetPassword(ActionEvent event) {
        String email = tfEmail.getText().trim();
        String username = tfUsername.getText().trim();

        if (app != null) {
            // set up schedule service:
            svc = new ScheduledService<String>() {
                StringBuilder text = new StringBuilder("Please wait");
                @Override
                protected Task<String> createTask() {
                    return new Task<>() {
                        @Override
                        protected String call() throws Exception {
                            text.append(".");
                            if (text.toString().endsWith("....")) {
                                text.replace(0, text.length() - 1, "Please wait");
                            }
                            return text.toString();
                        }
                    };
                }
            };
            svc.setPeriod(Duration.seconds(0.5));
            prompt.textProperty().bind((ObservableValue<? extends String>) svc.lastValueProperty());
            // service runs on the main thread (does it have to?, i don't know).
            svc.restart();

            // run the (reset pw + sending email) on another thread:
            Thread t = new Thread(() -> {
                int error = userDao.resetPassword(username, email);

                // use Platform.runlater() because we want to update something in the main thread, in another thread
                Platform.runLater(() -> {
                    svc.cancel();
                    prompt.textProperty().unbind();
                    if (error != 0) {
                        if (error == 1) {
                            prompt.setText("Username and email don't match");
                        } else if (error == 2) {
                            prompt.setText("Error resetting password");
                        }
                    } else {
                        prompt.setText("Done. Check email for new password");
                    }
                });
            });
            t.start();

        } else {
            System.out.println("App field is null");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setApp(App app) {
        this.app = app;
    }
}

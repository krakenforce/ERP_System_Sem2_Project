package App;

import Boxes.ConfirmBox;
import Controller.LoginController;
import Controller.MainWindowController;
import Services.Hibernate.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {

    public Stage window;
    private User loggedUser;
    MainWindowController mainw;
    LoginController login;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        // setting up:
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        // jump to login:
        goToLogin();
        window.show();
    }
    public void goToMainWindow() {
        try {
            mainw = (MainWindowController) changeScene("../Fxml/MainWindow.fxml", "Main window");
            mainw.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToLogin() {

        try {
            login = (LoginController) changeScene("../Fxml/LoginUI.fxml", "Login");
            login.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Initializable changeScene(String fxml, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        InputStream in =  getClass().getResourceAsStream(fxml);
        loader.setLocation(getClass().getResource(fxml));

        Parent root = loader.load(in);

        window.setScene(new Scene(root));
        window.setTitle(title);

        return (Initializable) loader.getController();
    }


    public void closeProgram() {
        try {
            boolean answer = ConfirmBox.display("Closing Application", "Do you want to quit?");
            if (answer) {
                window.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logOut() {
        loggedUser = null;
        goToLogin();
    }
}

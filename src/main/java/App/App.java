package App;

import Boxes.ConfirmBox;
import Controller.DashBoardController;
import Controller.LoginController;
import Controller.MainWindowController;
import Controller.SalesManageModule.SalesManListController;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.utils.HibernateUtil;
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
    private LoginInfo loggedUser;
    MainWindowController mainw;
    LoginController login;
    DashBoardController dashboard;

    public LoginInfo getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoginInfo loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        HibernateUtil.getSessionFactory();
//         setting up:
//        window.setOnCloseRequest(e -> {
//            e.consume();
//            closeProgram();
//        });


//        // jump to login:
        //goToDashboard();
        window.show();
    }

    public void goToDashboard(){
        try{
            dashboard = (DashBoardController) changeScene("/Form/MainForm/DashBoard.fxml", "Enterprise Planning Resource System - Kraken force");
            dashboard.setApp(this);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void testForm(){
        try{
            SalesManListController test = new SalesManListController();
            test = (SalesManListController) changeScene("/Form/SalesmanModule/SalesManList.fxml", "Test");
            test.setApp(this);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void goToMainWindow() {
        try {
            mainw = (MainWindowController) changeScene("/Form/Login.fxml", "Main window");
            mainw.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToLogin() {

        try {
            login = (LoginController) changeScene("/Form/Login.fxml", "Login");
            login.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Initializable changeScene(String fxml, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = getClass().getResourceAsStream(fxml);
        loader.setLocation(getClass().getResource(fxml));

        Parent root = (Parent) loader.load(in);

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

package Controller;

import Boxes.AlertBox;
import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.DAO.UserTypeDAO;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.entity.UserType;
import Utils.Crypto;
import Utils.Security;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserCreatorController implements Initializable {

    @FXML
    private TextField tfUserName;

    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox<String> clUserRole;

    @FXML
    void createUser(ActionEvent event) {
        String username = tfUserName.getText();
        String email = tfEmail.getText();
        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        LoginInfo loginInfo = new LoginInfo();

        Security security = new Security();
        String password = security.generateRandomPassword(6);
        String hashPassword = security.generateHash(password);
        String type = clUserRole.getSelectionModel().getSelectedItem();
        UserTypeDAO userTypeDAO = new UserTypeDAO();
        UserType userType = userTypeDAO.findByName(type);

        System.out.println(password);
        loginInfo.setUsername(username);
        loginInfo.setEmail(email);
        loginInfo.setHashpw(hashPassword);
        loginInfo.setUserType(userType);
        AlertBox alertBox = new AlertBox();


        if (alertBox.InsertAlert("Create new user", "Do you really want to create this user") == true) {
            loginInfoDAO.saveLoginInfo(loginInfo);
        }

    }

    public ObservableList<String> setDataToCB(){
        UserTypeDAO userTypeDAO = new UserTypeDAO();
        List<UserType> userTypeList = userTypeDAO.getAll();
        ObservableList<String> userRoleList = FXCollections.observableArrayList();

        for(UserType userType : userTypeList){
            userRoleList.add(userType.getTypeName());
        }
        return userRoleList;
    }

    public Boolean checkExistInfo(String userName, String email){
        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        List<LoginInfo> loginInfoList = loginInfoDAO.getAll();

        for (LoginInfo loginInfo : loginInfoList){
            if(loginInfo.getUsername() == userName || loginInfo.getEmail() == email){
                return false;
            }
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clUserRole.setItems(setDataToCB());
    }
}

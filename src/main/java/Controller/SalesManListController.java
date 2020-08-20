package Controller;

import App.App;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.Salesman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesManListController implements Initializable {

    private App app;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
    @FXML
    private Button btnAdd;
    @FXML
    private GridPane gpTable;

    public void setApp(App app){
        this.app = app;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void AddSeller(ActionEvent actionEvent) {
        Salesman salesman = new Salesman();
        salesman.setName(tfName.getText());
        salesman.setPhone(tfPhone.getText());
        salesman.setAddress(tfAddress.getText());


        SalesManDAO salesManDAO = new SalesManDAO();
        salesManDAO.saveSalesMan(salesman);
    }

    public void AlertOperation(){

    }
}

package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSalerListController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfID;

    @FXML
    private Pagination pgPage;

    @FXML
    private TableView<?> tbCusInfo;

    @FXML
    private TableColumn<?, ?> clCusID;

    @FXML
    private TableColumn<?, ?> clCusName;

    @FXML
    private TableColumn<?, ?> clCusPhone;

    @FXML
    private TableColumn<?, ?> clCusAddress;



    public void addCustomer(ActionEvent actionEvent) {

    }

    public void updateCustomer(ActionEvent actionEvent) {

    }

    public void deleteCustomer(ActionEvent actionEvent) {

    }

    public void cancelAction(ActionEvent actionEvent) {

    }

    public void openCustomerList(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

package TestController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {

    @FXML
    private TextField tfSalesmanID;

    @FXML
    private ComboBox<?> cbCustomerName;

    @FXML
    private DatePicker dpCurrentDate;

    @FXML
    private TableView<?> tbProductList;

    Long salesmanID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate();
    }

    private java.sql.Date getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        dpCurrentDate.setValue(localDate);
        return sqlDate;
    }

    public void openAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Form/TestForm/AddProductToTable.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void saveOrderProduct(ActionEvent actionEvent) {
    }

    public Long getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(Long salesmanID) {
        this.salesmanID = salesmanID;
    }

}

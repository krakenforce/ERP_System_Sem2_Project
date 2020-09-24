package TestController;

import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductToTableController implements Initializable {

    @FXML
    private ComboBox<String> cbProductName;

    @FXML
    private Spinner<Integer> spAmount;


    public void AddProductToTable(ActionEvent actionEvent) {

    }

    public Product getProduct(){
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product product = dao.findByName(productName);
        return product;
    }

    public ObservableList setCBProductName(){
        ObservableList<String> obsProName = FXCollections.observableArrayList();
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getAll();

        for(Product items : list){
            obsProName.add(items.getName());
        }
        cbProductName.setItems(obsProName);
        return obsProName;
    }

    public void spinnerInit(){
        spAmount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99)
        );
    }
    public Long getSpinnerValue(){
        return (long)spAmount.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerInit();
        setCBProductName();
    }
}

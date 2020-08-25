package Controller;

import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.entity.GroupProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class SalesManProductGroupController implements Initializable {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfName2;
    @FXML
    private ComboBox<String> cbProGroup;
    @FXML
    private ListView<?> lvProGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToComboBox();
    }

    public void addProductGroup(ActionEvent event) {

    }

    public void setDataToComboBox(){
        ObservableList<String> list = FXCollections.observableArrayList();
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        List<GroupProduct> groupProductList  = groupProductDAO.getAll();
        for(GroupProduct value : groupProductList){
            list.add(value.getName());
        }
        cbProGroup.setItems(list);
    }
}

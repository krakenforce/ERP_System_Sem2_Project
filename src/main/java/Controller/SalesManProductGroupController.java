package Controller;

import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Salesman;
import Services.Hibernate.entity.Salesman_GroupProduct;
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
    private TextField tfSellerID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfName2;
    @FXML
    private ComboBox<String> cbProGroup;
    @FXML
    private ListView<String> lvProGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToComboBox();

    }

    public void addProductGroup(ActionEvent event) {
        //Get seller ID
        SalesManDAO salesManDAO = new SalesManDAO();
        long salesmanID = Long.parseLong(tfSellerID.getText());
        Salesman selectedSalesman = salesManDAO.findById(salesmanID);

        //Get Product group ID
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        GroupProduct selectedGroup = groupProductDAO.findByName(cbProGroup.getValue());

        
        //After add show them to list view
        Salesman_GroupProduct salesman_groupProduct = new Salesman_GroupProduct();
        salesman_groupProduct.setSalesman(selectedSalesman);
        salesman_groupProduct.setGroupProduct(selectedGroup);
        Salesman_ProductGroupDAO salesmanProGroup = new Salesman_ProductGroupDAO();
        salesmanProGroup.saveGroup(salesman_groupProduct);
        //setDataToListView();
    }
    public void updateSellerProGroup(ActionEvent event) {

    }

    public void deleteSellerProGroup(ActionEvent event) {
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        GroupProduct groupProduct = groupProductDAO.findByName(lvProGroup.getSelectionModel().getSelectedItem());
        Salesman_GroupProduct salesman_groupProduct = new Salesman_GroupProduct();
        salesman_groupProduct.setGroupProduct(groupProduct);
        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        salesman_productGroupDAO.deleteGroup(salesman_groupProduct);
    }

    public void cancelAction(ActionEvent event) {
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

    public void getSalesmanInfo(Long id, String salesmanName){
        tfSellerID.setText(id.toString());
        tfName.setText(salesmanName);
        tfName2.setText(salesmanName);
    }

    public void setDataToListView(Long id) {
        Salesman_ProductGroupDAO dao = new Salesman_ProductGroupDAO();
        ObservableList<String> product = FXCollections.observableArrayList();
        List<Salesman_GroupProduct> list = dao.selectBySalesmanID(id);
        for(Salesman_GroupProduct items : list){
            product.add(items.getGroupProduct().getName());
        }
        lvProGroup.setItems(product);
    }


}

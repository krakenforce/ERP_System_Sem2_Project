package Controller;

import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.DAO.Salesman_ProductGroupDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
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

    public void updateSellerProGroup(ActionEvent event) {
    }

    public void deleteSellerProGroup(ActionEvent event) {
    }

    public void cancelAction(ActionEvent event) {
    }

    public void setDataToListView(){
        //get data by salesman id to get groupProduct id
        
        //get group product object to get information and set it to a ObservableList

        //set ObservableList to the Listview
        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        List<Salesman_GroupProduct> list = salesman_productGroupDAO.selectBySalesmanID(Long.parseLong(tfSellerID.getText()));
        for(Salesman_GroupProduct items: list){
            list.add(items);
        }
    }
}

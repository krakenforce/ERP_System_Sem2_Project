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
import javafx.scene.control.TableView;
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
    @FXML
    private ListView<Double> lvCommission;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataToComboBox();
    }

    public void addProductGroup(ActionEvent event) {
        if(cbProGroup.getSelectionModel().getSelectedItem() == null){
            System.out.println("chưa chọn giá trị");
        }else{
            System.out.println("Đã chọn giá trị");
        }
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
        // fix this bug. add all group without add Group
        refreshListView(lvProGroup,setDataToLVProGroup(salesmanID) );
    }
    public void updateSellerProGroup(ActionEvent event) {
        System.out.println("sdjftiis");

    }

    public void deleteSellerProGroup(ActionEvent event) {
        SalesManDAO salesManDAO = new SalesManDAO();
        Salesman salesman = salesManDAO.findById(Long.parseLong(tfSellerID.getText()));

        GroupProductDAO groupProductDAO = new GroupProductDAO();
        GroupProduct groupProduct = groupProductDAO.findByName(lvProGroup.getSelectionModel().getSelectedItem());

        Salesman_ProductGroupDAO salesman_productGroupDAO = new Salesman_ProductGroupDAO();
        Salesman_GroupProduct salesman_groupProduct = salesman_productGroupDAO.selectBySalesmanAndGroupProductID(salesman.getId(),groupProduct.getId());
        salesman_productGroupDAO.deleteGroup(salesman_groupProduct);

        Long salesmanID = Long.parseLong(tfSellerID.getText());

        refreshListView(lvProGroup, setDataToLVProGroup(salesmanID));
    }

    public void cancelAction(ActionEvent event) {
    }

    public ObservableList<String> setDataToComboBox(){
        ObservableList<String> list = FXCollections.observableArrayList();
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        List<GroupProduct> groupProductList  = groupProductDAO.getAll();
        for(GroupProduct value : groupProductList){
            list.add(value.getName());
        }
        cbProGroup.setItems(list);
        return list;
    }

    public ObservableList setDataToLVProGroup(Long id) {
        Salesman_ProductGroupDAO dao = new Salesman_ProductGroupDAO();
        ObservableList<String> productList = FXCollections.observableArrayList();
        List<Salesman_GroupProduct> list = dao.selectBySalesmanID(id);
        for(Salesman_GroupProduct items : list){
            productList.add(items.getGroupProduct().getName());
        }
        lvProGroup.setItems(productList);
        return productList;
    }

    public void getSalesmanInfo(Long id, String salesmanName){
        tfSellerID.setText(id.toString());
        tfName.setText(salesmanName);
        tfName2.setText(salesmanName);
    }

    public void refreshListView(ListView listViewName, ObservableList obsList) {
        listViewName.getItems().clear();
        listViewName.setItems(obsList);
    }

}

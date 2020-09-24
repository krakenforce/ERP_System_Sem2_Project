package TestController;

import Repositories.IListBehavior;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestOrderProductController implements Initializable {

    @FXML
    private Spinner<Integer> amountSpinner;
    @FXML
    private CheckBox cbkEnough;
    @FXML
    private ComboBox<Long> cbDetailOrderID;
    @FXML
    private ComboBox<String> cbProductName;
    @FXML
    private ComboBox<String> cbSalesmanName;


    public Boolean checkBoxResult(){
        if(cbkEnough.isSelected()){
            return true;
        }else{
            return false;
        }
    }

    public Long getSpinnerValue(){
        return (long)amountSpinner.getValue();
    }

    public DetailOrder getDetailOrder(){
        Long id = cbDetailOrderID.getSelectionModel().getSelectedItem();
        DetailOrderDAO dao = new DetailOrderDAO();
        DetailOrder detailOrder = dao.findByID(id);
        return detailOrder;
    }

    public Product getProduct(){
        String productName = cbProductName.getSelectionModel().getSelectedItem();
        ProductDAO dao = new ProductDAO();
        Product product = dao.findByName(productName);
        return product;
    }

    public Salesman getSalesman(){
        String salesmanName = cbSalesmanName.getSelectionModel().getSelectedItem();
        SalesManDAO salesManDAO = new SalesManDAO();
        Salesman salesman = salesManDAO.findByName(salesmanName);
        return salesman;
    }

    @FXML
    public void addOrderProduct(ActionEvent event){
        long amount = getSpinnerValue();
        boolean enough = checkBoxResult();
        DetailOrder detailOrder = getDetailOrder();
        Product product = getProduct();
        Salesman salesman = getSalesman();

        OrderDAO dao = new OrderDAO();
        Order order = new Order(amount, enough, detailOrder, product, salesman);
        dao.saveOrder(order);
    }


    public void spinnerInit(){
        amountSpinner.setValueFactory(
               new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99)
       );
    }

    public ObservableList setCBDetailOrderID(){
        ObservableList<Long> obs = FXCollections.observableArrayList();
        DetailOrderDAO dao = new DetailOrderDAO();
        List<DetailOrder> list = dao.getAll();

        for(DetailOrder items : list){
            obs.add(items.getId());
        }
        cbDetailOrderID.setItems(obs);
        return obs;
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

    public ObservableList setCBSalesmanName(){
        ObservableList<String> obssalemanName = FXCollections.observableArrayList();
        SalesManDAO dao = new SalesManDAO();
        List<Salesman> list = dao.selectAll();

        for(Salesman items : list){
            obssalemanName.add(items.getName());
        }
        cbSalesmanName.setItems(obssalemanName);
        return obssalemanName;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerInit();
        setCBDetailOrderID();
        setCBProductName();
        setCBSalesmanName();
    }


}

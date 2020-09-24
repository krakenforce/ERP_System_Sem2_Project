package TestController;

import Services.Hibernate.DAO.CustomerDAO;
import Services.Hibernate.DAO.DetailOrderDAO;
import Services.Hibernate.entity.Customer;
import Services.Hibernate.entity.DetailOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TestDetailOrderController implements Initializable {
    @FXML
    private DatePicker dpDate;

    @FXML
    private CheckBox ckbxTrueFalse;

    @FXML
    private ComboBox<Long> cbCusID;

    @FXML
    void testAddDetailOrder(ActionEvent event) {

        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setDate(getDate());
        detailOrder.setCustomer(getSelectedCustomer(getCustomerId()));
        detailOrder.setPay(getCheckboxValue());

        DetailOrderDAO detailOrderDAO = new DetailOrderDAO();
        detailOrderDAO.saveDetailOrder(detailOrder);

    }

    public List<Customer> getCustomerList(){
        ObservableList<Long> customerList = FXCollections.observableArrayList();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> list = customerDAO.selectAllCustomer();

        for(Customer items: list){
            customerList.add(items.getId());
        }
        cbCusID.setItems(customerList);
        return list;
    }
    public Long getCustomerId(){
        return cbCusID.getSelectionModel().getSelectedItem();
    }

    public Customer getSelectedCustomer(Long id){
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer  = customerDAO.findByID(id);
        return customer;
    }

    private Date getDate(){
        return Date.valueOf(dpDate.getValue());
    }

    private Boolean getCheckboxValue(){
        if(ckbxTrueFalse.isSelected()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerList();
    }
}

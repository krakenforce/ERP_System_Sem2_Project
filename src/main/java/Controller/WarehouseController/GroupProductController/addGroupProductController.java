package Controller.WarehouseController.GroupProductController;

import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.entity.GroupProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addGroupProductController implements Initializable {

    @FXML
    private TextField id_TenNhomSanPham;

    @FXML
    private TextField id_PhanTramNhanVien;

    private GroupProductDAO groupProductDAO = new GroupProductDAO();

    @FXML
    void themNhomSanPham(ActionEvent event) {

        String tenNhom = id_TenNhomSanPham.getText();

        Double phanTramNhanVien = Double.valueOf(id_PhanTramNhanVien.getText());
        GroupProduct groupProduct = new GroupProduct();

        groupProduct.setName(tenNhom);
        groupProduct.setCommission(phanTramNhanVien);

        groupProductDAO.saveGroupProduct(groupProduct);

    }

    public void LamMoi(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

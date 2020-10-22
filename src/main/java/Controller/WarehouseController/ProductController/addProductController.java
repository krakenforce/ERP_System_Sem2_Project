package Controller.WarehouseController.ProductController;

import NodeService.ComboBoxService;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.UnitDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class addProductController implements Initializable {
    @FXML
    private TextField id_TenSanPham;

    @FXML
    private TextField id_GiaBanLe;

    @FXML
    private TextField id_GiaBanSi;

    @FXML
    private TextField id_PhanTramLoiNhuan;

    @FXML
    private TextField id_DuTinhGiaBan;

    @FXML
    private ComboBox<Unit> id_DonViTinh;


    @FXML
    private TextField id_TonKhoToiThieu;

    @FXML
    private ComboBox<GroupProduct> id_NhomSanPham;

    private ProductDAO productDAO = new ProductDAO();

    private ComboBoxService<Unit> unitComboBoxService = new ComboBoxService<Unit>();

    private ComboBoxService<GroupProduct> groupProductComboBoxService = new ComboBoxService<GroupProduct>();

    private UnitDAO unitDAO = new UnitDAO();

    private GroupProductDAO groupProductDAO = new GroupProductDAO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.khoiTaoComboboxGroupProduct();
        this.khoiTaoComboboxUnit();
    }

    public void LamMoi(){

    }
    public void tinhGiaBan(ActionEvent actionEvent) {
    }

    public void nhapSanPham(ActionEvent actionEvent) {

        String ten = id_TenSanPham.getText();
        String tonKhoToiThieu = id_TonKhoToiThieu.getText();
        String phanTramLoiNhuan = id_PhanTramLoiNhuan.getText();
        String giaBanSi = id_GiaBanSi.getText();
        String giaBanLe = id_GiaBanLe.getText();
        GroupProduct groupProduct = id_NhomSanPham.getSelectionModel().getSelectedItem();
        Unit donViTinh = id_DonViTinh.getSelectionModel().getSelectedItem();

        Product product = new Product();
        product.setName(ten);
        product.setPrice(Long.valueOf(giaBanSi));
        product.setRateProfit(Double.valueOf(phanTramLoiNhuan));
        product.setRetailPrice(Long.valueOf(giaBanLe));
        product.setMinimumInventory(Long.valueOf(tonKhoToiThieu));
        product.setGroupProduct(groupProduct);
        product.setUnit(donViTinh);
        productDAO.saveProduct(product);

    }

    private void khoiTaoComboboxUnit(){

        List<Unit> unitList = unitDAO.findAll();
        unitComboBoxService.setComboBox(id_DonViTinh);
        unitComboBoxService.taiDuLieu(unitList);
    }

    private void khoiTaoComboboxGroupProduct(){

        List<GroupProduct> groupProductList = groupProductDAO.findAll();
        groupProductComboBoxService.setComboBox(id_NhomSanPham);
        groupProductComboBoxService.taiDuLieu(groupProductList);
    }
}

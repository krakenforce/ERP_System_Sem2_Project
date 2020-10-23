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
import java.util.*;

public class addProductController implements Initializable {
    @FXML
    private TextField id_TenSanPham;

    @FXML
    private TextField id_GiaBanLe;

    @FXML
    private TextField id_GiaBanSi;

    @FXML TextField tfBarcode;

    @FXML
    private TextField id_PhanTramLoiNhuan;

    @FXML
    private TextField id_DuTinhGiaBan;

    @FXML
    private ComboBox<String> id_DonViTinh;


    @FXML
    private TextField id_TonKhoToiThieu;

    @FXML
    private ComboBox<String> id_NhomSanPham;

    private ProductDAO productDAO = new ProductDAO();

    private ComboBoxService<String> unitComboBoxService = new ComboBoxService<String>();

    private ComboBoxService<String> groupProductComboBoxService = new ComboBoxService<String>();

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
        GroupProductDAO groupProductDAO = new GroupProductDAO();
        UnitDAO unitDAO = new UnitDAO();

        String ten = id_TenSanPham.getText();
        String tonKhoToiThieu = id_TonKhoToiThieu.getText();
        String phanTramLoiNhuan = id_PhanTramLoiNhuan.getText();
        String giaBanSi = id_GiaBanSi.getText();
        String giaBanLe = id_GiaBanLe.getText();
        String barcode = tfBarcode.getText();
        GroupProduct groupProduct = groupProductDAO.findByName(id_NhomSanPham.getSelectionModel().getSelectedItem());
        Unit donViTinh = unitDAO.findByPrimaryUnit(id_DonViTinh.getSelectionModel().getSelectedItem());




        Product product = new Product();
        product.setName(ten);
        product.setPrice(Long.valueOf(giaBanLe));
        product.setRateProfit(Double.valueOf(phanTramLoiNhuan));
        product.setRetailPrice(Long.valueOf(giaBanSi));
        product.setMinimumInventory(Long.valueOf(tonKhoToiThieu));
        product.setGroupProduct(groupProduct);
        product.setUnit(donViTinh);
        product.setBarCode(barcode);
        productDAO.saveProduct(product);

    }

    private void khoiTaoComboboxUnit(){

        UnitDAO unitDAO = new UnitDAO();
        List<Unit> unitList = unitDAO.findAll();
        List<String> test2  = new ArrayList<>();

        for(Unit unit : unitList){
            test2.add(unit.getUnitPrimary());
        }
        unitComboBoxService.setComboBox(id_DonViTinh);
        unitComboBoxService.taiDuLieu(test2);
    }

    private void khoiTaoComboboxGroupProduct(){

        GroupProductDAO groupProductDAO = new GroupProductDAO();
        List<GroupProduct> groupProductList = groupProductDAO.findAll();
        groupProductComboBoxService.setComboBox(id_NhomSanPham);
        List<String> test  = new ArrayList<>();

        for(GroupProduct groupProduct : groupProductList){
            test.add(groupProduct.getName());
        }

        groupProductComboBoxService.taiDuLieu(test);
    }
}

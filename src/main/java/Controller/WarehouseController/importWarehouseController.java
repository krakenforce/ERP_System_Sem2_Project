package Controller.WarehouseController;

import ModelView.ProductImport;
import NodeService.ComboBoxService;
import Services.Hibernate.DAO.BillWarehousingDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.WarehouseDAO;
import Services.Hibernate.entity.BillWarehousing;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Warehouse;
import Services.Hibernate.entity.WarehousingDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class importWarehouseController implements Initializable {

    @FXML
    private ComboBox<Product> id_CbSanPham;

    @FXML
    private TextField id_GiaNhaCungCap;

    @FXML
    private TextField id_SoLuong;

    @FXML
    private TableView<ProductImport> id_TableSPNhapKho;

    @FXML
    private TableColumn<ProductImport, String> id_ColTenSP;

    @FXML
    private TableColumn<ProductImport, Long> id_ColGiaNhaCungCap;

    @FXML
    private TableColumn<ProductImport, Long> id_ColSoLuong;

    @FXML
    private TableColumn<ProductImport, Boolean> id_ColXoa;

    @FXML
    private ComboBox<Warehouse> id_CbKho;

    @FXML
    private TextField id_PhiVanChuyen;

    private ComboBoxService<Warehouse> warehouseComboBoxService = new ComboBoxService<Warehouse>();;

    private ComboBoxService<Product> productComboBoxService = new ComboBoxService<Product>();

    private WarehouseDAO warehouseDAO = new WarehouseDAO();

    private ProductDAO productDAO = new ProductDAO();

    private BillWarehousingDAO billWarehousingDAO = new BillWarehousingDAO();
    public void LamMoi(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.khoiTaoComboBoxKho();
        this.khoiTaoComboBoxProduct();

        id_ColTenSP.setCellValueFactory(new PropertyValueFactory<ProductImport, String>("tenSp"));
        id_ColSoLuong.setCellValueFactory(new PropertyValueFactory<ProductImport, Long>("soLuong"));
        id_ColGiaNhaCungCap.setCellValueFactory(new PropertyValueFactory<ProductImport, Long>("giaNhaCungCap"));

        Callback<TableColumn<ProductImport, Boolean>, TableCell<ProductImport, Boolean>> CellSuaFactory
                = (TableColumn<ProductImport, Boolean> param) -> new ButtonCellDelete();

        id_ColXoa.setCellFactory(CellSuaFactory);

    }

    private void khoiTaoComboBoxKho(){
        warehouseComboBoxService.setComboBox(id_CbKho);
        List<Warehouse> warehouseList = warehouseDAO.getAll();
        warehouseComboBoxService.taiDuLieu(warehouseList);
    }

    private void khoiTaoComboBoxProduct(){
        productComboBoxService.setComboBox(id_CbSanPham);
        List<Product> productList = productDAO.getAll();
        productComboBoxService.taiDuLieu(productList);
    }


    public void nhapKho(ActionEvent actionEvent) {

        Long phiVanChuyen = Long.valueOf(id_PhiVanChuyen.getText());
        Date ngay = Date.valueOf(LocalDate.now());
        Warehouse warehouse = id_CbKho.getSelectionModel().getSelectedItem();

        BillWarehousing billWarehousing = new BillWarehousing();
        billWarehousing.setDate(ngay);
        billWarehousing.setWarehouse(warehouse);

        Set<WarehousingDetails> warehousingDetailsSet = this.taoDSwarehousingDetails(billWarehousing);
        billWarehousing.setWarehousingDetailsSet(warehousingDetailsSet);
        //billWarehousing
        System.out.println(billWarehousing.getWarehousingDetailsSet());
        billWarehousingDAO.saveBillWarehousing(billWarehousing);

        id_TableSPNhapKho.getItems().clear();
    }

    private  Set<WarehousingDetails> taoDSwarehousingDetails(BillWarehousing billWarehousing){

        Set<WarehousingDetails> warehousingDetailsSet = new HashSet<WarehousingDetails>(0);
        List<ProductImport> productImportList = id_TableSPNhapKho.getItems();
        for(ProductImport item : productImportList){
            WarehousingDetails warehousingDetails = new WarehousingDetails();
            warehousingDetails.setPrice(item.getGiaNhaCungCap());
            warehousingDetails.setAmount(item.getSoLuong());
            warehousingDetails.setProduct(productDAO.findById(item.getId_Product()));
            warehousingDetails.setBillWarehousing(billWarehousing);
            warehousingDetailsSet.add(warehousingDetails);
        }
        return warehousingDetailsSet;
    }
    public void themSPNhapKho(ActionEvent actionEvent) {

        Product product = id_CbSanPham.getSelectionModel().getSelectedItem();
        Long id = product.getId();
        String tenSP = product.getName();
        Long soLuong = Long.valueOf(id_SoLuong.getText());
        Long giaNhaCungCap = Long.valueOf(id_GiaNhaCungCap.getText());

        ProductImport productImport = new ProductImport();
        productImport.setId_Product(id);
        productImport.setGiaNhaCungCap(giaNhaCungCap);
        productImport.setTenSp(tenSP);
        productImport.setSoLuong(soLuong);

        id_TableSPNhapKho.getItems().add(productImport);
    }

    public class ButtonCellDelete extends TableCell<ProductImport, Boolean> {

        private Button Button = new Button("Xóa");

        public ButtonCellDelete() {
            // TODO Auto-generated constructor stub
            Button.setOnMousePressed(new EventHandler<MouseEvent>() {


                @Override public void handle(MouseEvent mouseEvent) {
//                    //KhachHang khachhang1 = getTableRow().getItem();
//                    Crime crime = getTableRow().getItem();
//                    getTableView().getItems().remove(crime);
//                    listCrimeCurent.remove(crime);
//                    listCrimeDelete.add(crime);
                    System.out.println("Xóa");
                    ProductImport productImport = getTableRow().getItem();
                    id_TableSPNhapKho.getItems().remove(productImport);
                }
            });
        }

        /** places an add button in the row only if the row is not empty. */
        @Override protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(Button);
            } else {
                setGraphic(null);
            }
        }
    }
}

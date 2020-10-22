package Controller.WarehouseController.ProductController;

import NodeService.ComboBoxService;
import NodeService.PaginationService;
import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.PriceOldDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.DAO.UnitDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.PriceOld;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class editProductController implements Initializable {

    @FXML
    private TextField id_TenSp;

    @FXML
    private TextField id_Price;

    @FXML
    private TextField id_TonToiThieu;

    @FXML
    private TextField id_LoiNhuan;

    @FXML
    private TextField id_DuGiaBan;

    @FXML
    private ComboBox<Unit> id_DonVi;

    @FXML
    private ComboBox<GroupProduct> id_NhomSP;


    @FXML
    private ComboBox<String> combo_SearchStyle;
    @FXML
    private AnchorPane anchorpane_InputDataSearch;
    @FXML
    private Pagination pagination_Product;

    private TextField text_NameSeacrh;
    private ComboBox<GroupProduct> combo_GroupProduct;

    private TableView<Product> productTableView;
    private TableColumn<Product, String> col_ten;
    private TableColumn<Product, Long> col_DonGia;
    private TableColumn<Product, String> col_DonVi;
    private TableColumn<Product, Double> col_LoiNhuan;

    private PaginationService<Product> productPaginationService = new PaginationService<Product>();
    private ComboBoxService<GroupProduct> groupProductComboBoxService = new ComboBoxService<GroupProduct>();
    private ComboBoxService<Unit> unitComboBoxService = new ComboBoxService<Unit>();
    private ComboBoxService<GroupProduct> groupProductUpdateComboBoxService = new ComboBoxService<GroupProduct>();
    private ProductDAO productDAO = new ProductDAO();
    private GroupProductDAO groupProductDAO = new GroupProductDAO();
    private PriceOldDAO priceOldDAO = new PriceOldDAO();
    private UnitDAO unitDAO = new UnitDAO();
    private Product productUpdate;
    private Product productSelect;

    public void LamMoi(){

    }
    public void SearchPrisoner(ActionEvent actionEvent) {
        List<Product> productList = new ArrayList<Product>();
        productList = this.GetListPrisonerSeacrh();
        productPaginationService.setPagination(pagination_Product);
        productPaginationService.setSopt(10);
        productPaginationService.setTableView(productTableView);
        productPaginationService.createPagination(productList);

    }

    private List<Product> GetListPrisonerSeacrh() {

        List<Product> productList = new ArrayList<Product>();

        String style = combo_SearchStyle.getValue();

        switch(style) {
            case "Nhóm Sản Phẩm":
                // code block
                productList = this.GetListPrisonerByPunishment();
                break;
            case "Tên Sản Phẩm":
                // code block
                productList = this.GetListPrisonerByName();
                break;
            default:
                // code block
        }
        return productList;
    }

    private List<Product> GetListPrisonerByName() {
        String nameSeacrh = text_NameSeacrh.getText();
        return productDAO.GetListProductByName(nameSeacrh);
    }

    private List<Product> GetListPrisonerByPunishment() {
        Set<Product> productHashSet = combo_GroupProduct.getSelectionModel().getSelectedItem().getProductHashSet();
        return new ArrayList<Product>(productHashSet);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        unitComboBoxService.setComboBox(id_DonVi);
        unitComboBoxService.taiDuLieu(unitDAO.findAll());

        groupProductUpdateComboBoxService.setComboBox(id_NhomSP);
        groupProductUpdateComboBoxService.taiDuLieu(groupProductDAO.findAll());

        combo_GroupProduct = new ComboBox<GroupProduct>();
        combo_GroupProduct.setPrefWidth(200);
        combo_GroupProduct.setPrefHeight(25);


        text_NameSeacrh = new TextField();
        text_NameSeacrh.setPrefWidth(200);
        text_NameSeacrh.setPrefHeight(25);

        this.InitComboBoxStyle();

        productTableView = new TableView<Product>();

        col_ten = new TableColumn<Product, String>("Tên SP");
        col_ten.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));

        col_DonGia = new TableColumn<Product, Long>("Đơn Giá");
        col_DonGia.setCellValueFactory(new PropertyValueFactory<Product, Long>("price"));

        col_DonVi = new TableColumn<Product, String>("Đơn Vị Tính");
        col_DonVi.setCellValueFactory(new PropertyValueFactory<Product, String>("unitString"));

        col_LoiNhuan = new TableColumn<Product, Double>("Lợi Nhuận");
        col_LoiNhuan.setCellValueFactory(new PropertyValueFactory<Product, Double>("rateProfit"));

        productTableView.getColumns().addAll(col_ten,col_DonGia,col_DonVi,col_LoiNhuan);

        productTableView.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                System.out.println("ádasdasdasdasd");
                productSelect = productTableView.getSelectionModel().getSelectedItem();
                Long idProduct = productSelect.getId();
                productUpdate = productDAO.findById(idProduct);
                id_TenSp.setText(productUpdate.getName());
                id_LoiNhuan.setText(productUpdate.getRateProfit().toString());
                id_Price.setText(productUpdate.getPrice().toString());
                id_TonToiThieu.setText(productUpdate.getMinimumInventory().toString());
                id_DonVi.getSelectionModel().select(productUpdate.getUnit());
                id_NhomSP.getSelectionModel().select(productUpdate.getGroupProduct());

            }
        });
    }

    private void InitComboBoxStyle() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Nhóm Sản Phẩm");
        stringList.add("Tên Sản Phẩm");
        ObservableList<String> styleList = FXCollections.observableArrayList(stringList);
        combo_SearchStyle.setItems(styleList);
    }

    public void SelectStyleSearch(ActionEvent actionEvent) {
        String style = combo_SearchStyle.getValue();

        switch(style) {
            case "Nhóm Sản Phẩm":
                // code block
                this.InitComboBoxPunishment();
                break;
            case "Tên Sản Phẩm":
                // code block
                this.InitTextName();
                break;
            default:
                // code block
        }
    }

    private void InitComboBoxPunishment() {

        anchorpane_InputDataSearch.getChildren().clear();
        anchorpane_InputDataSearch.getChildren().add(combo_GroupProduct);
        groupProductComboBoxService.setComboBox(combo_GroupProduct);
        List<GroupProduct> groupProductList = groupProductDAO.findAll();
        groupProductComboBoxService.taiDuLieu(groupProductList);

    }


    private void InitTextName() {

        anchorpane_InputDataSearch.getChildren().clear();
        anchorpane_InputDataSearch.getChildren().add(text_NameSeacrh);
    }

    public void suaThongTinSP(ActionEvent actionEvent) {
        if(productUpdate != null){
            productTableView.getItems().remove(productSelect);

            String ten = id_TenSp.getText();
            Long tonKho = Long.valueOf(id_TonToiThieu.getText());
            Double loiNhuan = Double.valueOf(id_LoiNhuan.getText());
            Long giaBan = Long.valueOf(id_Price.getText());
            Unit unit = id_DonVi.getSelectionModel().getSelectedItem();
            GroupProduct groupProduct = id_NhomSP.getSelectionModel().getSelectedItem();
            Long giaBanCu = productUpdate.getPrice();

            PriceOld priceOld = new PriceOld();
            priceOld.setDate(Date.valueOf(LocalDate.now()));
            priceOld.setProduct(productUpdate);
            priceOld.setPriceImport(giaBanCu);
            priceOldDAO.savePriceOld(priceOld);

            productUpdate.setUnit(unit);
            productUpdate.setName(ten);
            productUpdate.setGroupProduct(groupProduct);
            productUpdate.setPrice(giaBan);
            productUpdate.setMinimumInventory(tonKho);
            productUpdate.setRateProfit(loiNhuan);
            productDAO.updateProduct(productUpdate);
            productTableView.getItems().add(productUpdate);

            this.afterUpdate();
            productUpdate = null;
        }
    }
    private void afterUpdate(){
        id_DonVi.getSelectionModel().clearSelection();
        id_NhomSP.getSelectionModel().clearSelection();
        id_DuGiaBan.setText("");
        id_LoiNhuan.setText("");
        id_TenSp.setText("");
        id_Price.setText("");
        id_TonToiThieu.setText("");
    }
}

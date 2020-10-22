package Controller.WarehouseController;

import NodeService.ComboBoxService;
import Services.Hibernate.DAO.WarehouseDAO;
import Services.Hibernate.DAO.WarehousingDetailDAO;
import Services.Hibernate.entity.Order;
import Services.Hibernate.entity.Warehouse;
import Services.Hibernate.entity.WarehousingDetails;
import Utils.OutOfWarehousing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class exportProductWarehouse implements Initializable {

    @FXML
    private ComboBox<Warehouse> id_ComboBoxKho;

    private Order order;

    private OutOfWarehousing outOfWarehousing = new OutOfWarehousing();

    @FXML
    private Label id_LbTenSP;

    @FXML
    private TableView<WarehousingDetails> id_TableSPTonKho;

    @FXML
    private TableColumn<WarehousingDetails, Long> id_ColSoLuong;

    @FXML
    private TableColumn<WarehousingDetails, Date> id_ColNgayNhap;

    private WarehousingDetailDAO warehousingDetailDAO = new WarehousingDetailDAO();
    private WarehouseDAO warehouseDAO = new WarehouseDAO();
    private ComboBoxService<Warehouse> warehouseComboBoxService = new ComboBoxService<Warehouse>();

    private void khoiTaoComboBoxWarehouse(){
        warehouseComboBoxService.setComboBox(id_ComboBoxKho);
        warehouseComboBoxService.taiDuLieu(warehouseDAO.getAll());
    }
    @FXML
    void xuatKho(ActionEvent event) {

        if(outOfWarehousing.createDeliveryDetail(order,id_TableSPTonKho.getItems())){
            order.setEnough(true);
        }
        else {
            order.setEnough(false);
        }

        Warehouse warehouse = id_ComboBoxKho.getSelectionModel().getSelectedItem();

        khoiTaoBangTonKho(warehouse.getId());


    }

    public void LamMoi(){

    }

    public void setOrder(Order order){
        this.order = order;
        id_LbTenSP.setText(order.getProduct().getName());
        System.out.println("set Order");
    }

    public boolean getOrderEnough(){
        return order.isEnough();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_ColSoLuong.setCellValueFactory(new PropertyValueFactory<WarehousingDetails, Long>("amount"));
        id_ColNgayNhap.setCellValueFactory(new PropertyValueFactory<WarehousingDetails, Date>("date"));

        this.khoiTaoComboBoxWarehouse();
    }

    private void khoiTaoBangTonKho(Long warehouseID ){
        ObservableList<WarehousingDetails> orderObservableList = FXCollections.observableArrayList(
                warehousingDetailDAO.findByProductIdAndWarehousingId(order.getProduct().getId(),warehouseID));
        id_TableSPTonKho.setItems(orderObservableList);
    }

    public void xemTonKho(ActionEvent actionEvent) {

        Warehouse warehouse = id_ComboBoxKho.getSelectionModel().getSelectedItem();

        khoiTaoBangTonKho(warehouse.getId());
    }
}

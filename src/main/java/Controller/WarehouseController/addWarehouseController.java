package Controller.WarehouseController;

import NodeService.ComboBoxService;
import Services.Hibernate.DAO.WarehouseDAO;
import Services.Hibernate.entity.CentalWarehouse;
import Services.Hibernate.entity.Warehouse;
import Services.Hibernate.entity.WarehouseOfStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addWarehouseController implements Initializable {

    @FXML
    private TextField id_TenKho;

    @FXML
    private TextField id_DiaChiKho;

    @FXML
    private ComboBox<String> id_LoaiKho;

    private ComboBoxService<String> comboBoxService ;

    private WarehouseDAO warehouseDAO = new WarehouseDAO();
    public void LamMoi(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBoxService = new ComboBoxService<String>();
        List<String> listLoaiKho = new ArrayList<String>();
        listLoaiKho.add("Kho Trung Tâm");
        listLoaiKho.add("Kho Cửa Hàng");
        comboBoxService.setComboBox(id_LoaiKho);
        comboBoxService.taiDuLieu(listLoaiKho);


    }

    public void themKho(ActionEvent actionEvent) {

        String tenkho = id_TenKho.getText();
        String diachi = id_DiaChiKho.getText();
        String loaikho = id_LoaiKho.getSelectionModel().getSelectedItem();

        if(loaikho.equals("Kho Trung Tâm")) {
            Warehouse warehouse = new CentalWarehouse();
            warehouse.setName(tenkho);
            warehouse.setAddress(diachi);
            warehouseDAO.saveWarehouse(warehouse);
        }
        else {
            Warehouse warehouse = new WarehouseOfStore();
            warehouse.setName(tenkho);
            warehouse.setAddress(diachi);
            warehouseDAO.saveWarehouse(warehouse);
        }
    }
}

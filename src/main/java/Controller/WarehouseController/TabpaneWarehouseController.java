package Controller.WarehouseController;

import NodeService.TabPaneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class TabpaneWarehouseController implements Initializable {

    @FXML
    private Tab id_TabNhapKhoHangLoi;
    @FXML
    private Tab id_TabNhapKhoHangDu;
    @FXML
    private Tab id_TabThemKho;
    @FXML
    private Tab id_TabXuatKho;
    @FXML
    private Tab id_TabNhapKho;

    private TabPaneService tabPaneService = new TabPaneService();

    public void LamMoi(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_TabNhapKho.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/importWarehouse.fxml"));
        //id_TabNhapKhoHangLoi.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/importWarehouse.fxml"));
        //id_TabNhapKhoHangDu.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/importWarehouse.fxml"));
        id_TabThemKho.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/addWarehouse.fxml"));
        id_TabXuatKho.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/exportWarehouse.fxml"));

    }
}

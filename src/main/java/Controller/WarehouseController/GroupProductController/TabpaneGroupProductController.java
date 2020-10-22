package Controller.WarehouseController.GroupProductController;

import NodeService.TabPaneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class TabpaneGroupProductController implements Initializable {

    @FXML
    private Tab id_TabNhomSanPham;

    private TabPaneService tabPaneService = new TabPaneService();


    public void LamMoi(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_TabNhomSanPham.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/GroupProductManager/addGroupProduct.fxml"));
    }
}

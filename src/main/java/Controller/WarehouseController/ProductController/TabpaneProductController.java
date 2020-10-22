package Controller.WarehouseController.ProductController;

import NodeService.TabPaneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class TabpaneProductController implements Initializable {

    @FXML
    private Tab id_TabThemSanPham;

    @FXML
    private Tab id_TabChinhSuaSP;
    private TabPaneService tabPaneService = new TabPaneService();

    public void LamMoi(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_TabThemSanPham.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/ProductManager/addProduct.fxml"));
        id_TabChinhSuaSP.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/ProductManager/editProduct.fxml"));

    }
}

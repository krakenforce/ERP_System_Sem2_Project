package Controller.WarehouseController.UnitController;

import NodeService.TabPaneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class TabpaneUnitContriller implements Initializable {

    @FXML
    private Tab id_TabThemDonVi;

    private TabPaneService tabPaneService = new TabPaneService();
    public void LamMoi(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_TabThemDonVi.setContent(tabPaneService.LoadTabAsAnchorPane("/Form/WarehouseModule/UnitManager/addUnit.fxml"));
    }
}

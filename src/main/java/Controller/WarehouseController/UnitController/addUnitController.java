package Controller.WarehouseController.UnitController;

import Services.Hibernate.DAO.UnitDAO;
import Services.Hibernate.entity.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addUnitController implements Initializable {

    @FXML
    private TextField id_DVChinh;

    @FXML
    private TextField id_GTDonVi;

    @FXML
    private TextField id_DVQuyDoi;

    @FXML
    private TextField id_GTQuyDoi;

    private UnitDAO unitDAO = new UnitDAO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void LamMoi(){

    }
    public void themDV(ActionEvent actionEvent) {

        String tenDVChinh = id_DVChinh.getText();
        String tenDVQuyDoi = id_DVQuyDoi.getText();
        Long giatriDvChinh = Long.valueOf(id_GTDonVi.getText());
        Long giatriDvQuyDoi = Long .valueOf(id_GTQuyDoi.getText());

        Unit unit = new Unit();
        unit.setUnitPrimary(tenDVChinh);
        unit.setUnitExchange(tenDVQuyDoi);
        unit.setValuePrimary(giatriDvChinh);
        unit.setValueExchange(giatriDvQuyDoi);

        unitDAO.saveUnit(unit);

    }
}

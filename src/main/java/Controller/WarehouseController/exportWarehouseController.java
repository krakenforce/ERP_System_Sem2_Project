package Controller.WarehouseController;

import NodeService.DialogImportSurplusOrError;
import NodeService.DialogService;
import Services.Hibernate.DAO.OrderDAO;
import Services.Hibernate.entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class exportWarehouseController implements Initializable {

    @FXML
    private TextField id_MaHD;

    @FXML
    private TableView<Order> id_TableOrderProduct;

    @FXML
    private TableColumn<Order, String> id_ColTenSp;

    @FXML
    private TableColumn<Order, Long> id_ColSoLuong;

    @FXML
    private TableColumn<Order, String> id_ColTrangThai;

    @FXML
    private TableColumn<Order, Boolean> id_ColButtonXem;

    @FXML
    private TableColumn<Order, Boolean> id_ColButtonNhapKho;

    private OrderDAO orderDAO = new OrderDAO();

    private DialogService dialogService = new DialogService();
    private DialogImportSurplusOrError dialogImportSurplusOrError = new DialogImportSurplusOrError();

    @FXML
    void timHD(ActionEvent event) {

        Long idHD = Long.valueOf(id_MaHD.getText());
        List<Order> orderList = orderDAO.findByDetailOrderID(idHD);
        if(orderList != null) {
            ObservableList<Order> orderObservableList = FXCollections.observableArrayList(orderList);
            id_TableOrderProduct.setItems(orderObservableList);
        }
    }

    public void LamMoi(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_ColTenSp.setCellValueFactory(new PropertyValueFactory<Order, String>("nameProduct"));
        id_ColSoLuong.setCellValueFactory(new PropertyValueFactory<Order, Long>("amount"));
        id_ColTrangThai.setCellValueFactory(new PropertyValueFactory<Order, String>("enough"));

        Callback<TableColumn<Order, Boolean>, TableCell<Order, Boolean>> CellXemFactory
                = (TableColumn<Order, Boolean> param) -> new ButtonCellXem();

        id_ColButtonXem.setCellFactory(CellXemFactory);

        Callback<TableColumn<Order, Boolean>, TableCell<Order, Boolean>> CellNhapKhoFactory
                = (TableColumn<Order, Boolean> param) -> new ButtonCellNhapKho();

        id_ColButtonNhapKho.setCellFactory(CellNhapKhoFactory);
    }

    public class ButtonCellXem extends TableCell<Order, Boolean> {

        private javafx.scene.control.Button Button = new Button("Xem Tồn Kho");

        public ButtonCellXem() {
            // TODO Auto-generated constructor stub
            Button.setOnMousePressed(new EventHandler<MouseEvent>() {


                @Override public void handle(MouseEvent mouseEvent) {

                    System.out.println("Xem Tồn Kho");
                    Order order = getTableRow().getItem();
                    if(!order.isEnough()) {
                        dialogService.ShowDialogTonKhoSP(orderDAO.findByID(order.getId())
                                , "/Form/WarehouseModule/exportProductWarehouse.fxml");
                        id_TableOrderProduct.getItems().remove(order);
                        System.out.println("kiểm tra đủ chưa sau khi xuất kho: "+dialogService.isEnough());
                        order.setEnough(dialogService.isEnough());
                        id_TableOrderProduct.getItems().add(order);
                    }

                    //id_TableOrderProduct.setItems(orderObservableList);
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

    public class ButtonCellNhapKho extends TableCell<Order, Boolean> {

        private javafx.scene.control.Button Button = new Button("Nhập Kho");

        public ButtonCellNhapKho() {
            // TODO Auto-generated constructor stub
            Button.setOnMousePressed(new EventHandler<MouseEvent>() {


                @Override public void handle(MouseEvent mouseEvent) {

                    System.out.println("Nhập KHo");
                    Order order = getTableRow().getItem();
                    if(order.isEnough()) {
                        dialogImportSurplusOrError.showDialogImportSurplusOrError(orderDAO.findByID(order.getId())
                                , "/Form/WarehouseModule/importWarehouseSurplusOrError.fxml");

                    }

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

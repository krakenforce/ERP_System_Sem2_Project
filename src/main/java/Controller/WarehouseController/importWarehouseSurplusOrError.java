package Controller.WarehouseController;

import Services.Hibernate.DAO.*;
import Services.Hibernate.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class importWarehouseSurplusOrError implements Initializable {

        @FXML
        private Label id_TieuDe;

        @FXML
        private TextField id_SoLuongNhap;

        @FXML
        private TableView<WarehousingDetails> tableViewPhieuXuatKho;

        @FXML
        private TableColumn<WarehousingDetails, Long> col_Ma;

        @FXML
        private TableColumn<WarehousingDetails, Long> id_SoLuongCongLai;

        @FXML
        private RadioButton id_HAngDu;

        @FXML
        private RadioButton id_HangLoi;


        private Order order;

        private ToggleGroup toggleGroup = new ToggleGroup();
        private WarehousingDetailDAO warehousingDetailDAO = new WarehousingDetailDAO();
        private BillWarehousingDAO billWarehousingDAO = new BillWarehousingDAO();
        private DeliveryDetail_WarehousingDao deliveryDetailWarehousingDao = new DeliveryDetail_WarehousingDao();
        private PriceOldDAO priceOldDAO = new PriceOldDAO();
        private ReceiptProductDao receiptProductDao = new ReceiptProductDao();

        @FXML
        void nhapKho(ActionEvent event) {

            Long soLuongNhap = Long.valueOf(id_SoLuongNhap.getText());

            if((soLuongNhap + this.tinhSoLuongTra()) <= order.getAmount()) {

                if (id_HAngDu.isSelected()) {
                    List<Delivery_Warehousing> delivery_warehousingList =
                            deliveryDetailWarehousingDao.getListDeliveryWarehouse(order.getId());

                    System.out.println(delivery_warehousingList);
                    RecieptsProduct recieptsProduct = new RecieptsProduct();
                    recieptsProduct.setAmout(soLuongNhap);
                    recieptsProduct.setOrder(order);
                    recieptsProduct.setDate(Date.valueOf(LocalDate.now()));
                    recieptsProduct.setTotalMoney(soLuongNhap * this.getGiaBanTaiThoiDiemXuatHD(order));
                    recieptsProduct.setReason(id_HAngDu.isSelected() == true? " Hàng Dư" : "Hàng Lỗi");
                    receiptProductDao.saveRecieptsProduct(recieptsProduct);

                    for (Delivery_Warehousing item : delivery_warehousingList) {
                        if (item.getAmount() >= soLuongNhap) {
                            WarehousingDetails warehousingDetails = item.getWarehousingDetails();
                            warehousingDetails.setAmount(soLuongNhap + warehousingDetails.getAmount());
                            warehousingDetailDAO.updateWarehousingDetail(warehousingDetails);
                            soLuongNhap = Long.valueOf(0);
                            break;
                        } else {
                            WarehousingDetails warehousingDetails = item.getWarehousingDetails();
                            warehousingDetails.setAmount(warehousingDetails.getAmount() + item.getAmount());
                            warehousingDetailDAO.updateWarehousingDetail(warehousingDetails);
                            soLuongNhap = soLuongNhap - item.getAmount();
                        }
                    }

                    this.khoiTaoBangTonKho();
                } else {
                    List<Delivery_Warehousing> delivery_warehousingList =
                            deliveryDetailWarehousingDao.getListDeliveryWarehouse(order.getId());

                    System.out.println(delivery_warehousingList);

                    for (Delivery_Warehousing item : delivery_warehousingList) {
                        BillWarehousing billWarehousing = new BillWarehousing();
                        billWarehousing.setError(true);
                        billWarehousing.setWarehouse(item.getWarehousingDetails().getBillWarehousing().getWarehouse());
                        billWarehousing.setDate(Date.valueOf(LocalDate.now()));
                        if (item.getAmount() >= soLuongNhap) {
                            WarehousingDetails warehousingDetailsItem = item.getWarehousingDetails();
                            WarehousingDetails warehousingDetails = new WarehousingDetails();
                            warehousingDetails.setPrice(warehousingDetailsItem.getPrice());
                            warehousingDetails.setBillWarehousing(billWarehousing);
                            warehousingDetails.setProduct(warehousingDetailsItem.getProduct());
                            warehousingDetails.setAmount(soLuongNhap);
                            soLuongNhap = Long.valueOf(0);
                            billWarehousing.getWarehousingDetailsSet().add(warehousingDetails);
                            billWarehousingDAO.saveBillWarehousing(billWarehousing);
                            break;
                        } else {
                            WarehousingDetails warehousingDetailsItem = item.getWarehousingDetails();
                            WarehousingDetails warehousingDetails = new WarehousingDetails();
                            warehousingDetails.setPrice(warehousingDetailsItem.getPrice());
                            warehousingDetails.setBillWarehousing(billWarehousing);
                            warehousingDetails.setProduct(warehousingDetailsItem.getProduct());
                            warehousingDetails.setAmount(item.getAmount());
                            billWarehousing.getWarehousingDetailsSet().add(warehousingDetails);
                            billWarehousingDAO.saveBillWarehousing(billWarehousing);
                            soLuongNhap = soLuongNhap - item.getAmount();
                        }
                    }
                }
            }
        }

    private Long tinhSoLuongTra() {

            Long tongLuongTra = Long.valueOf(0);

            List<RecieptsProduct> recieptsProductList = ReceiptProductDao.getAllOfOrder(order);
            if (recieptsProductList == null)
                return Long.valueOf(0);
            else{
                for (RecieptsProduct item : recieptsProductList){
                    tongLuongTra = tongLuongTra + item.getAmout();
                }
            }
            return tongLuongTra;
    }

    private Long getGiaBanTaiThoiDiemXuatHD(Order order) {

            Long giaBan = Long.valueOf(0);
            Date dateOrder = order.getDetailOrder().getDate();
            List<PriceOld> priceOldList = priceOldDAO.getAllPriceOldProduct(order.getProduct().getId());

            System.out.println(priceOldList);
            //nếu ds gia cũ khác null hoặc ngày xuất hóa đơn sau ngày update cuối cùng
            if(priceOldList != null || dateOrder.after(priceOldList.get(0).getDate())) {
                for (PriceOld item : priceOldList) {
                    if(dateOrder.before(item.getDate())) {
                        System.out.println("giá bán trước ngày :" + item.getDate());
                        giaBan = item.getPriceImport();
                    }
                }
            }
            else{
                giaBan = order.getProduct().getPrice();
            }
            return giaBan;
    }

    public void setOrder(Order order){
        System.out.println(order);
        this.order = order;
        id_TieuDe.setText("Mã HĐ:" + order.getDetailOrder().getId()+
                ", Sản Phẩm: "+order.getProduct().getName());
        System.out.println("set Order");
        this.khoiTaoBangTonKho();
        System.out.println("giá bán tại thời ddiemr xuất hđ: " + getGiaBanTaiThoiDiemXuatHD(order));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_SoLuongCongLai.setCellValueFactory(new PropertyValueFactory<WarehousingDetails, Long>("amount"));
        col_Ma.setCellValueFactory(new PropertyValueFactory<WarehousingDetails, Long>("id"));

        id_HAngDu.setToggleGroup(toggleGroup);
        id_HangLoi.setToggleGroup(toggleGroup);
        id_HAngDu.setSelected(true);

    }

    private void khoiTaoBangTonKho(){
        System.out.println("id order:" + order.getId());
        List<WarehousingDetails> warehousingDetailsList =  warehousingDetailDAO.getListWarehouseDetail(order.getId());
        ObservableList<WarehousingDetails> orderObservableList = FXCollections.observableArrayList(warehousingDetailsList);
        tableViewPhieuXuatKho.setItems(orderObservableList);
    }
}

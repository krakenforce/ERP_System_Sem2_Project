package Controller;

import App.App;
import Boxes.AlertBox;
import Boxes.ConfirmBox;
import Controller.SalesManageModule.CreateOrderController;
import NodeService.TabPaneService;
import NodeService.TabService;
import Services.Hibernate.DAO.UserTypeDAO;
import Services.Hibernate.entity.LoginInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    private App app;
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private MenuItem mnitAbout;

    @FXML
    private TabPane tpMain;

    TabPaneService tabPaneService = new TabPaneService();
    TabService saleManageTab = new TabService("Sale Manage");
    TabService customerStatisticTab = new TabService("Customer Statistic");
    TabService paymentManageTab = new TabService("Payment Manage");
    TabService saleStatisticTab = new TabService("Sale Statistic");
    TabService productGroupStatisticTab = new TabService("Product Group Statistic");
    TabService productSaleStatisticsTab = new TabService("Product Sale Statistic");
    TabService tradeDiscountStatisticTab = new TabService("Trade Discount Statistic");
    TabService customerTradeDiscountStatTab = new TabService("Customer Trade Discount Statistic");
    TabService customerManageTab = new TabService("Customer Manage");
    TabService tradeDiscountManageTab = new TabService("Trade Discount Manage");
    TabService receiptManageTab = new TabService("Receipt Manage");
    TabService welcomeTab = new TabService("Welcome");

    private TabPaneService tabpaneservice;
    private TabService tab_ManagerSales;
    private TabService tab_QuanLyKho;
    private TabService tab_QuanLySanPham;
    private TabService tab_QuanLyNhomSanPham;
    private TabService tab_QuanLyThongTinGiamGia;
    private TabService tab_QuanLyDonVi;

    public DashBoardController() {

        tabpaneservice = new TabPaneService();

        tab_ManagerSales = new TabService("Quản Lý Nhân Viên");
        tab_QuanLyKho = new TabService("Warehouse Manage");
        tab_QuanLySanPham = new TabService("Product Manage");
        tab_QuanLyNhomSanPham = new TabService("Product Group Manage");
        tab_QuanLyThongTinGiamGia = new TabService("Discount Manage");
        tab_QuanLyDonVi = new TabService("Unit Manage");
    }

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblID;

    @FXML
    private Label lblRole;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tabPaneService.addTab(tpMain,welcomeTab,"/Form/MainForm/welcome.fxml");



        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(App app){
        UserTypeDAO userTypeDAO = new UserTypeDAO();
        LoginInfo user = new LoginInfo();
        user = app.getLoggedUser();
        lblUsername.setText(user.getUsername());
        lblID.setText(Integer.toString(user.getId()));
        lblRole.setText(userTypeDAO.findByID(user.getUserType().getId()).getTypeName());
    }


    public void openSaleManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,saleManageTab,"/Form/SalesmanModule/SalesManList.fxml");
    }

    public void openStatistic(ActionEvent actionEvent) {
    }

    public void openPayment(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,paymentManageTab,"/Form/SalesmanModule/PaymentManage.fxml");
    }


    public void openAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource("/Form/About.fxml"));
        Parent root = loader.load();



        Stage stage = new Stage();
        stage = (Stage) root.getScene().getWindow();
        stage.show();
    }

    public void openCustomerStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,customerStatisticTab,"/Form/StatisticModule/CustomerListStatistic.fxml");
    }

    public void openSaleStatistic(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,saleStatisticTab,"/Form/StatisticModule/SalesStatistic.fxml");
    }

    public void openProductGroupStatistic(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,productGroupStatisticTab,"/Form/StatisticModule/GroupProductStatistic.fxml");
    }

    public void openProductSaleStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,productSaleStatisticsTab,"/Form/StatisticModule/ProductSoldStatistic.fxml");
    }

    @FXML
    void openTradeDiscountStatis(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,tradeDiscountStatisticTab,"/Form/StatisticModule/TradeDiscountStatistic.fxml");
    }

    public void openCustomerTradeDiscountStatis(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain,customerTradeDiscountStatTab,"/Form/StatisticModule/CustomerTradeStatistic.fxml");
    }

    public void exitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                boolean answer = ConfirmBox.display("Closing Application", "Do you want to quit?");
                if (answer) {
                    stage.close();
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    public void openCustomersManage(ActionEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain, customerManageTab, "/Form/CustomerManageModule/CustomerList.fxml");
        //createTab("Customer Management", "Form/Module2/Module2_Main.fxml");
    }

    public void openTradeDiscountManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain, tradeDiscountManageTab, "/Form/SalesmanModule/TradeDiscountList.fxml");
    }

    public void logOut(ActionEvent actionEvent) {
        AlertBox alertBox = new AlertBox();
        if(alertBox.InsertAlert("Sign out", "Do you really want to sign out")){
            app.logOut();
        }
    }

    public void openCreateOrder(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/CreateOrder.fxml"));
        CreateOrderController controller = loader.getController();

    }

    public void openCustomerList(ActionEvent actionEvent) {
    }

    public void openCommission(ActionEvent actionEvent) {
    }

    public void openGroupProduct(ActionEvent actionEvent) {
    }

    public void openReceipt(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabPaneService.addTab(tpMain, receiptManageTab , "/Form/SalesmanModule/ReceiptByType.fxml");
    }

    public void openWarehouseList(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabpaneservice.addTab(tpMain,tab_QuanLyKho, "/Form/WarehouseModule/TabpaneWarehouseMain.fxml");
    }

    public void openUnitManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabpaneservice.addTab(tpMain,tab_QuanLyDonVi, "/Form/WarehouseModule/UnitManager/TabpaneUnitMain.fxml");

    }

    public void openProductGroupManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabpaneservice.addTab(tpMain,tab_QuanLyNhomSanPham, "/Form/WarehouseModule/GroupProductManager/TabpaneGroupProductMain.fxml");

    }


    public void openProductManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabpaneservice.addTab(tpMain,tab_QuanLySanPham, "/Form/WarehouseModule/ProductManager/TabpaneProductMain.fxml");

    }

    public void openDiscountProductManage(ActionEvent actionEvent) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        tabpaneservice.addTab(tpMain,tab_QuanLyThongTinGiamGia, "/Form/WarehouseModule/DiscountManager/TabpaneDiscountMain.fxml");

    }
}

package Controller.WarehouseController.DiscountController;

import NodeService.PaginationService;
import Services.Hibernate.DAO.DiscountDAO;
import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.EntityCombination.DetailOrderCustomer;
import Services.Hibernate.entity.Discount;
import Services.Hibernate.entity.Product;
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
import java.util.stream.Collectors;

public class addDiscountController implements Initializable {

    @FXML
    private ComboBox<String> cbProductName;

    @FXML
    private Spinner<Integer> spAmount;

    @FXML
    private TextField tfMoneyReduction;

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private Pagination pgDiscountList;
    private PaginationService<Discount> paginationService = new PaginationService<Discount>();

    TableView<Discount> tbDiscountList;
    TableColumn<Discount, Long> clID;
    TableColumn<Discount, Long> clAmount;
    TableColumn<Discount, Date> clStartDay;
    TableColumn<Discount, Date> clEndDay;
    TableColumn<Discount, Long> clMoneyReduction;
    TableColumn<Discount, Boolean> clStatus;
    TableColumn<Discount, String> clProductName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerInit();
        setDataToCB();
        setUpTableView();
        setUpPagination(getAllDiscountInfo());
    }

    public void setUpPagination(ObservableList<Discount> observableList){
        paginationService.setPagination(pgDiscountList);
        paginationService.setTableView(tbDiscountList);
        paginationService.setSopt(10);
        List<Discount> list = observableList.stream().collect(Collectors.toList());
        paginationService.createPagination(list);
    }

    public void setUpTableView(){
        tbDiscountList = new TableView<Discount>();
        clID = new TableColumn<Discount, Long>("ID");
        clAmount = new TableColumn<Discount, Long>("Amount");
        clStartDay = new TableColumn<Discount, Date>("Start Day");
        clEndDay = new TableColumn<Discount, Date>("End Day");
        clMoneyReduction = new TableColumn<Discount, Long>("Money Reduction");
        clStatus = new TableColumn<Discount, Boolean>("Status");
        clProductName = new TableColumn<Discount, String>("Product Name");

        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clMoneyReduction.setCellValueFactory(new PropertyValueFactory<>("moneyReduction"));
        clStartDay.setCellValueFactory(new PropertyValueFactory<>("dateStarts"));
        clEndDay.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        clStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));



        tbDiscountList.getColumns().addAll(clID, clAmount, clMoneyReduction, clStartDay, clEndDay, clStatus, clProductName);
    }

    @FXML
    void addDiscount(ActionEvent event) {
        ProductDAO productDAO = new ProductDAO();
        DiscountDAO discountDAO = new DiscountDAO();
        Discount discount = new Discount();
        discount.setAmount(getSpinnerValue());
        discount.setMoneyReduction(Long.parseLong(tfMoneyReduction.getText()));
        discount.setDateStarts(getDay(dpStartDay));
        discount.setDateEnd(getDay(dpEndDay));
        discount.setStatus(true);
        discount.setProduct(productDAO.findByName(cbProductName.getSelectionModel().getSelectedItem()));

        discountDAO.saveDiscount(discount);
        setUpPagination(getAllDiscountInfo());
    }

    public ObservableList<Discount> getAllDiscountInfo(){
        DiscountDAO discountDAO = new DiscountDAO();
        List<Discount> discountList = discountDAO.getAll();
        ObservableList<Discount> discountObservableList = FXCollections.observableArrayList();
        for(Discount discount : discountList){
            Discount object = new Discount();
            object.setId(discount.getId());
            object.setAmount(discount.getAmount());
            object.setDateEnd(discount.getDateEnd());
            object.setDateStarts(discount.getDateStarts());
            object.setMoneyReduction(discount.getMoneyReduction());
            object.setStatus(discount.getStatus());
            object.setProductName(discount.getProduct().getName());

            discountObservableList.add(object);
        }
        return discountObservableList;
    }

    public void spinnerInit(){
        spAmount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99)
        );
    }

    public Long getSpinnerValue(){
        return (long)spAmount.getValue();
    }

    public void setDataToCB(){
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getAll();

        ObservableList<String> productObservableList = FXCollections.observableArrayList();
        for(Product product : productList){
            productObservableList.add(product.getName());
        }
        cbProductName.setItems(productObservableList);
    }

    public Date getDay(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }


}

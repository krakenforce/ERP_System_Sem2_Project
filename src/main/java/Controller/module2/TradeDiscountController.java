package Controller.module2;

import Boxes.AddCustomerToDiscount;
import Boxes.AddDiscount;
import Boxes.ConfirmBox;
import Services.Hibernate.DAO.TradeDiscountDAO;
import Services.Hibernate.entity.TradeDiscounts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeDiscountController implements Initializable {

    TradeDiscountDAO ti = new TradeDiscountDAO();
    public TextField searchField;
    public Button searchBtn;
    public Label promptText;
    public Pagination pag;
    public Button addBtn;
    public TableView<TradeDiscountCols> discountsTabl = createTable();
    public List<TradeDiscountCols> discountsData = createData("");


    public static int rowsPerPage = 5;

    private TableView<TradeDiscountCols> createTable() {
        TableView<TradeDiscountCols> tabl = new TableView<>();

        TableColumn<TradeDiscountCols, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<TradeDiscountCols, String> limitCol = new TableColumn<>("Limit");
        limitCol.setCellValueFactory(new PropertyValueFactory<>("limit"));
        limitCol.setPrefWidth(100);

        TableColumn<TradeDiscountCols, String> percentCol = new TableColumn<>("Discount");
        percentCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        percentCol.setPrefWidth(150);

        TableColumn<TradeDiscountCols, String> startCol = new TableColumn<>("Start Date");
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        startCol.setPrefWidth(110);

        TableColumn<TradeDiscountCols, String> endCol = new TableColumn<>("End Date");
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        endCol.setPrefWidth(110);

        // context menu:
        MenuItem deleteMenu = new MenuItem("delete trade discount");
        deleteMenu.setOnAction(e -> {
            try {
                if (ConfirmBox.display("Deleting Trade Discount", "Are you sure?")) {
                    TradeDiscountCols d = discountsTabl.getSelectionModel().getSelectedItem();
                    discountsTabl.getItems().remove(d);

                    // delete on database:
                    ti.deleteTradeDiscount(ti.findById(d.getId()));

                    // reset page count:
                    discountsData.remove(d);
                    int curPage = pag.getCurrentPageIndex();
                    pag.setPageCount((int) Math.ceil((double) discountsData.size()/(double)rowsPerPage));
                    pag.setCurrentPageIndex(Math.min(curPage, pag.getPageCount()));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        MenuItem addToDMenu = new MenuItem("set customers");
        addToDMenu.setOnAction(e -> {
            AddCustomerToDiscount box = new AddCustomerToDiscount();

            TradeDiscountCols d = discountsTabl.getSelectionModel().getSelectedItem();
            box.setUp(this, d);
            try {
                box.display();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteMenu, addToDMenu);


        // put things together:
        tabl.getColumns().addAll(nameCol, limitCol, startCol, endCol, percentCol);
        tabl.setContextMenu(contextMenu);
        return tabl;
    }

    public List<TradeDiscountCols> createData(String search) {
        List<TradeDiscounts> tradiscounts = search.isBlank() ? ti.getAll() : ti.findByName(search);
        List<TradeDiscountCols> tradeDiscountCols = new ArrayList<>();
        for (TradeDiscounts c : tradiscounts) {
            TradeDiscountCols col = new TradeDiscountCols();
            col.setId(c.getId());
            col.setName(c.getName());
            col.setLimit(c.getLimitMoney());
            col.setStart(c.getDateStars());
            col.setEnd(c.getDateEnd());
            col.setPercentage(c.getDiscountPercentage());

            tradeDiscountCols.add(col) ;
        }

        return tradeDiscountCols;
    }

       @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pag.setPageFactory(this::createPage);
        pag.setPageCount((int) Math.ceil((double) discountsData.size()/(double)rowsPerPage));
    }

    private Node createPage(int pageIndex) {
       int fromIndex = rowsPerPage * pageIndex;
       int toIndex = Math.min( fromIndex + rowsPerPage, discountsData.size());
        discountsTabl.setItems(FXCollections.observableList(discountsData.subList(fromIndex, toIndex)));
       return discountsTabl;
    }

    public void searchByName(ActionEvent event) {
        discountsData = createData(searchField.getText());

        pag.setPageFactory(this::createPage);
        pag.setPageCount((int) Math.ceil((double) discountsData.size()/(double)rowsPerPage));
        pag.setCurrentPageIndex(0);


    }

    public void addDiscount(ActionEvent event) throws IOException {
        AddDiscount ad = new AddDiscount();
        ad.display();
        ad.setUp(this);
    }
}

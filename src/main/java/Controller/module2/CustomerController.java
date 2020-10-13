package Controller.module2;

import Boxes.ConfirmBox;
import Boxes.MakePayReceipt;
import Services.Hibernate.DAO.CustomerDaoImpl;
import Services.Hibernate.entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    CustomerDaoImpl ci = new CustomerDaoImpl();
    public TextField searchField;
    public Button searchBtn;
    public Label promptText;
    public Pagination pag;
    public TableView<CustomerCols> customerTabl = createTable();
    public List<CustomerCols> customerData = createData("");
    public static int rowsPerPage = 5;

    private TableView<CustomerCols> createTable() {
        TableView<CustomerCols> tabl = new TableView<>();

        TableColumn<CustomerCols, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<CustomerCols, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setPrefWidth(150);

        TableColumn<CustomerCols, Long> debtCol = new TableColumn<>("Debt");
        debtCol.setCellValueFactory(new PropertyValueFactory<>("debt"));
        debtCol.setPrefWidth(100);

        TableColumn<CustomerCols, Long> spentCol = new TableColumn<>("Total Spent");
        spentCol.setCellValueFactory(new PropertyValueFactory<>("spent"));
        spentCol.setPrefWidth(100);

        // context menu:
        MenuItem deleteMenu = new MenuItem("delete customer");
        deleteMenu.setOnAction(e -> {
            try {
                if (ConfirmBox.display("Deleting Customer", "Are you sure?")) {
                    CustomerCols d = customerTabl.getSelectionModel().getSelectedItem();
                    customerTabl.getItems().remove(d);

                    // delete on database:
                    ci.deleteCustomer(ci.findByID(d.getId()));

                    // reset page count:
                    customerData.remove(d);
                    int curPage = pag.getCurrentPageIndex();
                    pag.setPageCount((int) Math.ceil((double) customerData.size() / (double) rowsPerPage));
                    pag.setCurrentPageIndex(Math.min(curPage, pag.getPageCount()));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        MenuItem receiptMenu = new MenuItem("make a paid receipt");
        receiptMenu.setOnAction(e -> {
            MakePayReceipt b = new MakePayReceipt();
            CustomerCols c = customerTabl.getSelectionModel().getSelectedItem();
            b.setUp(this, c);
            try {
                b.display();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteMenu, receiptMenu);
        contextMenu.setOnShowing(e -> {
            CustomerCols c = customerTabl.getSelectionModel().getSelectedItem();
            Long debt = c.getDebt();
            if (debt == null || debt.equals(0L)) {
                receiptMenu.setDisable(true);
            } else {
                receiptMenu.setDisable(false);
            }

        });


        // put things together:
        tabl.getColumns().addAll(nameCol, phoneCol, debtCol, spentCol);
        tabl.setContextMenu(contextMenu);

        return tabl;
    }


    private List<CustomerCols> createData(String search) {
        ObservableList<Customer> customers = FXCollections.observableList(search.isBlank() ? ci.getAllCustomers() : ci.findByName(search));
        List<CustomerCols> customerCols = new ArrayList<>();
        for (Customer c : customers) {
            CustomerCols col = new CustomerCols();
            col.setId(c.getId());
            col.setName(c.getName());
            col.setPhone(c.getPhone());

            col.setDebt(c.calculateDebt());
            col.setSpent(c.totalSpent());
            customerCols.add(col);
        }

        return customerCols;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pag.setPageFactory(this::createPage);
        pag.setPageCount((int) Math.ceil((double) customerData.size() / (double) rowsPerPage));
    }

    private Node createPage(int pageIndex) {
        int fromIndex = rowsPerPage * pageIndex;
        int toIndex = Math.min(fromIndex + rowsPerPage, customerData.size());
        customerTabl.setItems(FXCollections.observableList(customerData.subList(fromIndex, toIndex)));
        return customerTabl;
    }

    public void searchByName(ActionEvent event) {
        customerData = createData(searchField.getText());

        pag.setPageFactory(this::createPage);
        pag.setPageCount((int) Math.ceil((double) customerData.size() / (double) rowsPerPage));
        pag.setCurrentPageIndex(0);


    }



}

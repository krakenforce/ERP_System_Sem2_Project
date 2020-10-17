package NodeService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import java.util.List;


public class PaginationService<S> {

    private Pagination pagination;
    private int sopt;
    private TableView<S> tableView;

    public PaginationService(){

    }

    public PaginationService(Pagination pagination, int sopt, TableView<S> tableView) {
        this.pagination = pagination;
        this.sopt = sopt;
        this.tableView = tableView;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public int getSopt() {
        return sopt;
    }

    public void setSopt(int sopt) {
        this.sopt = sopt;
    }

    public TableView<S> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<S> tableView) {
        this.tableView = tableView;
    }

    public void createPagination(List<S> listItem) {

        pagination.setPageCount(listItem.size()/sopt + 1);

        pagination.setPageFactory(pageIndeX -> {

            ObservableList<S> list = FXCollections.observableArrayList(listItem);

            int fromIndex = pageIndeX * sopt;
            int toIndex = Math.min(fromIndex + sopt, list.size());
            tableView.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

            return tableView;

        });
    }
}


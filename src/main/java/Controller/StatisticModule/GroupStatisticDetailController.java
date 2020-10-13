package Controller.StatisticModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.time.LocalDate;

public class GroupStatisticDetailController {

    @FXML
    private DatePicker dpStartDay;

    @FXML
    private DatePicker dpEndDay;

    @FXML
    private TableView<?> tbGroupProductList;

    @FXML
    private TableColumn<?, ?> clProductID;

    @FXML
    private TableColumn<?, ?> clProductName;

    @FXML
    private TableColumn<?, ?> clTotalSold;

    @FXML
    private BarChart<String, Number> bcProductSold;

    public void searchByDay(ActionEvent actionEvent) {
    }

    public void showAll(ActionEvent actionEvent) {
    }

    public Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        return Date.valueOf(localDate);
    }

    public void initLineChart(String productName, Long totalSold){
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.getData().addAll(new XYChart.Data<String, Number>(productName,totalSold));

        bcProductSold.getData().addAll(series);
    }
}

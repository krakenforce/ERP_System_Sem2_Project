package NodeService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

public class ComboBoxService<T> {

    private ComboBox<T> comboBox;

    public ComboBoxService(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
    }

    public ComboBoxService() {

    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
    }

    public void taiDuLieu(List<T> listItem){

        ObservableList<T> listNoiDen = FXCollections.observableArrayList(listItem);

        comboBox.setItems(listNoiDen);

        comboBox.setCellFactory(new Callback<ListView<T>, ListCell<T>>(){

            @Override
            public ListCell<T> call(ListView<T> p) {

                final ListCell<T> cell = new ListCell<T>(){

                    @Override
                    protected void updateItem(T t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t.toString());
                        }else{
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });

        comboBox.getSelectionModel().selectFirst();
    }
}

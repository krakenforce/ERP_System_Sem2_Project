package Boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertBox {
    public static void display(String s, String title) {
        Stage win = new Stage();
        win.initModality(Modality.APPLICATION_MODAL);

        Label prompt = new Label(s);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.getStyleClass().add("prompt");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(prompt);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);



        BorderPane border = new BorderPane();
        border.setCenter(vbox);
        border.getStyleClass().add("prompt-window");

        Scene scene = new Scene(border);
        scene.getStylesheets().add("Main/css/style.css");

        win.setScene(scene);
        win.setHeight(250);
        win.setWidth(300);
        win.setTitle(title);

        win.showAndWait();
    }

    public boolean InsertAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result =  alert.showAndWait();
        if(result.get() == ButtonType.OK){
            return true;
        }else{
            return false;
        }
    }

    public void warningAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void confirmAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void nullSelectedSalesman(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText("You still have not selected a seller, please choose from the table below");
        alert.showAndWait();
    }
}

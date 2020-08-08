package Controller;

import Utils.BarcodeOperation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DemoFormController {
    @FXML
    private TextField tfBarcodeText;
    @FXML
    private ImageView imgvBarcode;

    public void generateBarcode(ActionEvent actionEvent) {
        String text = tfBarcodeText.getText();
        BarcodeOperation barcodeOperation = new BarcodeOperation();
        BufferedImage bufferedImage = barcodeOperation.generateEAN13BarcodeImage(text);
    }
}

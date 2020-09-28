package TestController;

import Utils.BarcodeOperation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateBarcodeTestController implements Initializable {

    @FXML
    private TextField tfBarcodeText;

    @FXML
    private ImageView imgvBarcodeShow;


    @FXML
    void generateBarcode(ActionEvent event) {
        BarcodeOperation barcodeOperation = new BarcodeOperation();
        BufferedImage barcodeBuffImage = barcodeOperation.generateEAN13BarcodeImage(tfBarcodeText.getText());
        Image barcodeImage = convertToFxImage(barcodeBuffImage);

        imgvBarcodeShow.setImage(barcodeImage);


    }
    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

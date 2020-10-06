package TestController;

import Utils.BarcodeOperation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class ProductInfoController {
    @FXML
    private ImageView imgvBarcode;

    @FXML
    private TextField tfProductID;

    @FXML
    private TextField tfProductName;

    @FXML
    private TextField tfMinInventory;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfRateProfit;

    @FXML
    private TextField tfRetail;

    @FXML
    private TextField tfGroupProduct;

    @FXML
    private TextField tfBarcode;


    public void testSetField(Long id, String name, Long minInventory, Long price, Double ratePro, Long retail, String tfGroup, String barcode){
        tfProductID.setText(Long.toString(id));
        tfProductName.setText(name);
        tfMinInventory.setText(Long.toString(minInventory));
        tfPrice.setText(Long.toString(price));
        tfRateProfit.setText(Double.toString(ratePro));
        tfRetail.setText(Long.toString(retail));
        tfGroupProduct.setText(tfGroup);
        tfBarcode.setText(barcode);

        BarcodeOperation barcodeOperation = new BarcodeOperation();
        imgvBarcode.setImage(convertToFxImage(barcodeOperation.generateEAN13BarcodeImage(barcode)));

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
}

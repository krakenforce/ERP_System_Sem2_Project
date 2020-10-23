import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.entity.Product;
import Utils.BarcodeOperation;
import Utils.RandomNumberGeneration;
import com.github.sarxos.webcam.Webcam;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/WarehouseModule/ProductManager/editProduct.fxml"));
        Parent root = loader.load();


        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


    public static void updateProductBarcode(){
        ProductDAO dao = new ProductDAO();
        List<Product> productList = dao.getAll();
        for(Product items : productList){
            RandomNumberGeneration generator = new RandomNumberGeneration();
            items.setBarCode(generator.RandomNumberGeneration(12));
            dao.updateProduct(items);
        }
    }

    public static void saveImageToFile() throws IOException {
        ProductDAO dao = new ProductDAO();
        List<Product> productList = dao.getAll();
        String barcodeText = "";

        for(Product items : productList){
            barcodeText = items.getBarCode();
            BufferedImage bi = new BarcodeOperation().generateEAN13BarcodeImage(barcodeText);
            File file = new File("D:\\Barcode Image\\" + items.getName() + ".png");
            ImageIO.write(bi, "png", file);
        }
    }

    public static void testWebcam() throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("D:\\Barcode Image\\hello-world.png"));
    }
}

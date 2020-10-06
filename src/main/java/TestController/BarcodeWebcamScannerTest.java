package TestController;

import Services.Hibernate.DAO.ProductDAO;
import Services.Hibernate.entity.Product;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.LuminanceSource;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class BarcodeWebcamScannerTest extends JFrame implements Runnable, ThreadFactory {

    private static final long serialVersionUID = 6441489157408381878L;

    private Executor executor = Executors.newSingleThreadExecutor(this);

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private JTextArea textarea = new JTextArea();
    private static Group root = new Group();
    Scene scene = null;

    public BarcodeWebcamScannerTest(){
        super();

        setLayout(new FlowLayout());
        setTitle("Read QR / Bar Code With Webcam");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Dimension size = WebcamResolution.QVGA.getSize();

        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        textarea.setEditable(false);
        textarea.setPreferredSize(size);

        add(panel);
        add(textarea);

        pack();
        setVisible(true);


        executor.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {

                if ((image = webcam.getImage()) == null) {
                    continue;
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }

            if (result != null) {
                textarea.setText(result.getText());
               initAndShowGUI();
            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }

    private void initAndShowGUI() {

        // This method is invoked on the EDT thread
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setPreferredSize(new Dimension(600, 400));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(() -> initFX(fxPanel));
    }

    public void findProduct() {
        ProductDAO dao = new ProductDAO();
        String barcodeString = textarea.getText();
        StringBuffer sb = new StringBuffer(barcodeString);
        sb.deleteCharAt(sb.length() - 1);
        String newBarcodeString = sb.toString();
        Product product = dao.findByBarcode(newBarcodeString);

        Long id, minInven, price, retail;
        Double ratePro;
        String name, groupName, barcode;

        id = product.getId();
        minInven = product.getMinimumInventory();
        price = product.getPrice();
        ratePro = product.getRateProfit();
        retail = product.getRetailPrice();
        barcode = product.getBarCode();
        name = product.getName();
        groupName = product.getGroupProduct().getName();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/TestForm/ProductInfo.fxml"));
        Parent root = loader.getRoot();

        try {
            root = loader.load();
            ProductInfoController controller = loader.getController();
            controller.testSetField(id, name, minInven, price, ratePro, retail, groupName, barcode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Second Window");
        stage.show();

    }

    private void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene(){
        if(scene == null){
            scene = new Scene(root);
        }
        try{
            ProductDAO dao = new ProductDAO();
            String barcodeString = textarea.getText();
            StringBuffer sb = new StringBuffer(barcodeString);
            sb.deleteCharAt(sb.length() - 1);
            String newBarcodeString = sb.toString();
            Product product = dao.findByBarcode(newBarcodeString);

            Long id, minInven, price, retail;
            Double ratePro;
            String name, groupName, barcode;

            id = product.getId();
            minInven = product.getMinimumInventory();
            price = product.getPrice();
            ratePro = product.getRateProfit();
            retail = product.getRetailPrice();
            barcode = product.getBarCode();
            name = product.getName();
            groupName = product.getGroupProduct().getName();
            Node newLoaderPage = FXMLLoader.load(getClass().getResource("/Form/TestForm/ProductInfo.fxml"));
            ProductInfoController controller = (ProductInfoController) getController(newLoaderPage);
            controller.testSetField(id, name, minInven, price, ratePro, retail, groupName, barcode);
            root.getChildren().add(newLoaderPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public static Object getController(Node node) {
        Object controller = null;
        do {
            controller = node.getUserData();
            node = node.getParent();
        } while (controller == null && node != null);
        return controller;
    }



}

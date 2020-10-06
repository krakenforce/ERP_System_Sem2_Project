package TestController;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import javafx.application.Application;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.Text;

;
import java.net.URL;
import java.util.ResourceBundle;

public class BarcodeWebcamController extends Application implements Initializable {

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private Pane pane;
    private TextField textField;


    @Override
    public void start(Stage stage) throws Exception {
        Pane canvas = new Pane();
        canvas.setPrefSize(400,600);
        Label textField = new Label("hello");
        textField.setPrefSize(30, 100);
        canvas.getChildren().addAll(textField);
        stage.setScene(new Scene(canvas));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

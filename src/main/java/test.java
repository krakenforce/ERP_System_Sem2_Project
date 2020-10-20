import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Form/SalesmanModule/TradeDiscountList.fxml"));
        Parent root = loader.load();


        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}

import Services.Hibernate.DAO.UnitDAO;
import Services.Hibernate.entity.Product;
import Services.Hibernate.entity.Unit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Form/SalesmanModule/SalesManList.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)  {
        launch(args);
        //addUnit();
    }

    public static void addProduct(){
        Product product = new Product();
    }

    public static void addUnit(){
        Unit unit = new Unit();
        unit.setUnitPrimary("hộp");
        unit.setUnitExchange("cái");
        unit.setValuePrimary((long) 1);
        unit.setValueExchange((long) 2);


        UnitDAO unitDAO = new UnitDAO();
        unitDAO.saveUnit(unit);
    }
}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/Form/SalesmanModule/SalesManList.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)  {
       launch(args);
//        SalesManDAO salesManDAO = new SalesManDAO();
//        Salesman salesman = salesManDAO.findById((long) 1);
//
//        GroupProductDAO groupProductDAO = new GroupProductDAO();
//        GroupProduct groupProduct = groupProductDAO.findById((long) 1);
//
//        Salesman_GroupProduct salesmanGroup = new Salesman_GroupProduct();
//        salesmanGroup.setSalesman(salesman);
//        salesmanGroup.setGroupProduct(groupProduct);
//
//        Salesman_ProductGroupDAO group = new Salesman_ProductGroupDAO();
//        group.saveGroup(salesmanGroup);



    }
}

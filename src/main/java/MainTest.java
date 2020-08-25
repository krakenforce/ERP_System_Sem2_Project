import Services.Hibernate.DAO.GroupProductDAO;
import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.GroupProduct;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.entity.Salesman;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/Form/SalesmanModule/SalesManProductGroup.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)  {
       launch(args);
    }
}

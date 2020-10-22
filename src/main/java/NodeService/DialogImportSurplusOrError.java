package NodeService;

import Services.Hibernate.entity.Order;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DialogImportSurplusOrError {
    private Stage dialog ;

    public void showDialogImportSurplusOrError(Order order, String urlFXML) {

        this.dialog = new Stage();
        dialog.resizableProperty().setValue(false);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(urlFXML));



        try {
            VBox addView = (VBox) fxmlLoader.load();
            Method method = fxmlLoader.getController().getClass().getMethod("setOrder", Order.class);
            method.invoke(fxmlLoader.getController(), order);


            Scene scene = new Scene(addView,500,600);
            this.dialog.setScene(scene);
            this.dialog.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

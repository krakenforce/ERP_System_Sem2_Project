package NodeService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TabPaneService {
    public TabPaneService() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AnchorPane LoadTabAsAnchorPane(String tenfilefx){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(tenfilefx));

        Node root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = fxmlLoader.getController().getClass().getMethod("LamMoi",null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(fxmlLoader.getController(), null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        AnchorPane a = new AnchorPane();
        a.getChildren().add(root);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        return a;
    }
    public void addTab(TabPane tabpane, TabService tabservice, String tenfilefx) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        // TODO Auto-generated method stub
        Tab tab = tabservice.getTab();

        if(tabservice.isKt()) {
//					int vitri = tabpane.getTabs().size();
//					tabservice.setVitri(vitri);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(tenfilefx));

            Node root = fxmlLoader.load();

            Method method = fxmlLoader.getController().getClass().getMethod("LamMoi",null);
            method.invoke(fxmlLoader.getController(), null);
            AnchorPane a = new AnchorPane();
            a.getChildren().add(root);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            tab.setContent(a);

            //tabservice.getTab().setContent(root);

            tabpane.getTabs().add(tab);

            tabpane.getSelectionModel().select(tab);

            tabservice.setKt(false);
        }
        else {
            tabpane.getSelectionModel().select(tab);
        }
    }

}

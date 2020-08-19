package Controller;

import App.App;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    private App app;
    private Tab currentTab;
    private Tab previousTab;
    private Tab[] tabs;
    @FXML
    public Tab module1Tab;
    public Tab module2Tab;
    public Tab module3Tab;
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int tabAmount = 4;
        tabs = new Tab[tabAmount-1];
        tabs[0] = module1Tab;
        tabs[1] = module2Tab;
        tabs[2] = module3Tab;

        currentTab = module1Tab;
        System.out.println("runnning");
    }


    public void handleTabChanges(Event event) throws IOException {

        if (currentTab == null || currentTab.isSelected()) {
            return;
        }
        for (Tab t : tabs) {
            if (t.isSelected()) {
                previousTab = currentTab;

                currentTab = t;
                System.out.println(currentTab.getText());
                closeTab(previousTab);
                displayTab(currentTab);
                return;
            }
        }
    }

    private void displayTab(Tab tab) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        if (tab == module2Tab) {
            loader.setLocation(getClass().getResource("../Form/Module2/Module2_Main.fxml"));
            Node tabContent = loader.load();
            tab.setContent(tabContent);

        }

        Initializable controller = (Initializable) loader.getController();


    }
    private void closeTab(Tab tab) {

    }
}

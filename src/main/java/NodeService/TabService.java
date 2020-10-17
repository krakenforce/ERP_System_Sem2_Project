package NodeService;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class TabService {
    private Tab tab;
    //	private int vitri;
    private boolean kt;

    public TabService() {
        super();
        // TODO Auto-generated constructor stub
        kt = true;
//		vitri = 0;
    }



    public TabService(Tab tab, int vitri) {
        super();
        this.tab = tab;
//		this.vitri = vitri;
    }



    public TabService(String tentab) {
        super();
        // TODO Auto-generated constructor stub
        tab = new Tab(tentab);
        kt = true;
//		vitri = 0;

        tab.setOnCloseRequest(e ->{
            //System.out.println("close tab them xe");
            AnchorPane node = ((AnchorPane) tab.getContent());
            if(node != null) {
                node.getChildren().clear();
            }
            kt = true;
//			vitri = 0;
        });

    }

//	public int getVitri() {
//		return vitri;
//	}
//
//
//
//	public void setVitri(int vitri) {
//		this.vitri = vitri;
//	}



    public Tab getTab() {
        return tab;
    }
    public void setTab(Tab tab) {
        this.tab = tab;
    }



    public boolean isKt() {
        return kt;
    }



    public void setKt(boolean kt) {
        this.kt = kt;
    }

//	public void setKtvsViTri() {
//		this.kt = true;
//		vitri = 0;
//	}
}

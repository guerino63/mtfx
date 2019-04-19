/**
 * Lo stage è l' equivalente di una finestra,
 * il primarystage è quello che viene chiamato allo start di javafx, sta sotto
 * ininfluente.
 */
package it.ma.mototrainerp;

import com.sun.glass.ui.Screen;
import java.io.IOException;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author maria
 */
public abstract class StageManager {

    //Le scene coi relativi dati di cambio Stage in questa mappa.
    private static final EnumMap<EStage, DataStage> STAGES = new EnumMap<>(EStage.class);
    //Lo schermo. 0=monitor interno, 1=esterno
    private static Screen screen;
    //La scena(e le proprietà stage) attuale
    private static EStage stageAttuale;

    /**
     * initialize stage manager
     */
    StageManager init(){
        for (EStage estage : EStage.values()) {
            stageAttuale = estage;  //Lo so ridonda, ma alla fine conterr� l' ultimo.
            DataStage ds = new DataStage();
            ds.stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(estage.fxml));
            try {
                ds.root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(StageManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Dopo il .load() viene caricato il controller
            ds.controller = loader.getController();
            ds.scene = new Scene(ds.root);
            ds.scene.getStylesheets().add(estage.css);
            ds.stage.setTitle(estage.title);
            ds.stage.initStyle(estage.decorated);
            ds.stage.initModality(estage.modality);
            ds.stage.setX(estage.x < 0 ? getScreen().getX() : estage.x + getScreen().getX());
            ds.stage.setY(estage.y < 0 ? getScreen().getY() : estage.y + getScreen().getY());
            ds.stage.setWidth(estage.width < 0 ? getScreen().getWidth() : estage.width);
            ds.stage.setHeight(estage.height < 0 ? getScreen().getHeight() : estage.height);
//            if (estage.width == -1 && estage.height == -1) {
//                ds.stage.setFullScreenExitHint("");
//                ds.stage.setFullScreen(true);
//            }
            ds.stage.setScene(ds.scene);
            STAGES.put(estage, ds);
        }
        postInit();
        return this;
    }
    public StageManager(int numeroSchermo) {
        setScreen(numeroSchermo);
    }

    public static Stage getStageAttuale() {
        return STAGES.get(stageAttuale).stage;
    }

    public static EStage getEStageAttuale() {
        return stageAttuale;
    }

    public static Object getController(EStage es) {
        return STAGES.get(es).controller;
    }

    static Stage getStage(EStage es) {
        return STAGES.get(es).stage;
    }

    static Scene getScene(EStage es) {
        return STAGES.get(es).scene;
    }

    static void showStageButHide(EStage es, javafx.stage.Window stageToHide) {
        stageToHide.hide();
        showStage(es);
    }

    static void showStageButHideActual(EStage es) {
        STAGES.get(stageAttuale).stage.hide();
        showStage(es);
    }

    static void showStage(EStage es) {
//        STAGES.get(stageAttuale).stage.hide();
//        if(stageAttuale!=null)
//            STAGES.get(stageAttuale).stage.hide();

        STAGES.get(es).stage.toFront();
        STAGES.get(es).stage.show();
        stageAttuale = es;
    }
    
    private static Screen getScreen() {
        return ((screen != null) ? screen : Screen.getScreens().get(0));
    }

    /**
     * @param nMonitor monitor. (0=internal)
     */
    private static void setScreen(int nMonitor) {
        //Sbatto lo stage nel monitor X, se non c'è lo rimetto nel primary
        try {
            screen = Screen.getScreens().get(nMonitor);
        } catch (IndexOutOfBoundsException ex) {
            screen = Screen.getScreens().get(0);
        }
    }

    /**
     * L' importanza di chiamarsi postInit();
     * dopo aver ciclato e caricato tutti gli Stages in questa funzione
     * possono essere richiamati tutti i BIND o i vari incroci di variabili usate dai controllers
     * (in quanto già tutti inizializzati), senza tema di avere dei riferimenti nulli.
     */
    abstract public void postInit();
}

class DataStage {

    Parent root;
    Scene scene;
    Stage stage;
    Object controller;

    public DataStage() {
    }
}

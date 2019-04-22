/*
  Lo stage e' l' equivalente di una finestra,
  il primarystage e' quello che viene chiamato allo start di javafx, sta sotto
  ininfluente.
 */
package it.ma.mototrainerp;

import java.io.IOException;
import java.util.EnumMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author maria
 */
public abstract class StageManager {
    private final static Log LOGGER = LogFactory.getLog(StageManager.class);

    //Le scene coi relativi dati di cambio Stage in questa mappa.
    private static final EnumMap<EStage, DataStage> STAGES = new EnumMap<>(EStage.class);
    //Lo schermo. 0=monitor interno, 1=esterno
    private static Screen screen;
    private static int screenNumber;
    //La scena(e le proprietà stage) attuale
    private static EStage stageAttuale;

    
    public StageManager(int numeroSchermo) {
        screenNumber = numeroSchermo;
    }
    /**
     * initialize stage manager
     */
    StageManager init(){
        setScreen(screenNumber);
        for (EStage estage : EStage.values()) {
            stageAttuale = estage;  //Lo so ridonda, ma alla fine conterra' l' ultimo.
            DataStage ds = new DataStage();
            ds.stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(estage.fxml));
            try {
                ds.root = loader.load();
            } catch (IOException ex) {
                LOGGER.error("", ex);
            }
            //Dopo il .load() viene caricato il controller
            ds.controller = loader.getController();
            ds.scene = new Scene(ds.root);
            ds.scene.getStylesheets().add(estage.css);
            ds.stage.setTitle(estage.title);
            ds.stage.initStyle(estage.decorated);
            ds.stage.initModality(estage.modality);
            ds.stage.setX(estage.x < 0 ? getScreen().getBounds().getMinX() : estage.x + getScreen().getBounds().getMinX());
            ds.stage.setY(estage.y < 0 ? getScreen().getBounds().getMinY() : estage.y + getScreen().getBounds().getMinY());
            ds.stage.setWidth(estage.width < 0 ? getScreen().getBounds().getWidth() : estage.width);
            ds.stage.setHeight(estage.height < 0 ? getScreen().getBounds().getHeight() : estage.height);
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

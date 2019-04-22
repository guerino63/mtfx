package it.ma.mototrainerp;

import static it.ma.mototrainerp.EStage.SETUP;
import static it.ma.mototrainerp.EStage.VIDEO;

import javafx.application.Application;
import javafx.stage.Stage;

public class Mototrainer extends Application {

    @Override
    public void start(Stage stage) {
        Prop.getInstance().oneShotLoadProperties(); //*** LASCIARE in testa!!!
        //Platform.isFxApplicationThread() is true
        /**
         * Caching...
         */
        //This is never used, but need to be done at the beginning so can start the
        //static block of class.
        //    private static final Rs232 RS232 = new Rs232();
        StageManager stageManager = new StageManager(1) {
            @Override
            public void postInit() {
                /**
                 * After loaded all Stages, we can cross binds stages
                 * Platform.isFxApplicati onThread() is true
                 */
                ((FXMLVideoController) StageManager.getController(VIDEO)).postInitialize();
                ((FXMLSetupController) StageManager.getController(SETUP)).postInitialize();
//                ((FXMLClipsController)StageManager.getController(CLIP)).postInitialize();
            }
        };
        stageManager.init();
        StageManager.showStage(SETUP);

//        Utility.msgDebug(RS232.open()? "RS232 Opened!":"RS232 Error:Not opened!");
//        StageManager.showStage(SETUP);
//        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (Event event) -> {
//            //***Attento! un throw od un consume event, e si rischia di non uscire più...
//            Utility.msgDebug("WindowEvent.WINDOW_CLOSE_REQUEST-->> ...exiting");
//        });
//
//        Utility.msgDebug("*IP : "+Host.getInstance().getIpAdress()+", MACADDRESS : "
//        +Host.getInstance().getMacAddress());
    }

    @Override
    public void stop() throws Exception {
        Prop.saveProperties();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

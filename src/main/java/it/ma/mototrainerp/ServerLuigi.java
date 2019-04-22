package it.ma.mototrainerp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author maria
 */
public class ServerLuigi extends ScheduledService<Void> {
    private final static Log LOGGER = LogFactory.getLog(ServerLuigi.class);

    SimpleStringProperty statusProperty = new SimpleStringProperty();
    SimpleBooleanProperty connectedProperty = new SimpleBooleanProperty();

    //viene chiamata se faccio reset(). Vedi failed()
    //Non sembra passarci se faccio start().
    //Quindi ne approfitto per far ripartire il servizio in caso di failed();
    @Override
    protected void ready() {
        //Platform.isFxApplicationThread()==true
        LOGGER.info("ServerLuigi ready():");
        super.ready();
//        start();
        //Platform.runLater(this::start); //Non serve il runLater...
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        LOGGER.info("ServerLuigi cancelled():Connecting cancelled.");
        statusProperty.set("Connecting cancelled.");
        connectedProperty.set(false);
    }

    public class TaskLuigi extends Task<Void> {

        @Override
        protected void running() {
            super.running();
            LOGGER.info("running():Connected.");
            statusProperty.set("Connected.");
            connectedProperty.set(true);
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            LOGGER.info("succeeded():Non contemplato.");
            statusProperty.set("Disconnected.");
            connectedProperty.set(false);
        }

        @Override
        protected void failed() {
            super.failed();
            LOGGER.info("failed():Connecting failed.");
            statusProperty.set("Connecting failed.");
            LOGGER.fatal(getException().getMessage());
            reset();
            connectedProperty.set(false);
        }

        @Override
        protected void cancelled() {
            super.cancelled();
        }

        @Override
        protected Void call() throws Exception {
            while (true) {
                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new TaskLuigi();
    }

    public ServerLuigi() {
        LOGGER.info("Create Server Luigi");
        this.setRestartOnFailure(true);
//        this.setDelay(Duration.seconds(3));
//        this.setPeriod(Duration.millis(100));
//        this.setMaximumFailureCount(5);
    }

//    public static void main(String[] args) {
//        JFXPanel jfx = new JFXPanel();  //Simply instance, otherwise doesn't load javaFX
//        ServerLuigi s = new ServerLuigi();
//        s.start();
//        //s.setOnRunning((ev) -> Utility.msgDebug("running():Connected." + ev.toString(), 3));
//        //s.start();
//    }
}

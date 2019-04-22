
package it.ma.mototrainerp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * FXML Controller class
 *
 * @author ma
 */
public class FXMLPostVideoController implements Initializable {

    final static Log LOGGER = LogFactory.getLog(FXMLPostVideoController.class);
    @FXML
    private Button butExit;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("initialize()");
        // TODO
    }    

    @FXML
    private void OnActionButExit(ActionEvent event) {
        StageManager.showStageButHide(EStage.SETUP, butExit.getScene().getWindow());
    }
    
}

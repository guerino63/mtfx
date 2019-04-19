
package it.ma.mototrainerp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ma
 */
public class FXMLPostVideoController implements Initializable {

    @FXML
    private Button butExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnActionButExit(ActionEvent event) {
        StageManager.showStageButHide(EStage.SETUP, butExit.getScene().getWindow());
    }
    
}

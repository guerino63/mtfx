package it.ma.mototrainerp;

import it.ma.tables.StringTableCell;
import it.ma.tables.Tolerances;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLAdminController implements Initializable {
    final static Log LOGGER = LogFactory.getLog(FXMLAdminController.class);
    @FXML
    private Button buttonClose;
    @FXML
    private PasswordField textConfirmPass;
    @FXML
    private PasswordField textNewPass;
    @FXML
    private PasswordField textPassword;
    @FXML
    private GridPane gridAdmin;
    @FXML
    private Label labelMatch;
    @FXML
    private Button buttonChangePassword;
    @FXML
    private Button buttonChangeBackground;
    @FXML
    private TableView<Tolerances> tableTolerances;

    @FXML
    private CheckBox selectEnableMasterTrack;

    private final static ObservableList<Tolerances> TABTOLERANCES = FXCollections.observableArrayList( //            new Tolerances("Front Brake", "20", "poroporo", "30", "", "15", "solo avviso", "5", ""),
            //            new Tolerances("Rear Brake", "20", "solo avviso", "30", "", "15", "solo avviso", "5", "solo avviso"),
            //            ...,
            //            new Tolerances("Time to game Over", "20", "solo avviso", "30", "", "15", "solo avviso", "5", "solo avviso")
            );
    @FXML
    private TableColumn<Tolerances, String> rowHeaders;
    @FXML
    private TableColumn<Tolerances, String> colBeginner;
    @FXML
    private TableColumn<Tolerances, String> colBeginnerAvviso;
    @FXML
    private TableColumn<Tolerances, String> colIntermediate;
    @FXML
    private TableColumn<Tolerances, String> colIntermediateAvviso;
    @FXML
    private TableColumn<Tolerances, String> colExpert;
    @FXML
    private TableColumn<Tolerances, String> colExpertAvviso;
    @FXML
    private TableColumn<Tolerances, String> colRider;
    @FXML
    private TableColumn<Tolerances, String> colRiderAvviso;
    @FXML
    private Button buttonSaveTolerancesData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("initialize()");
        gridAdmin.setVisible(false);
        tableTolerances.setEditable(true);

        tableTolerances.setItems(TABTOLERANCES);
//        tableTolerances.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

//      stessa funzionalita con private TableColumn<?, ?> rowHeader
//      senza dover descrivere classe e tipo 
//      rowHeaders.setCellValueFactory(new PropertyValueFactory<>("rowHeaders"));
        //Questa è meglio, soprattutto se si deve associare una combo o altro alla cella.
        rowHeaders.setCellValueFactory(cellData -> cellData.getValue().rowHeadersProperty());
        colBeginner.setCellValueFactory(cellData -> cellData.getValue().colBeginnerProperty());
//il commit non avverra più solo per uso tasto INVIO
//        colBeginner.setCellFactory(TextFieldTableCell.forTableColumn());
        colBeginner.setCellFactory(cellData -> {
            return new StringTableCell();
        });
        colBeginnerAvviso.setCellValueFactory(cellData -> cellData.getValue().colBeginnerAvvisoProperty());
        colBeginnerAvviso.setCellFactory(ComboBoxTableCell.forTableColumn("solo avviso", ""));
        colIntermediate.setCellValueFactory(cellData -> cellData.getValue().colIntermediateProperty());
        //colIntermediate.setCellFactory(TextFieldTableCell.forTableColumn());
        colIntermediate.setCellFactory(cellData -> new StringTableCell());
        colIntermediateAvviso.setCellValueFactory(cellData -> cellData.getValue().colIntermediateAvvisoProperty());
        colIntermediateAvviso.setCellFactory(ComboBoxTableCell.forTableColumn("solo avviso", ""));
        colExpert.setCellValueFactory(cellData -> cellData.getValue().colExpertProperty());
//        colExpert.setCellFactory(TextFieldTableCell.forTableColumn());
        colExpert.setCellFactory(cellData -> new StringTableCell());
        colExpertAvviso.setCellValueFactory(cellData -> cellData.getValue().colExpertAvvisoProperty());
        colExpertAvviso.setCellFactory(ComboBoxTableCell.forTableColumn("solo avviso", ""));
        colRider.setCellValueFactory(cellData -> cellData.getValue().colRiderProperty());
//        colRider.setCellFactory(TextFieldTableCell.forTableColumn());
        colRider.setCellFactory(cellData -> new StringTableCell());
        colRiderAvviso.setCellValueFactory(cellData -> cellData.getValue().colRiderAvvisoProperty());
        colRiderAvviso.setCellFactory(ComboBoxTableCell.forTableColumn("solo avviso", ""));

        try {
            Ut.importToleranceFromCSV(TABTOLERANCES, Prop.FILETOLERANCE.toString());
        } catch (Exception ex) {
            LOGGER.error("Errore import file tolleranze", ex);
        }
    }

    @FXML
    private void onButtonClose(ActionEvent event) {
//        buttonClose.getScene().getWindow().hide();
        StageManager.showStageButHide(EStage.SETUP, buttonClose.getScene().getWindow());       // windows need...
//        ((Stage) buttonClose.getScene().getWindow()).close();
//        SCENA.get(SceneManager.LeScene.SETUP).esponiLaScena();
    }

    @FXML
    private void OnKeyReleasedTextPassword(KeyEvent event) {
        String s = textPassword.getText();
        String sMatch = Prop.Desc.PASSWORD_ADMIN.getValue();
        if (s.equals(sMatch)) {
            //Passw match! rendi visibile la griglia 
            gridAdmin.setVisible(true);
            labelMatch.setText("");
            buttonChangePassword.setVisible(false);
        }
    }

    @FXML
    private void onKeyReleasedTextNewPass(KeyEvent event) {
        if (textNewPass.getText().length() == 0 && textConfirmPass.getText().length() == 0) {
            labelMatch.setText("");
            buttonChangePassword.setVisible(false);
            return;
        }
        if (textNewPass.getText().equals(textConfirmPass.getText())) {
            labelMatch.setText("Match!");
            buttonChangePassword.setVisible(true);
        } else {
            labelMatch.setText("Don't match!");
            buttonChangePassword.setVisible(false);
        }
    }

    @FXML
    private void onKeyReleasedTextConfirm(KeyEvent event) {
        if (textConfirmPass.getText().length() == 0 && textConfirmPass.getText().length() == 0) {
            labelMatch.setText("");
            buttonChangePassword.setVisible(false);
            return;
        }
        if (textNewPass.getText().equals(textConfirmPass.getText())) {
            labelMatch.setText("Match!");
            buttonChangePassword.setVisible(true);
        } else {
            labelMatch.setText("Don't match!");
            buttonChangePassword.setVisible(false);
        }
    }

    @FXML
    private void onActionbuttonChangePassword(ActionEvent event) {
        Prop.Desc.PASSWORD_ADMIN.setValue(textNewPass.getText());
        labelMatch.setText("New password saved!");
    }

    @FXML
    private void OnActionButtonChangeBackground(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Background image");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Background", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File f = fileChooser.showOpenDialog(StageManager.getStageAttuale());
        if (f == null) {
            return;
        }
        try {
            Path pathSrcImg = f.toPath();

            Path pathDestImg = Prop.IMGBACKGROUND;
            Files.copy(pathSrcImg, pathDestImg, StandardCopyOption.REPLACE_EXISTING);

            Ut.setSetupBackground();

//            SceneManager.getActualScene().getStylesheets().add("appCssFile.css");
//            SCENA.get(SceneManager.LeScene.SETUP).setStyleSheetFromJar();
        } catch (IOException ex) {
            LOGGER.error("Error setup background", ex);
        }
    }

    @FXML
    private void OnActionSelectEnableMasterTrack(ActionEvent event) {
    }

    @FXML
    private void onButtonSaveToleranceData(ActionEvent event) {
        Ut.exportToleranceToCSV(TABTOLERANCES, Prop.FILETOLERANCE.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(StageManager.getStageAttuale());
        alert.setTitle("Tolerances");
        alert.setHeaderText("Tolerances are updated!");
        alert.showAndWait();
        return;
    }

}

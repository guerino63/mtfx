package it.ma.mototrainerp;

import it.ma.tables.CRank;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLSetupController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(Prop.class.getName());

    @FXML
    private ListView<Label> listCircuit;
    @FXML
    private ListView<Label> listViewDifficult;
    @FXML
    private ListView<Label> labelViewGearBoxType;

    private ObservableList<Label> obListaCircuiti;
    @FXML
    private TableView<CRank> tableRanking;
    @FXML
    private Label labAcceleration;
    @FXML
    private Label labThrClose;
    @FXML
    private Label labThrOpen;
    @FXML
    private Label labLean;
    @FXML
    private Label labFrontBrake;
    @FXML
    private Label labFrontBrakeAllClose;
    @FXML
    private Label labFrontBrakeAllOpen;
    @FXML
    private Label labRearBrake;
    @FXML
    private Label labRearBrakeAllClose;
    @FXML
    private Label labRearBrakeAllOpen;
    @FXML
    private Label labGridCircuitSelected;
    //Binding
    private final SimpleStringProperty sLabGridCircuitSelected
            = new SimpleStringProperty();
    @FXML
    private TextField textfieldPilota;
    @FXML
    private TextField textfieldBike;
    @FXML
    private Button buttonAdmin;
    @FXML
    private TableColumn<CRank, Integer> tabColPosition;
    @FXML
    private TableColumn<CRank, String> tabColName;
    @FXML
    private TableColumn<CRank, String> tabColLevel;
    @FXML
    private TableColumn<CRank, String> tabColFrontBrake;
    @FXML
    private TableColumn<CRank, String> tabColRearBrake;
    @FXML
    private TableColumn<CRank, String> tabColThrottle;
    @FXML
    private TableColumn<CRank, String> tabColLean;
    @FXML
    private TableColumn<CRank, String> tabColGear;
    @FXML
    private TableColumn<CRank, Integer> tabColScore;
    @FXML
    private TableColumn<CRank, String> tabColBike;
    @FXML
    private Slider sliderLaps;
    @FXML
    private AnchorPane paneSetup;

    public AnchorPane getPaneSetup() {
        return paneSetup;
    }

    public TextField getTextfieldBike() {
        return textfieldBike;
    }

    public TextField getTextfieldPilota() {
        return textfieldPilota;
    }

    public Slider getSliderLaps() {
        return sliderLaps;
    }

    private final static ObservableList<CRank> TABRANKS = FXCollections.observableArrayList( //            new CRank(5, "Mario Andretti", "INTERMEDIATE", "100", "100", "100", "100", "100", 100, "Ducati 500 mk")
    );

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * tableview RANKINGS
         */
        tableRanking.setEditable(true);
        tableRanking.setItems(TABRANKS);
        tabColBike.setCellValueFactory(cellData -> cellData.getValue().colBikeProperty());
        tabColFrontBrake.setCellValueFactory(cellData -> cellData.getValue().colFrontBrakeProperty());
        tabColGear.setCellValueFactory(cellData -> cellData.getValue().colGearProperty());
        tabColLean.setCellValueFactory(cellData -> cellData.getValue().colLeanProperty());
        tabColLevel.setCellValueFactory(cellData -> cellData.getValue().colLevelProperty());
        tabColName.setCellValueFactory(cellData -> cellData.getValue().colNameProperty());
        tabColPosition.setCellValueFactory(cellData -> cellData.getValue().colPositionProperty().asObject());
        tabColRearBrake.setCellValueFactory(cellData -> cellData.getValue().colRearBrakeProperty());
        tabColScore.setCellValueFactory(cellData -> cellData.getValue().colScoreProperty().asObject());
        tabColThrottle.setCellValueFactory(cellData -> cellData.getValue().colThrottleProperty());

//        SpinnerValueFactory<Integer> valueFactory
//                = //
//                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
//
//        spinnerLaps.setValueFactory(valueFactory);
        /**
         * Con questa inizializzazione la Label visualizza e ridimensiona la
         * ImageView interna a livello scene-builder
         *
         * *
         */
        ObservableList<Label> items1 = FXCollections.observableArrayList(
                new Label("BEGINNER"),
                new Label("INTERMEDIATE"),
                new Label("EXPERT"),
                new Label("RIDER"));
        listViewDifficult.setItems(items1);
        listViewDifficult.getSelectionModel().selectFirst();
        /**
         * con questa label con image inside la listview visualizza i disegni
         * ObservableList<Label> items = FXCollections.observableArrayList( new
         * Label("Single"), new Label("Double"), new Label("Suite"), new
         * Label("Double"), new Label("Family App"));
         */
        obListaCircuiti = Ut.listVideoCircuiti();
        listCircuit.setItems(obListaCircuiti);
        ObservableList<Label> items2 = FXCollections.observableArrayList(
                new Label("NORMAL"),
                new Label("SPORT"));
        labelViewGearBoxType.setItems(items2);
        labelViewGearBoxType.getSelectionModel().selectFirst();
    }

    public void postInitialize() {
        //BINDINGS ARDUINO
        labAcceleration.textProperty().bind(ArduinoData.ACCELERATORE_PERC.asString("%3d%%"));
        labThrClose.textProperty().bind(ArduinoData.ACCELERATORE_MIN.asString("%6d"));
        labThrOpen.textProperty().bind(ArduinoData.ACCELERATORE_MAX.asString("%6d"));
        labFrontBrake.textProperty().bind(ArduinoData.FRENO_ANT_PERC.asString("%3d%%"));
        labFrontBrakeAllClose.textProperty().bind(ArduinoData.FRENO_ANT_MIN.asString("%6d"));
        labFrontBrakeAllOpen.textProperty().bind(ArduinoData.FRENO_ANT_MAX.asString("%6d"));
        labRearBrake.textProperty().bind(ArduinoData.FRENO_POST_PERC.asString("%3d%%"));
        labRearBrakeAllClose.textProperty().bind(ArduinoData.FRENO_POST_MIN.asString("%6d"));
        labRearBrakeAllOpen.textProperty().bind(ArduinoData.FRENO_POST_MAX.asString("%6d"));
        labLean.textProperty().bind(ArduinoData.ANGOLO.asString("%6d"));
        //BINDINGS OTHERS
        //Non posso metterlo, perché se non c'è selezione mi da null ed esce malamente...
        //Allora uso SimpleStringProperty
//        labGridCircuitSelected.textProperty().bind(
//                listCircuit.getSelectionModel().getSelectedItem().textProperty());
        //eccallà
        labGridCircuitSelected.textProperty().bind(sLabGridCircuitSelected);
        Ut.setSetupBackground();
    }

    @FXML
    private void importCircuits(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select circuit file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4", "*.MP4"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File f = fileChooser.showOpenDialog(StageManager.getStageAttuale());
        if (f == null) {
            return;
        }
        try {
            Path pathSrcVideo = f.toPath();
            Path pathSrcMtrk = Paths.get(f.getAbsolutePath()
                    .replace(".mp4", Prop.MASTER_TRACK_EXT)
                    .replace(".MP4", Prop.MASTER_TRACK_EXT)
            );
            Path pathDestVideo = Paths.get(Prop.CIRCUITI.toString(), f.getName().replace(".MP4", ".mp4"));
            Path pathDestMtrk = Paths.get(Prop.CIRCUITI.toString(), f.getName().replace(".mp4", Prop.MASTER_TRACK_EXT).replace(".MP4", Prop.MASTER_TRACK_EXT));
            Files.copy(pathSrcVideo, pathDestVideo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            if (pathSrcMtrk.toFile().exists()) {
                Files.copy(pathSrcMtrk, pathDestMtrk, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            }
            String nameNoExt = f.getName()
                    .replace(".mp4", "")
                    .replace(".MP4", "");
            if (pathDestMtrk.toFile().exists()) {
                obListaCircuiti.add(new Label(nameNoExt + Prop.MARK_TRACK_EXT));
            } else {
                obListaCircuiti.add(new Label(nameNoExt));
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void listCircuitSelected(MouseEvent event) {
        Label lbl = listCircuit.getSelectionModel().getSelectedItem();
        if (lbl != null) {
            sLabGridCircuitSelected.set(lbl.getText());
            LOGGER.info(lbl.getText());
            Prop.setMediaUrl(Paths.get(
                    Prop.CIRCUITI.toString(),
                    lbl.getText().replace(Prop.MARK_TRACK_EXT, "") + ".mp4"));
            try {
                //            FXMLVideoController fx = Mototrainer.getMyApp().getFXMLController();
//            fx.changeVideo(Paths.get(
//                    Prop.CIRCUITI.toString()
//                    ,lbl.getText().replace(Prop.MARK_TRACK_EXT,"")+".mp4"));
                Ut.importRankFromCSV(TABRANKS,
                        Prop.RANKS.toString()
                                + System.getProperty("file.separator")
                                + lbl.getText().replace(Prop.MARK_TRACK_EXT, "")
                                + Prop.RANK_EXT);
            } catch (Exception ex) {
//                Logger.getLogger(FXMLDataController.class.getName()).log(Level.SEVERE, null, ex);
                LOGGER.info(lbl.getText() + ":doesn't have rank file associated.");
            }
        }
    }

    @FXML
    private void startTheRace(ActionEvent event) {
        int i = listCircuit.getSelectionModel().getSelectedIndex();
        if (i < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(StageManager.getStageAttuale());
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please, select a circuit");
//        alert.setContentText("");
            //Recover Java BUG!! for alert and stage on external monitor
//            alert.setX(Mototrainer.getStage().getX()+
//                    Mototrainer.getStage().getWidth()/2);
//            alert.setY(Mototrainer.getStage().getY()+
//                    Mototrainer.getStage().getHeight()/2);
            alert.showAndWait();
            return;
        }

        FXMLVideoController fxml = (FXMLVideoController) StageManager.getController(EStage.VIDEO);
        fxml.changeVideo();
        fxml.playTheVideo();
        StageManager.showStage(EStage.VIDEO);
    }

    @FXML
    private void buttResetThrottle(ActionEvent event) {
        ArduinoData.getInstance().resetAcceleratore();
    }

    @FXML
    private void buttResetFrontBrake(ActionEvent event) {
        ArduinoData.getInstance().resetFrenoAnteriore();
    }

    @FXML
    private void buttResetRearBrake(ActionEvent event) {
        ArduinoData.getInstance().resetFrenoPosteriore();
    }

    @FXML
    private void onButtonExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onButtonAdmin(ActionEvent event) {
        StageManager.showStage(EStage.ADMIN);
    }
}

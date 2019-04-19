package it.ma.mototrainerp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author ma
 */
@SuppressWarnings("unused")
public class FXMLVideoController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(FXMLVideoController.class.getName());

    @FXML
    MediaView mediaView;
    private MediaPlayer mediaPlayer = null;
    @FXML
    private Pane PaneVideo;
    @FXML
    private Label labelPilota;
    @FXML
    private Label labelBike;
    @FXML
    private Button buttonExit;
    @FXML
    private Label labelLap;
    @FXML
    private Label labelTime;

    @FXML
    private ProgressBar progressBarFrontBreak;
    @FXML
    private ProgressBar progressBarRearBreak;
    @FXML
    private Group groupThumbs;
    @FXML
    private Text textPreCount;
    @FXML
    AnchorPane panePreCount;

    private boolean isPreCount = true;
    TimeLinePrecount ptv;

    public Label getLabelLap() {
        return labelLap;
    }

    public Label getLabelTime() {
        return labelTime;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Initialize video controller!!!");

        Scene scenaSetup = StageManager.getScene(EStage.SETUP);
//                SCENA.get(SceneManager.LeScene.SETUP).getScene();
//        ScaleThumbs scaleThumbs
//                = new ScaleThumbs(scenaSetup, groupThumbs);

    }

    public void postInitialize() {
        //Binds
        FXMLSetupController fxml = (FXMLSetupController) StageManager.getController(EStage.SETUP);
        labelBike.textProperty().bind(fxml.getTextfieldBike().textProperty());
        labelPilota.textProperty().bind(fxml.getTextfieldPilota().textProperty());
        progressBarFrontBreak.progressProperty().bind(ArduinoData.FRENO_ANT_PERC.divide(100d));
        progressBarRearBreak.progressProperty().bind(ArduinoData.FRENO_POST_PERC.divide(100d));

        //Properties
//        StageManager.getStage(EStage.VIDEO).focusedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown) -> {
//            if (getMediaPlayer() != null) {
//                if (onShown && getMediaPlayer().getStatus() != MediaPlayer.Status.PLAYING) {
//                    playTheVideo();
//                } else {
//                    if (getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
//                        getMediaPlayer().pause();
//                    }
//                }
//            }
//        });
        StageManager.getStage(EStage.VIDEO).setOnHidden((WindowEvent event) -> {
//            if (getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING)
//                getMediaPlayer().pause();
        });
        StageManager.getStage(EStage.VIDEO).setOnShown((WindowEvent event) -> {
//            getMediaPlayer().play();
        });

        mediaView.setOnError((MediaErrorEvent arg0) -> {
            System.out.println("view error");
            arg0.getMediaError().printStackTrace(System.out);
        });
        setMediaViewFullSize(mediaView);
        PaneVideo.getChildren().setAll(mediaView);

    }
    public static void setMediaViewFullSize(MediaView mv) {
        final DoubleProperty width = mv.fitWidthProperty();
        final DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        mv.setPreserveRatio(false);
    }
    public void playTheMedia(){
        mediaPlayer.play();
    }
    public void stopTheMedia(){
        mediaPlayer.stop();
    }

    /**
     * Used only from button into SETUP stage
     */
    public void playTheVideo() {
        if (mediaPlayer != null) {
            if (isPreCount) {
//                thd1 = new DoPreCount(this);
//                thd1.execute();
                ptv = new TimeLinePrecount(this).play();
                isPreCount = false;
            }
        }
    }

    class TimeLinePrecount {

        //Timer to count until msecs
        private Timeline tl = null;
        private int counter;
        FXMLVideoController fxml;

        public TimeLinePrecount(FXMLVideoController fxml) {
            this.fxml = fxml;
            counter = 3;
            fxml.textPreCount.setText("");
            fxml.panePreCount.setVisible(true);
            tl = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                fxml.textPreCount.setText(counter-- + "");
            }));
            tl.setCycleCount(4); // repeat 
            tl.setOnFinished(event -> {
                fxml.panePreCount.setVisible(false);
                if (StageManager.getEStageAttuale().equals(EStage.VIDEO)) {
                    playTheMedia();
                }
            });
        }

        TimeLinePrecount play() {
            tl.play();
            return this;
        }

        void stop() {
            if (tl != null) {
                fxml.panePreCount.setVisible(false);
                tl.stop();
            }
        }
    }

    /**
     * Il filmato precedente partirà daccapo, dato il dispose.
     */
    @SuppressWarnings("unused")
    public void changeVideo() {
        isPreCount = true;
        Path videoPath = Prop.getMediaUrl();
        if (!videoPath.toFile().exists()) {
            videoPath = Prop.getMediaUrlDefault();
            Prop.setMediaUrl(Prop.getMediaUrlDefault());
        }
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        Media ilVideo = new Media(videoPath.toUri().toString());
        ilVideo.setOnError(() -> {
            System.out.println("Media error");
            ilVideo.getError().printStackTrace(System.out);
        });
        mediaPlayer = new MediaPlayer(ilVideo);
        mediaPlayer.setOnError(() -> {
            System.out.println("player error");
            mediaPlayer.getError().printStackTrace(System.out);
        });

        mediaView.setMediaPlayer(mediaPlayer);
        VideoProcessing videoProcessing = new VideoProcessing(mediaPlayer,
                mediaView,
                (FXMLVideoController) StageManager.getController(EStage.VIDEO)
        );
    }

    @FXML
    private void ButBackFromVideoOnAction(ActionEvent event) {
//        thd1.getBackGroundThread().interrupt();
        ptv.stop();
        stopTheMedia();
        StageManager.showStageButHide(EStage.SETUP, buttonExit.getScene().getWindow());
    }

//    private void playaa(ActionEvent event) {
//        Prop.setMediaUrl(Paths.get(Prop.CIRCUITI.toString(), "aa.mp4"));
//        changeVideo();
//    }
}

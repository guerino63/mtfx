/*
 * lavora qui per interazione con filmato!!!
 */
package it.ma.mototrainerp;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author maria
 */
public class VideoProcessing {
    private final static Log LOGGER = LogFactory.getLog(VideoProcessing.class);

    private final MediaPlayer mp;
    private final FXMLVideoController fxmlVideo;
    Duration duration;
    static TimeLineMsecs timeLine = new TimeLineMsecs();
    static TimeLineLuigi timeLineLuigi = new TimeLineLuigi();
    static ServerLuigi serverLuigi = new ServerLuigi();

    private void timLineAndTaskInit() {
        serverLuigi.restart();
        timeLine.stop();
        timeLine.init(0, fxmlVideo);
        timeLineLuigi.stop();
        timeLineLuigi.init(0, fxmlVideo);
    }

    private void timeLineAndTaskStop() {
        timeLine.stop();
        timeLineLuigi.stop();
        serverLuigi.cancel();
    }

    static class TimeLineMsecs {

        //Timer to count until msecs
        private Timeline tl = null;
        private long timeMSecs;
        FXMLVideoController fxml;

        void init(final long STARTTIME, FXMLVideoController fxml) {
            this.fxml = fxml;
            timeMSecs = STARTTIME;
            //**NON CAMBIARE KEY FRAME!
            tl = new Timeline(new KeyFrame(Duration.millis(1), evt -> {
                timeMSecs++;
                Duration currentTime = new Duration(timeMSecs);
                double minuti = currentTime.toMinutes() % 60;
                double secondi = currentTime.toSeconds() % 60;
                double msecs = (currentTime.toMillis()) % 1000;
                String tempo = String.format("%02.0f:%02.0f%c%02.0f", minuti, secondi, '.', msecs / 10);
                fxml.getLabelTime().setText(tempo);
            }));
            tl.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        }

        void reset(){
            timeMSecs = 0;
        }
        void play() {
            tl.play();
        }

        void stop() {
            if (tl != null) {
                tl.stop();
            }
        }
    }

    public VideoProcessing(MediaPlayer mp, MediaView mediaView, FXMLVideoController fxml) {

//        Utility.msgDebug("is VideoProcessing class running on FXThread?"+isFxApplicationThread());
//Yes it is, so i called directly(be care about time consuming tasks)
        this.mp = mp;
        this.fxmlVideo = fxml;
        //crea un BIND coi LAPS
        FXMLSetupController fxmlSetup = (FXMLSetupController) StageManager.getController(EStage.SETUP);
        mp.setCycleCount((int) fxmlSetup.getSliderLaps().getValue());
        String formatted = "Lap %d of " + (int) fxmlSetup.getSliderLaps().getValue();
        fxml.getLabelLap().setText(String.format(formatted, mp.getCurrentCount() + 1));
        /**
         * Class timeline timer
         */
        timLineAndTaskInit();

        mp.currentTimeProperty().addListener((Observable ov) -> {
            updateValues();
        });
        mp.setOnPlaying(() -> {
            LOGGER.info("Start playing...:");
            timeLine.play();
            timeLineLuigi.play();
        });
        mp.setOnRepeat(() -> {
            LOGGER.info(String.format("repeat:%d", mp.getCurrentCount()));
            fxml.getLabelLap().setText(String.format(formatted, mp.getCurrentCount() + 1));
            /**
             * test, rimuovere poi usando il TODO

            if (mp.getCurrentCount() == 1) {
                StageManager.showStage(EStage.CLIP);
                FXMLClipsController fxmlClips = (FXMLClipsController) StageManager.getController(EStage.CLIP);
                fxmlClips.changeClip(Paths.get(Prop.CLIPS.toString(), "clip1.mp4"));
                fxmlClips.playClip();
            }*/
            timeLine.reset();            
        });
        mp.setOnEndOfMedia(() -> {
//                mp.seek(Duration.ZERO);   mp.play();  //manual repeat
            LOGGER.info(String.format("\"End of media\":%d", mp.getCurrentCount()));
            if ((int) fxmlSetup.getSliderLaps().getValue() - mp.getCurrentCount() == 0) {
                timeLineAndTaskStop();
                StageManager.showStageButHide(EStage.POSTVIDEO, mediaView.getScene().getWindow());
            }
        });
        mp.setOnStopped(() -> {
            LOGGER.info("Stopped");
            timeLineAndTaskStop();
            StageManager.showStageButHide(EStage.SETUP, mediaView.getScene().getWindow());
        });
        mp.setOnError(() -> {
            timeLineAndTaskStop();
            LOGGER.info(String.format("Error playing:%s", mp.getError().toString()));
        });
        mp.setOnReady(() -> {
            LOGGER.info(String.format("Ready playing:%s", mp.getStatus().toString()));
        });
        mp.setOnStalled(() -> {
            timeLineAndTaskStop();
            LOGGER.info(String.format("Stalled playing:%s", mp.getStatus().toString()));
        });

    }

    /**
     * This method is call Approx every 100mSecs after the play of video pay
     * attention to consuming time, and use AsyncTask.
     *
     */
    private void updateValues() {

//        elapsedTime thd1 = new elapsedTime(fxmlVideo);
//        thd1.execute();
//        {   //tst breaks progressing bar. Remarks when done
//            int fa = ArduinoData.frenoAnteriorePercent.get();
//            if(fa>100) fa=0;
//            ArduinoData.frenoAnteriorePercent.set(++fa);
//            int fp = ArduinoData.frenoPosteriorePercent.get();
//            if(fp>100) fp=0;
//            ArduinoData.frenoPosteriorePercent.set(++fp);
//        }
    }

    /**
     * !!!non sarebbe cosi necessario usare un' Async...ma vabbè serve anche per
     * descrizione... We don't need to use the progressCallback(Object...
     * params) of AsyncTask (at least for now...) because updateValue is called
     * every 100msec, and so the AsyncTask job arrive on onPostExecute(every 100
     * msecs.)
     *
     */
//    class elapsedTime extends AsyncTask {
//
//        private final FXMLVideoController controller;
//
//        elapsedTime(FXMLVideoController controller) {
//            this.controller = controller;
//        }
//        String tempo;
//
//        @Override
//        public void onPreExecute() {
////            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        //Datosi che il timer mi arriva da sistema
//        @Override
//        public Object doInBackground(Object... params) {
////            Utility.msgDebug("isFxApplicationThread dovrebbe essere falso:" + isFxApplicationThread());
//            Duration currentTime = mp.getCurrentTime();
//            double minuti = currentTime.toMinutes() % 60;
//            double secondi = currentTime.toSeconds() % 60;
//            double msecs = (currentTime.toMillis()) % 1000;
//            tempo = String.format("%02.0f:%02.0f%c%1.0f", minuti, secondi, '.', msecs / 100);
//            return tempo;
//        }
//
//        @Override
//        public void onPostExecute(Object params) {
////            Utility.msgDebug("la callback!!!, isFxApplicationThread dovrebbe essere vero:" + isFxApplicationThread());
////            fxmlVideo.getLabelTime().setText(tempo);
//            controller.getLabelTime().setText(tempo);
//        }
//
//        @Override
//        public void progressCallback(Object... params) {
////            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//    }
}

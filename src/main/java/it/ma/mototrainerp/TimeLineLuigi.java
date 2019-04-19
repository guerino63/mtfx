/*
*manovre grafiche sul video
 */
package it.ma.mototrainerp;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author maria
 */
public class TimeLineLuigi {
    
        //Timer to count until msecs
        private Timeline tl = null;
        private long timeMSecs;
        FXMLVideoController fxml;

        void init(final long STARTTIME, FXMLVideoController fxml) {
            this.fxml = fxml;
            timeMSecs = STARTTIME;
            tl = new Timeline(new KeyFrame(Duration.millis(100), evt -> {
//                timeMSecs++;
//                Duration currentTime = new Duration(timeMSecs);
//                double minuti = currentTime.toMinutes() % 60;
//                double secondi = currentTime.toSeconds() % 60;
//                double msecs = (currentTime.toMillis()) % 1000;
//                String tempo = String.format("%02.0f:%02.0f%c%02.0f", minuti, secondi, '.', msecs / 10);
//                fxml.getLabelTime().setText(tempo);
//Metti qui il tuo codice....
//
            }));
            tl.setCycleCount(Animation.INDEFINITE); // repeat over and over again
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

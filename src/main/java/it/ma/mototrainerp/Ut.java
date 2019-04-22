package it.ma.mototrainerp;

import it.ma.tables.CRank;
import it.ma.tables.Tolerances;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ut {
    private final static Log LOGGER = LogFactory.getLog(Ut.class);
        public static void importToleranceFromCSV(ObservableList<Tolerances> table,
            String pathToImport) throws Exception {

        List<String> list = Files.readAllLines(Paths.get(pathToImport), StandardCharsets.UTF_8);
        String[] a = list.toArray(new String[list.size()]);
        if (a.length != 7) {  //Numero di linee del file
            throw new Exception("The tolerance file is incorrect: Line number doesn't match.(!=7)");
        }
        table.clear();
        //la riga 0 contiene headers, quindi la skyppo
        for (int ndx = 1; ndx < a.length; ndx++) {
            String[] s = a[ndx].split(",");
            if (s.length > 8) {
                table.add(new Tolerances(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8]));
            } else {
                table.add(new Tolerances(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7]));
            }
        }
    }

    public static void exportToleranceToCSV(ObservableList<Tolerances> table,
            String pathToExportTo) {
        try {
            try (FileWriter csv = new FileWriter(new File(pathToExportTo))) {
                csv.write(",Beginner,,Intermediate,,Expert,,Rider,,\n");
                for (Tolerances rec : table) {
                    csv.write(rec.toString() + "\n");
                }
            }
        } catch (IOException ex) {
            LOGGER.error("", ex);
        }
    }
    public static void importRankFromCSV(ObservableList<CRank> table, String pathToImport) throws Exception {
        table.clear();
        List<String> list = Files.readAllLines(Paths.get(pathToImport), StandardCharsets.UTF_8);
        String[] a = list.toArray(new String[list.size()]);
        int counter = 1;
        for (String a1 : a) {
            String[] s = a1.split(",");
            if (s.length != 9) {
                throw new Exception(pathToImport + " Data errors!");
            }
            table.add(0, new CRank(counter++, s[0], s[1], s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]), s[8]));
        }

        /**
         * Start the sort.
         */
        Comparator<CRank> byScore = (CRank o1, CRank o2) -> o2.getColScore().compareTo(o1.getColScore());
        SortedList<CRank> sortedList = new SortedList<>(table, byScore);

        /**
         * Now adjust RANK number on first unordered list.
         */
        int positionToUnorderList = 1;
        for (CRank r : sortedList) {
            table.get(r.getColPosition() - 1).
                    setcolPosition(positionToUnorderList++);
        }
    }
    static public void setSetupBackground() {
        Image img = new Image("file:" + Prop.IMGBACKGROUND.toString());
        FXMLSetupController fxml = (FXMLSetupController) StageManager.getController(EStage.SETUP);

        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background bgImg = new Background(backgroundImage);
        fxml.getPaneSetup().setBackground(bgImg);
    }

    public static ObservableList<Label> listVideoCircuiti() {
        File f = Prop.CIRCUITI.toFile();

        FilenameFilter filter = (File directory, String fileName) 
                -> fileName.toLowerCase().endsWith(".mp4");

        File[] files = f.listFiles(filter);
        if (files == null) 
            return null;
        
        List<Label> list = new ArrayList<>();
        for (File fil : files) {
            String s = fil.getName().substring(0, fil.getName().length() - 4);
            String masterTrack
                    = Prop.CIRCUITI + System.getProperty("file.separator")
                    + s + Prop.MASTER_TRACK_EXT;
            if (new File(masterTrack).exists()) 
                s += Prop.MARK_TRACK_EXT;
            list.add(new Label(s));
        }
        return FXCollections.observableArrayList(list);
    }
}

interface UtCopy {
    void fileCopied(String fileC);

    default void copyFolder(Path src, Path dest) throws IOException {
        Files.walk(src)
                .forEach(source -> copy(source, dest.resolve(src.relativize(source))));
    }

    default void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            fileCopied(source.toString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

/**
 * File delle proprietà(Singleton)
 * con Prop si accede agli static come i vari paths(es:Prop.CIRCUITI)
 * ed anche alle properties read/write che andranno sul file di proprietà
 * Prop.Desc.FRENO_ANTERIORE_MIN.getValue(XXX), attraverso l' enum Desc.
 */
package it.ma.mototrainerp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author maria
 */
public class Prop {
    private static final Logger LOGGER = Logger.getLogger(Prop.class.getName());

    public static final Path PATH_DEFAULT
            = Paths.get("conf$$");

    public static final Path PATH
            = Paths.get(System.getProperty("user.home"), ".mtfx");

    //File proprietà
    private static final Path FILPROP = Paths.get(PATH.toString(), "prop.xml");
    //Path circuiti
    public static final Path CIRCUITI = Paths.get(PATH.toString(), "circuiti");
    //Path clips
    public static final Path CLIPS = Paths.get(PATH.toString(), "clips");
    //Path Ranks
    public static final Path RANKS = Paths.get(PATH.toString(), "ranks");
    //File tolleranze
    public static final Path FILETOLERANCE = Paths.get(PATH.toString(), "Tolleranze", "tolerance.csv");
    //Background
    public static final Path IMGBACKGROUND = Paths.get(PATH.toString(), "background-img.png");

    final static public String RANK_EXT = ".rnk";
    final static public String MASTER_TRACK_EXT = ".mtk";
    final static public String MARK_TRACK_EXT = "(*)";

    //File circuito di default
    private static Path mediaUrl
            = Paths.get(CIRCUITI.toString(), "default.mp4");
    private final static Path MEDIA_URL_DEFAULT
            = Paths.get(CIRCUITI.toString(), "default.webm");

    //non è statica!
    public static Path getMediaUrlDefault() {
        return MEDIA_URL_DEFAULT;
    }

    public static Path getMediaUrl() {
        return mediaUrl;
    }

    public static void setMediaUrl(Path mediaUrl) {
        Prop.mediaUrl = mediaUrl;
    }

    /**
     * Notare che è privato.
     */
    private static final Properties PROP = new Properties();

    //Setup
    public enum Desc {
        //Setup macchina SECTION
        ACCELERATORE_MIN("65000"),
        ACCELERATORE_MAX("0"),
        FRENO_POSTERIORE_MIN("65000"),
        FRENO_POSTERIORE_MAX("0"),
        FRENO_ANTERIORE_MIN("65000"),
        FRENO_ANTERIORE_MAX("0"),
        //Admin SECTION
        PASSWORD_ADMIN("mototrainer");
        final String defaultValue;

        Desc(String value) {
            this.defaultValue = value;
        }

        public String getValue() {
            return (String) PROP.getOrDefault(this.name(), this.defaultValue);
        }

        public int getValueInt() {
            return Integer.parseInt((String) PROP.getOrDefault(this.name(), this.defaultValue));
        }

        public void setValue(String val) {
            PROP.setProperty(this.name(), val);
        }

        void setDefaultValue() {
            PROP.setProperty(this.name(), defaultValue);
        }
    }

    public void oneShotLoadProperties() {
        /**
         * some System info
         */
        LOGGER.log(Level.INFO, "Application directory->{0}", System.getProperty("user.dir"));
        LOGGER.log(Level.INFO, "JAVA Version->{0}", System.getProperty("java.runtime.version"));
        LOGGER.log(Level.INFO, "mtfx Version(*Manifest)->{0}", getClass().getPackage().getSpecificationVersion());
        /**
         * Se non esiste $user.home.mototrainerp, la creo e copio CONF$$
         * Ma solo se trovo conf in user.dir
         */
        if (!Files.exists(PATH) && Files.exists(PATH_DEFAULT)) {
            LOGGER.log(Level.INFO, "Create {0} Directory", Prop.PATH);
            UtCopy cp = fileC -> LOGGER.info(fileC);
            try {
                cp.copyFolder(PATH_DEFAULT, PATH);
            } catch (IOException ex) {
                Logger.getLogger(Prop.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            LOGGER.log(Level.INFO, "{0} exists or {1} not found, skip files copy.", new Object[]{PATH, PATH_DEFAULT});
        }

        boolean erroreParse = false;
        try {
            FileInputStream fi = new FileInputStream(FILPROP.toFile());
            PROP.loadFromXML(fi);
        } catch (FileNotFoundException ex) {
            erroreParse = true;
            Logger.getLogger(Prop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            erroreParse = true;
            Logger.getLogger(Prop.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (erroreParse) {
                LOGGER.warning("Properties file not correct,"
                        + "Will be used default data.");
            } else{
                LOGGER.info("Properties file correctly read.");
            }
        }
        LOGGER.log(Level.INFO, "Properties initialized!{0}", (erroreParse) ? "...anyway" : "");
    }

    /**
     * !!!!!!!!!! Ultimo blocco statico da eseguire!!!!! NON MUOVERE. Carica il
     * file di proprietà, che necessita di tutte le variabili precedenti mettilo
     * sempre per ultimo.
     */
    private Prop() {
        LOGGER.log(Level.INFO, "File properties:{0}", FILPROP.toUri().toString());
    }

    /**
     * Salvataggio dati di properties. Chiamato alla chiusura della piattaforma.
     */
    public static void saveProperties() {
        try {
            FileOutputStream fi = new FileOutputStream(FILPROP.toFile());
            PROP.storeToXML(fi, "File properties");
        } catch (IOException ex) {
            Logger.getLogger(Prop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static Prop getInstance() {
        return PropHolder.INSTANCE;
    }

    private static class PropHolder {

        private static final Prop INSTANCE = new Prop();
    }

    public static void main(String[] s) {
        LOGGER.info("start some test...");

        LOGGER.info("...Default values");
        for (Desc f : Desc.values()) {
            f.setDefaultValue();
            LOGGER.log(Level.INFO, "{0}={1}", new Object[]{f.name(), f.getValue()});
        }
        LOGGER.info("...rewrite the default values");
        int i = 0;
        for (Desc f : Desc.values()) {
            f.setValue(i++ + "");
            LOGGER.log(Level.INFO, "{0}={1}", new Object[]{f.name(), f.getValue()});
        }
    }
}

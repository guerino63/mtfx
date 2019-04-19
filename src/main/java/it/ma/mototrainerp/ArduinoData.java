/*

 */
package it.ma.mototrainerp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.commons.codec.binary.Hex;

import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class ArduinoData {
    private static final Logger LOGGER = Logger.getLogger(Prop.class.getName());

    private static final SimpleIntegerProperty ACCELERATORE = new SimpleIntegerProperty(1);
    static public final SimpleIntegerProperty ACCELERATORE_MIN = new SimpleIntegerProperty(65000);
    static public final SimpleIntegerProperty ACCELERATORE_MAX = new SimpleIntegerProperty(0);
    static public final SimpleIntegerProperty ACCELERATORE_PERC = new SimpleIntegerProperty(75);
    private static final SimpleIntegerProperty FRENOPOST = new SimpleIntegerProperty(2);
    static public final SimpleIntegerProperty FRENO_POST_MIN = new SimpleIntegerProperty(65000);
    static public final SimpleIntegerProperty FRENO_POST_MAX = new SimpleIntegerProperty(0);
    static public final SimpleIntegerProperty FRENO_POST_PERC = new SimpleIntegerProperty(50);
    private static final SimpleIntegerProperty FRENO_ANT = new SimpleIntegerProperty(3);
    static public final SimpleIntegerProperty FRENO_ANT_MIN = new SimpleIntegerProperty(65000);
    static public final SimpleIntegerProperty FRENO_ANT_MAX = new SimpleIntegerProperty(0);
    static public final SimpleIntegerProperty FRENO_ANT_PERC = new SimpleIntegerProperty(3);
    static public final SimpleIntegerProperty ANGOLO = new SimpleIntegerProperty(90);
    private static final SimpleBooleanProperty MARCIA_INC = new SimpleBooleanProperty();
    private static final SimpleBooleanProperty MARCIA_DEC
            = new SimpleBooleanProperty();

    private ArduinoData() {
    }

    /**
     * Dati da arduino !!!! F0 & F7 non vengono passati 11 byte: F0 -> Inizio
 messaggio

 MSB LSB 0000_0xxx 0xxx_xxxx 2 byte (utilizzati solo 10 bit) Freno
 Anteriore ( 0 - 1023 ) 0000_0yyy 0yyy_yyyy 2 byte (utilizzati solo 10
 bit) Freno Posteriore ( 0 - 1023 ) 0000_0zzz 0zzz_zzzz 2 byte (utilizzati
 solo 10 bit) Acceleratore ( 0 - 1023 ) 0000_0aaa 0aaa_aaaa 2 byte
 (utilizzati solo 10 bit) ANGOLO ( 0 - 1023 ) 0000_00id 1 byte (utilizzati
 solo 2 bit) incrementa marcia (0000_0010), decrementa marcia (0000_0001)
 F7 -> Fine messaggio
     *
     * @param buf
     */
    public void setData(byte[] buf) {
        LOGGER.info(Hex.encodeHexString( buf ));
        FRENO_ANT.set(from10BitsToInt(buf[0], buf[1]));
        FRENOPOST.set(from10BitsToInt(buf[2], buf[3]));
        ACCELERATORE.set(from10BitsToInt(buf[4], buf[5]));
        ANGOLO.set(from10BitsToInt(buf[6], buf[7]));
        MARCIA_INC.set(isMarciaInc(buf[8]));
        MARCIA_DEC.set(isMarciaDec(buf[8]));
        LOGGER.info(toString());

        //setup
        if (Prop.Desc.FRENO_ANTERIORE_MIN.getValueInt() > FRENO_ANT.get()) {
            Prop.Desc.FRENO_ANTERIORE_MIN.setValue(FRENO_ANT.get() + "");
        }
        FRENO_ANT_MIN.set(Prop.Desc.FRENO_ANTERIORE_MIN.getValueInt());
        if (Prop.Desc.FRENO_ANTERIORE_MAX.getValueInt() < FRENO_ANT.get()) {
            Prop.Desc.FRENO_ANTERIORE_MAX.setValue(FRENO_ANT.get() + "");
        }
        FRENO_ANT_MAX.set(Prop.Desc.FRENO_ANTERIORE_MAX.getValueInt());
        FRENO_ANT_PERC.set(percent(
                Prop.Desc.FRENO_ANTERIORE_MIN.getValueInt()
                , Prop.Desc.FRENO_ANTERIORE_MAX.getValueInt()
                ,FRENO_ANT.get()));
        if (Prop.Desc.FRENO_POSTERIORE_MIN.getValueInt() > FRENOPOST.get()) {
            Prop.Desc.FRENO_POSTERIORE_MIN.setValue(FRENOPOST.get() + "");
        }
        FRENO_POST_MIN.set(Prop.Desc.FRENO_POSTERIORE_MIN.getValueInt());
        if (Prop.Desc.FRENO_POSTERIORE_MAX.getValueInt() < FRENOPOST.get()) {
            Prop.Desc.FRENO_POSTERIORE_MAX.setValue(FRENOPOST.get() + "");
        }
        FRENO_POST_MAX.set(Prop.Desc.FRENO_POSTERIORE_MAX.getValueInt());
        FRENO_POST_PERC.set(percent(
                Prop.Desc.FRENO_POSTERIORE_MIN.getValueInt()
                , Prop.Desc.FRENO_POSTERIORE_MAX.getValueInt()
                ,FRENOPOST.get()));
        if (Prop.Desc.ACCELERATORE_MIN.getValueInt() > ACCELERATORE.get()) {
            Prop.Desc.ACCELERATORE_MIN.setValue(ACCELERATORE.get() + "");
        }
        ACCELERATORE_MIN.set(Prop.Desc.ACCELERATORE_MIN.getValueInt());
        if (Prop.Desc.ACCELERATORE_MAX.getValueInt() < ACCELERATORE.get()) {
            Prop.Desc.ACCELERATORE_MAX.setValue(ACCELERATORE.get() + "");
        }
        ACCELERATORE_MAX.set(Prop.Desc.ACCELERATORE_MAX.getValueInt());
        ACCELERATORE_PERC.set(percent(
                Prop.Desc.ACCELERATORE_MIN.getValueInt()
                , Prop.Desc.ACCELERATORE_MAX.getValueInt()
                ,ACCELERATORE.get()));
    }

    public void resetFrenoAnteriore() {
        Prop.Desc.FRENO_ANTERIORE_MIN.setValue(Short.MAX_VALUE*2 + "");
        ArduinoData.FRENO_ANT_MIN.set(Short.MAX_VALUE*2);
        Prop.Desc.FRENO_ANTERIORE_MAX.setValue(0 + "");
        ArduinoData.FRENO_ANT_MAX.set(0);
    }

    public void resetFrenoPosteriore() {
        Prop.Desc.FRENO_POSTERIORE_MIN.setValue(Short.MAX_VALUE*2 + "");
        ArduinoData.FRENO_POST_MIN.set(Short.MAX_VALUE*2);
        Prop.Desc.FRENO_POSTERIORE_MAX.setValue(0 + "");
        ArduinoData.FRENO_POST_MAX.set(0);
    }

    public void resetAcceleratore() {
        Prop.Desc.ACCELERATORE_MIN.setValue(Short.MAX_VALUE*2 + "");
        ArduinoData.ACCELERATORE_MIN.set(Short.MAX_VALUE*2);
        Prop.Desc.ACCELERATORE_MAX.setValue(0 + "");
        ArduinoData.ACCELERATORE_MAX.set(0);
    }

    //0000_0xxx 0xxx_xxxx  2 byte (utilizzati solo 10 bit)
    private int from10BitsToInt(int msb, int lsb) {
        if ((msb & 0x01) != 0) {
            lsb |= 0x80;
        }
        msb >>= 1;
        return (msb & 0x03) * 256 + lsb;
    }

    private boolean isMarciaInc(byte b) {
        return ((b & 0x02) != 0);
    }

    private boolean isMarciaDec(byte b) {
        return ((b & 0x01) != 0);
    }

    private int percent(int min, int max, int value) {
        //A=(max-min), B=(value-min)
        //A : 100 = B : X
        //result = B*100/A
        return (value - min) * 100 / (max - min);
    }

    @Override
    public String toString() {
        return "Arduino ha spedito: Freno anteriore:" + FRENO_ANT.get()
                + " Freno posteriore:" + FRENOPOST.get()
                + " Acceleratore:" + ACCELERATORE.get()
                + " Angolo:" + ANGOLO.get()
                + " Marcia Inc.:" + MARCIA_INC.get()
                + " Marcia Dec.:" + MARCIA_DEC.get();
    }

    public static ArduinoData getInstance() {
        return ArduinoDataHolder.INSTANCE;
    }

    private static class ArduinoDataHolder {

        private static final ArduinoData INSTANCE = new ArduinoData();
    }
}

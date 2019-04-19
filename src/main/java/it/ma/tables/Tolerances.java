package it.ma.tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author maria
 */
public class Tolerances {

    private final StringProperty rowHeaders;
    private final StringProperty colBeginner;
    private final StringProperty colBeginnerAvviso;
    private final StringProperty colIntermediate;
    private final StringProperty colIntermediateAvviso;
    private final StringProperty colExpert;
    private final StringProperty colExpertAvviso;
    private final StringProperty colRider;
    private final StringProperty colRiderAvviso;

    public String getRowHeaders() {
        return rowHeaders.get();
    }

    public void setRowHeaders(String value) {
        rowHeaders.set(value);
    }

    public StringProperty rowHeadersProperty() {
        return this.rowHeaders;
    }

    
    public String getColBeginner() {
        return colBeginner.get();
    }

    public void setColBeginner(String value) {
        colBeginner.set(value);
    }

    public StringProperty colBeginnerProperty() {
        return this.colBeginner;
    }
    public String getColBeginnerAvviso() {
        return colBeginnerAvviso.get();
    }

    public void setColBeginnerAvviso(String value) {
        colBeginnerAvviso.set(value);
    }

    public StringProperty colBeginnerAvvisoProperty() {
        return this.colBeginnerAvviso;
    }
    public String getColIntermediate() {
        return colIntermediate.get();
    }

    public void setColIntermediate(String value) {
        colIntermediate.set(value);
    }
    public StringProperty colIntermediateProperty() {
        return this.colIntermediate;
    }

    public String getColIntermediateAvviso() {
        return colIntermediateAvviso.get();
    }

    public void setColIntermediateAvviso(String value) {
        colIntermediateAvviso.set(value);
    }
    public StringProperty colIntermediateAvvisoProperty() {
        return this.colIntermediateAvviso;
    }

    public String getColExpert() {
        return colExpert.get();
    }

    public void setColExpert(String value) {
        colExpert.set(value);
    }
    public StringProperty colExpertProperty() {
        return this.colExpert;
    }

    public String getColExpertAvviso() {
        return colExpertAvviso.get();
    }

    public void setColExpertAvviso(String value) {
        colExpertAvviso.set(value);
    }
    public StringProperty colExpertAvvisoProperty() {
        return this.colExpertAvviso;
    }

    public String getColRider() {
        return colRider.get();
    }

    public void setColRider(String value) {
        colRider.set(value);
    }
    public StringProperty colRiderProperty() {
        return this.colRider;
    }

    public String getColRiderAvviso() {
        return colRiderAvviso.get();
    }

    public void setColRiderAvviso(String value) {
        colRiderAvviso.set(value);
    }
    public StringProperty colRiderAvvisoProperty() {
        return this.colRiderAvviso;
    }

    public Tolerances(String rowHeaders, String colBeginner, String colBeginnerAvviso,
            String colIntermediate, String colIntermediateAvviso, String colExpert,
            String colExpertAvviso, String colRider) {
        this(rowHeaders, colBeginner, colBeginnerAvviso,
                colIntermediate, colIntermediateAvviso, colExpert,
                colExpertAvviso, colRider, "");
    }

    public Tolerances(String rowHeaders, String colBeginner, String colBeginnerAvviso,
            String colIntermediate, String colIntermediateAvviso, String colExpert,
            String colExpertAvviso) {
        this(rowHeaders, colBeginner, colBeginnerAvviso,
                colIntermediate, colIntermediateAvviso, colExpert,
                colExpertAvviso, "", "");
    }

    public Tolerances(String rowHeaders, String colBeginner, String colBeginnerAvviso,
            String colIntermediate, String colIntermediateAvviso, String colExpert,
            String colExpertAvviso, String colRider, String colRiderAvviso) {
        this.rowHeaders = new SimpleStringProperty(rowHeaders);
        this.colBeginner = new SimpleStringProperty(colBeginner);
        this.colBeginnerAvviso = new SimpleStringProperty(colBeginnerAvviso);
        this.colIntermediate = new SimpleStringProperty(colIntermediate);
        this.colIntermediateAvviso = new SimpleStringProperty(colIntermediateAvviso);
        this.colExpert = new SimpleStringProperty(colExpert);
        this.colExpertAvviso = new SimpleStringProperty(colExpertAvviso);
        this.colRider = new SimpleStringProperty(colRider);
        this.colRiderAvviso = new SimpleStringProperty(colRiderAvviso);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", rowHeaders.get(),
                 colBeginner.get(), colBeginnerAvviso.get(), colIntermediate.get(),
                 colIntermediateAvviso.get(), colExpert.get(),
                 colExpertAvviso.get(), colRider.get(), colRiderAvviso.get());
    }

}

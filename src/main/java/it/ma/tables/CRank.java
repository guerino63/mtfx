package it.ma.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author maria
 */
public class CRank {
    
    private final IntegerProperty colPosition;
    public IntegerProperty colPositionProperty() {
        return this.colPosition;
    }
    public Integer getColPosition() {
        return colPosition.get();
    }
    public void setcolPosition(Integer value) {
        colPosition.set(value);
    }
    
    private final StringProperty colName;
    public StringProperty colNameProperty() {
        return this.colName;
    }
    public String getColName() {
        return colName.get();
    }
    public void setColName(String value) {
        colName.set(value);
    }
    
    private final StringProperty colLevel;
    public StringProperty colLevelProperty() {
        return this.colLevel;
    }
    public String getColLevel() {
        return colLevel.get();
    }
    public void setColLevel(String value) {
        colLevel.set(value);
    }
    private final StringProperty colFrontBrake;
    public StringProperty colFrontBrakeProperty() {
        return this.colFrontBrake;
    }
    public String getColFrontBrake() {
        return colFrontBrake.get();
    }
    public void setColFrontBrake(String value) {
        colFrontBrake.set(value);
    }
    private final StringProperty colRearBrake;
    public StringProperty colRearBrakeProperty() {
        return this.colRearBrake;
    }
    public String getColRearBrake() {
        return colRearBrake.get();
    }
    public void setColRearBrake(String value) {
        colRearBrake.set(value);
    }
    private final StringProperty colThrottle;
    public StringProperty colThrottleProperty() {
        return this.colThrottle;
    }
    public String getColThrottle() {
        return colThrottle.get();
    }
    public void setColThrottle(String value) {
        colThrottle.set(value);
    }
    private final StringProperty colLean;
    public StringProperty colLeanProperty() {
        return this.colLean;
    }
    public String getColLean() {
        return colLean.get();
    }
    public void setColLean(String value) {
        colLean.set(value);
    }
    private final StringProperty colGear;
    public StringProperty colGearProperty() {
        return this.colGear;
    }
    public String getColGear() {
        return colGear.get();
    }
    public void setColGear(String value) {
        colGear.set(value);
    }
    private final IntegerProperty colScore;
    public IntegerProperty colScoreProperty() {
        return this.colScore;
    }
    public Integer getColScore() {
        return colScore.get();
    }
    public void setColScore(Integer value) {
        colScore.set(value);
    }
    private final StringProperty colBike;
    public StringProperty colBikeProperty() {
        return this.colBike;
    }
    public String getColBike() {
        return colBike.get();
    }
    public void setColBike(String value) {
        colBike.set(value);
    }
    
    public CRank(Integer colPosition, String colName, String colLevel, 
            String colFrontBrake, String colRearBrake, String colThrottle, 
            String colLean, String colGear, Integer colScore, String colBike) {
        this.colPosition = new SimpleIntegerProperty(colPosition);
        this.colName = new SimpleStringProperty(colName);
        this.colLevel = new SimpleStringProperty(colLevel);
        this.colFrontBrake = new SimpleStringProperty(colFrontBrake);
        this.colRearBrake = new SimpleStringProperty(colRearBrake);
        this.colThrottle = new SimpleStringProperty(colThrottle);
        this.colLean = new SimpleStringProperty(colLean);
        this.colGear = new SimpleStringProperty(colGear);
        this.colScore = new SimpleIntegerProperty(colScore);
        this.colBike = new SimpleStringProperty(colBike);
    }
    
    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", 
                        colPosition.get(),colName.get(),colLevel.get(),
                        colFrontBrake.get(),colRearBrake.get(),colThrottle.get(),
                        colLean.get(),colGear.get(),colScore.get(),
                        colBike.get()
                );
    }
}

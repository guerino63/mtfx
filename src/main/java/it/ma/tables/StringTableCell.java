package it.ma.tables;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class StringTableCell<S,T> extends AutoCommitTableCell<S,String>
{

    @Override
    protected String getInputValue() {
        return ((TextField) getInputField()).getText();
    }

    @Override
    protected void setInputValue(String value) {
        ((TextField) getInputField()).setText(value);
    }

    @Override
    protected String getDefaultValue() {
        return "";
    }

    @Override
    protected Node newInputField() {
        return new TextField();
    }

   @Override
    protected String inputValueToText(String newValue) {
        return newValue;
    }
}
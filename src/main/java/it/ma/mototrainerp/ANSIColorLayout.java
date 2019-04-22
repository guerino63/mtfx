package it.ma.mototrainerp;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;


/**
 * ANSIColorLayout is a Log4J Layout that formats messages using ANSI colors.
 *
 * Each level (DEBUG,INFO,WARN) has its own color that you can customize.
 * To use this file, in your log4j.properties or log4j.xml specify ANSIColorLayout
 * instead of a PatternLayout.
 *
 */
public class ANSIColorLayout extends PatternLayout {
    public static final String ANSI_BLACK = "\u001B[1;30m";
    public static final String ANSI_RED = "\u001B[1;31m";
    public static final String ANSI_GREEN = "\u001B[1;32m";
    public static final String ANSI_YELLOW = "\u001B[1;33m";
    public static final String ANSI_BLUE = "\u001B[1;34m";
    public static final String ANSI_PURPLE = "\u001B[1;35m";
    public static final String ANSI_CYAN = "\u001B[1;36m";
    public static final String ANSI_WHITE = "\u001B[1;37m";

    public static final String DEFAULT_COLOR_ALL = ANSI_WHITE;
    public static final String DEFAULT_COLOR_FATAL = ANSI_RED;
    public static final String DEFAULT_COLOR_ERROR = ANSI_RED;
    public static final String DEFAULT_COLOR_WARN = ANSI_YELLOW;
    public static final String DEFAULT_COLOR_INFO = ANSI_BLUE;
    public static final String DEFAULT_COLOR_DEBUG = ANSI_PURPLE;
    //public static final String DEFAULT_COLOR_RESET = "\u001B[1;37m";
    public static final String DEFAULT_COLOR_STACKTRACE = ANSI_CYAN;
    public static final String DEFAULT_COLOR = ANSI_BLACK;

    public ANSIColorLayout() {

        setDefaultColors();
    }

    public ANSIColorLayout(String string) {

        super(string);
        setDefaultColors();
    }

    /**
     * set the color patterns to the defaults
     */
    public void setDefaultColors() {

        all = DEFAULT_COLOR_ALL;
        fatal = DEFAULT_COLOR_FATAL;
        error = DEFAULT_COLOR_ERROR;
        warn = DEFAULT_COLOR_WARN;
        info = DEFAULT_COLOR_INFO;
        debug = DEFAULT_COLOR_DEBUG;
        stacktrace = DEFAULT_COLOR_STACKTRACE;
        defaultcolor = DEFAULT_COLOR;
    }

    /**
     * All - color string for events that do not have a specified type
     */
    private String all;

    public String getAll() {

        return all;
    }

    public void setAll(String inp) {

        all = inp;
    }

    /**
     * Fatal - color string for fatal events.  Default is red.
     */
    private String fatal;

    public String getFatal() {

        return fatal;
    }

    public void setFatal(String inp) {

        fatal = inp;
    }

    /**
     * Error - color string for error events.  Default is red.
     */
    private String error;

    public String getError() {

        return error;
    }

    public void setError(String inp) {

        error = inp;
    }

    /**
     * Warn - color string for warn events.  Default is yellow.
     */
    private String warn;

    public String getWarn() {

        return warn;
    }

    public void setWarn(String inp) {

        warn = inp;
    }

    /**
     * Info - color string for info events.  Default is gray.
     */
    private String info;

    public String getInfo() {

        return info;
    }

    public void setInfo(String inp) {

        info = inp;
    }

    /**
     * Debug - color string for debug events.  Default is blue.
     */
    private String debug;

    public String getDebug() {

        return debug;
    }

    public void setDebug(String inp) {

        debug = inp;
    }

    /**
     * stacktrace - color string for stacktrace events.  Default is red.
     */
    private String stacktrace;

    public String getStacktrace() {

        return stacktrace;
    }

    public void setStacktrace(String inp) {

        stacktrace = inp;
    }

    /**
     * defaultcolor - default terminal color.  this is the color that the terminal will be reset to after each line.  default is white
     */
    private String defaultcolor;

    public String getDefaultcolor() {

        return defaultcolor;
    }

    public void setDefaultcolor(String inp) {

        defaultcolor = inp;
    }

    public String format(LoggingEvent loggingEvent) {

        StringBuffer oBuffer = new StringBuffer();
        switch (loggingEvent.getLevel().toInt()) {
            case Level.ALL_INT:
                oBuffer.append(all);
                break;
            case Level.FATAL_INT:
                oBuffer.append(fatal);
                break;
            case Level.ERROR_INT:
                oBuffer.append(error);
                break;
            case Level.WARN_INT:
                oBuffer.append(warn);
                break;
            case Level.INFO_INT:
                oBuffer.append(info);
                break;
            case Level.DEBUG_INT:
                oBuffer.append(debug);
                break;
        }
        oBuffer.append(super.format(loggingEvent));
        oBuffer.append(defaultcolor);
        return oBuffer.toString();
    }

}

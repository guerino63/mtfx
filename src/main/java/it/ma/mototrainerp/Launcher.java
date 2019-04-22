
package it.ma.mototrainerp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Launcher {
    final static Log LOGGER = LogFactory.getLog(Launcher.class);

    public static void main(String[] args) {
        LOGGER.info(System.getProperty("java.vm.version"));
        LOGGER.info("JPath=" + System.getProperty("java.library.path"));
        Mototrainer.main(args);
    }
}
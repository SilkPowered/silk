package cx.rain.silk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Silk {
    private static Silk INSTANCE;

    private Logger logger = LoggerFactory.getLogger("Silk");

    private Silk() {
        INSTANCE = this;
    }

    public static Silk get() {
        if (INSTANCE == null) {
            INSTANCE = new Silk();
        }

        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }
}

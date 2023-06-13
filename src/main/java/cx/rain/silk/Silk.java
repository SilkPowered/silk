package cx.rain.silk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Silk {
    private static Silk INSTANCE;

    private Logger silkLogger = LoggerFactory.getLogger("Silk");
    private Logger loaderLogger = LoggerFactory.getLogger("Silk/Loader");

    private Silk() {
        INSTANCE = this;
    }

    public static Silk get() {
        if (INSTANCE == null) {
            INSTANCE = new Silk();
        }

        return INSTANCE;
    }

    public Logger getSilkLogger() {
        return silkLogger;
    }

    public Logger getLoaderLogger() {
        return loaderLogger;
    }
}

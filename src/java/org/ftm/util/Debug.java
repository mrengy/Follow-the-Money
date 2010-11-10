package org.ftm.util;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Nov 9, 2010
 */
public final class Debug {

    private static final boolean debug;

    static {
        debug = Boolean.valueOf(System.getProperty("debug", "false"));
    }

    public static boolean isDebug() {
        return debug;
    }
}

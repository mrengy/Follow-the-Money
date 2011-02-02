package org.ftm.ui;

import java.awt.EventQueue;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Nov 24, 2010
 */
public final class EventQueueUtils {

    private EventQueueUtils() {
        throw new AssertionError("Please do not call this nor change its visibility");
    }

    public static void invokeInEdt(Runnable runnable) {
        if(EventQueue.isDispatchThread()) {
            runnable.run();
        }
        else {
            EventQueue.invokeLater(runnable);
        }
    }
}

package org.ftm.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 16, 2010
 */
final class DataExtractor {

    private static final String dataFile = "/Users/hujol/Projects/followthemoney/data/transparencydata.com/contributions-amount-cat-order-rcpt-name.csv";

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Collection<String> recipientNamesBeingVisited = new ArrayList<String>(300);

    public static void main(String[] args) {
        File f = new File(dataFile);
        
    }
}

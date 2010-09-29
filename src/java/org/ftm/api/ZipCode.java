package org.ftm.api;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Aug 17, 2010
 */
public final class ZipCode {
    private final String zip5;

    /**
     * @param zip5 the 5 digits of a ZIP code.
     */
    public ZipCode(String zip5) {
        if (5 != zip5.length() || !NumberUtils.isDigits(zip5)) {
            throw new IllegalArgumentException(
                    "The ZIP code must contain only 5 digits! specified zip5: " + zip5);
        }

        this.zip5 = zip5;
    }

    /**
     * @return the 5 digits of the ZIP code.
     */
    public String getZip5() {
        return zip5;
    }

}

package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class Issue {

    private final String description;

    public Issue(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

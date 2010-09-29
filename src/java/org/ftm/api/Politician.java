package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class Politician {

    private final String lastName;
    private final String firstName;

    public Politician(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Politician");
        sb.append("{first name='").append(firstName).append('\'');
        sb.append("{last name='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

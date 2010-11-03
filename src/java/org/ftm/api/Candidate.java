package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class Candidate {

    private final String lastName;
    private final String firstName;
    private final int id;

    public Candidate(String lastName, String firstName) {
        this.id = -1;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Candidate(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public boolean hasId() {
        return -1 != id;
    }

    /**
     * @return the candidate ID
     * @throws IllegalStateException if {@link #hasId()} returns false.
     */
    public int getId() throws IllegalStateException {
        if(!hasId()) {
            throw new IllegalStateException("This instance does not have an ID. Call hasId() before calling this method.");
        }

        return id;
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
        sb.append("Candidate");
        sb.append("{first name='").append(firstName).append('\'');
        sb.append("{last name='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

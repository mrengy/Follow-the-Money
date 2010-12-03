package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class Contributor {

    private final String industryCategory;

    public Contributor(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    public String getIndustryCategory() {
        return industryCategory;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        final Contributor that = (Contributor) o;

        if(!industryCategory.equals(that.industryCategory)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return industryCategory.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Contributor");
        sb.append("{industry category='").append(industryCategory).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

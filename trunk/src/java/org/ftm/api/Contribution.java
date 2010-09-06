package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 6, 2010
 */
public final class Contribution {
    private Contributor contributorName;
    private float amountUSDollars;
    private Politician recipientName;

    public Contribution(Contributor contributorName, float amountUSDollars, Politician recipientName) {
        this.contributorName = contributorName;
        this.amountUSDollars = amountUSDollars;
        this.recipientName = recipientName;
    }

    public float getAmountUSDollars() {
        return amountUSDollars;
    }

    public Contributor getContributorName() {
        return contributorName;
    }

    public Politician getRecipientName() {
        return recipientName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Contribution");
        sb.append("{amount=").append(amountUSDollars);
        sb.append(", contributorName='").append(contributorName).append('\'');
        sb.append(", recipientName='").append(recipientName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

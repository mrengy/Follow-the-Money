package org.ftm.api;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 6, 2010
 */
public final class Contribution {
    private Contributor contributorName;
    private float amountUSDollars;
    private Candidate recipientName;
    private final Date date;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Contribution(Contributor contributorName, float amountUSDollars, Candidate recipientName, Date date) {
        this.contributorName = contributorName;
        this.amountUSDollars = amountUSDollars;
        this.recipientName = recipientName;
        this.date = date;
    }

    public float getAmountUSDollars() {
        return amountUSDollars;
    }

    public Contributor getContributorName() {
        return contributorName;
    }

    public Candidate getRecipientName() {
        return recipientName;
    }

    /**
     * @return the date or null.
     */
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Contribution");
        sb.append("{amount=").append(amountUSDollars);
        sb.append(", contributorName='").append(contributorName).append('\'');
        sb.append(", recipientName='").append(recipientName).append('\'');
        sb.append(", date='").append(SIMPLE_DATE_FORMAT.format(date)).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

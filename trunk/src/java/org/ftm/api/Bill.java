package org.ftm.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 1, 2010
 */
public final class Bill {

    private final String billNumber;
    private final String title;
    private final Date dateIntroduced;
    private final String outcome;
    private final Date outcomeStatusDate;
    private final String vote;
    private final List<String> issues;
    private final int yearVote;

    public Bill(BillBuilder builder) {
        this.title = builder.title;
        this.billNumber = builder.billNumber;
        this.dateIntroduced = builder.dateIntroduced;
        this.outcome = builder.outcome;
        this.outcomeStatusDate = builder.outcomeStatusDate;
        this.vote = builder.vote;
        this.yearVote = builder.yearVote;
        this.issues = Collections.unmodifiableList(builder.issues);
    }

    public String getTitle() {
        return title;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public Date getDateIntroduced() {
        return dateIntroduced;
    }

    public String getOutcome() {
        return outcome;
    }

    public Date getOutcomeStatusDate() {
        return outcomeStatusDate;
    }

    public String getVote() {
        return vote;
    }

    public List<String> getIssues() {
        return issues;
    }

    public int getYearVote() {
        return yearVote;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Bill");
        sb.append("{billNumber='").append(billNumber).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", dateIntroduced='").append(dateIntroduced).append('\'');
        sb.append(", outcome='").append(outcome).append('\'');
        sb.append(", outcomeStatusDate='").append(outcomeStatusDate).append('\'');
        sb.append(", vote='").append(vote).append('\'');
        sb.append(", issues=").append(issues);
        sb.append(", yearVote=").append(yearVote);
        sb.append('}');
        return sb.toString();
    }

    public static final class BillBuilder {

        private String billId = null;
        private String title = null;
        private String billNumber = null;
        private Date dateIntroduced = null;
        private String outcome = null;
        private String vote = null;
        private Date outcomeStatusDate = null;
        private int yearVote = -1;

        private final List<String> issues = new ArrayList<String>(4);

        public BillBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BillBuilder setBillNumber(String billNumber) {
            this.billNumber = billNumber;
            return this;
        }

        public BillBuilder setDateIntroduced(Date dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }

        public void setOutcomeStatusDate(Date outcomeStatusDate) {
            this.outcomeStatusDate = outcomeStatusDate;
        }

        public BillBuilder addIssue(String issue) {
            this.issues.add(issue);
            return this;
        }

        public Bill createBill() {
            final StringBuilder errors = new StringBuilder();
            appendToErrorsIfIsNull(errors, billId, "billId");
            appendToErrorsIfIsNull(errors, title, "title");
            appendToErrorsIfIsNull(errors, billNumber, "billNumber");
            appendToErrorsIfIsNull(errors, outcome, "outcome");
            appendToErrorsIfIsNull(errors, outcomeStatusDate, "outcomeStatusDate");
            appendToErrorsIfIsNull(errors, dateIntroduced, "dateIntroduced");
            if(-1 == yearVote) {
                errors.append(String.format("field %s is not set%n", "yearVote"));
            }
            if(0 != errors.length()) {
                errors.insert(0, "BillId: " + billId + "\n");
                throw new IllegalStateException(errors.toString());
            }

            return new Bill(this);
        }

        private static void appendToErrorsIfIsNull(StringBuilder errors, Object field, String objectName) {
            if(null == field) {
                errors.append(String.format("field %s is null%n", objectName));
            }
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }

        public void setVote(String vote) {
            this.vote = vote;
        }

        public void setYearVote(int yearVote) {
            this.yearVote = yearVote;
        }
    }
}

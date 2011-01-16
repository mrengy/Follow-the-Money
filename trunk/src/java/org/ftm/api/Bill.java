package org.ftm.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 1, 2010
 */
public final class Bill {

    private final String billNumber;
    private final String title;
    private final String dateIntroduced;
    private final String outcome;
    private final String outcomeStatusDate;
    private final List<String> issues;

    public Bill(BillBuilder builder) {
        this.title = builder.title;
        this.billNumber = builder.billNumber;
        this.dateIntroduced = builder.dateIntroduced;
        this.outcome = builder.outcome;
        this.outcomeStatusDate = builder.outcomeStatusDate;
        this.issues = Collections.unmodifiableList(builder.issues);
    }

    public String getTitle() {
        return title;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getDateIntroduced() {
        return dateIntroduced;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getOutcomeStatusDate() {
        return outcomeStatusDate;
    }

    public List<String> getIssues() {
        return issues;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Bill");
        sb.append("{billNumber='").append(billNumber).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", dateIntroduced='").append(dateIntroduced).append('\'');
        sb.append(", outcome=").append(outcome);
        sb.append(", outcomeStatusDate='").append(outcomeStatusDate).append('\'');
        sb.append(", issues=").append(issues);
        sb.append('}');
        return sb.toString();
    }

    public static final class BillBuilder {

        private String billId = null;
        private String title = null;
        private String billNumber = null;
        private String dateIntroduced = null;
        private String outcome = null;

        private String outcomeStatusDate = null;
        private final List<String> issues = new ArrayList<String>(4);

        public BillBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BillBuilder setBillNumber(String billNumber) {
            this.billNumber = billNumber;
            return this;
        }

        public BillBuilder setDateIntroduced(String dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }

        public void setOutcomeStatusDate(String outcomeStatusDate) {
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
    }
}

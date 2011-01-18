package org.ftm.ui;

import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.Contributor;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
public final class Model {

    private ZipCode zipCode = null;
    private Candidate candidateSelected = null;
    private List<Issue> issues = null;
    private List<Contribution> contributions = null;
    private Contributor contributorSelected;
    private Issue issueSelected;

    private static final Model singleton = new Model();

    private Model() {
    }

    public static Model getSingleton() {
        return singleton;
    }

    public synchronized List<Contribution> getContributions() {
        return contributions;
    }

    public synchronized void setContributions(List<Contribution> contributions) {
        this.contributions = Collections.unmodifiableList(contributions);
    }

    public synchronized List<Issue> getIssues() {
        return issues;
    }

    public synchronized void setIssues(List<Issue> issues) {
        this.issues = Collections.unmodifiableList(issues);
    }

    public synchronized Candidate getCandidateSelected() {
        return candidateSelected;
    }

    public synchronized void setCandidateSelected(Candidate candidateSelected) {
        this.candidateSelected = candidateSelected;
    }

    public synchronized ZipCode getZipCode() {
        return zipCode;
    }

    public synchronized void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }

    private List<Bill> bills = new ArrayList<Bill>(8);

    public synchronized List<Bill> getBills() {
        return Collections.unmodifiableList(bills);
    }

    public synchronized void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Issue getIssueSelected() {
        return issueSelected;
    }

    public Contributor getContributorSelected() {
        return contributorSelected;
    }

    public void setContributorSelected(Contributor contributorSelected) {
        this.contributorSelected = contributorSelected;
    }

    public void setIssueSelected(Issue issueSelected) {
        this.issueSelected = issueSelected;
    }
}

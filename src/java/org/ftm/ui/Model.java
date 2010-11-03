package org.ftm.ui;

import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;

import java.util.Collections;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
final class Model {

    private ZipCode zipCode = null;
    private Candidate candidateSelected = null;
    private List<Issue> issues = null;
    private List<Contribution> contributions = null;

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

    public synchronized Candidate getPoliticianSelected() {
        return candidateSelected;
    }

    public synchronized void setPoliticianSelected(Candidate candidateSelected) {
        this.candidateSelected = candidateSelected;
    }

    public synchronized ZipCode getZipCode() {
        return zipCode;
    }

    public synchronized void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }
}

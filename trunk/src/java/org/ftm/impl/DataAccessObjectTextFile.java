package org.ftm.impl;

import org.apache.commons.lang.StringUtils;
import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class DataAccessObjectTextFile implements DataAccessObject {
    public Collection<Contribution> getContributions(String candidateName) throws Exception {
        throw new IllegalStateException("org.ftm.impl.DataAccessObjectTextFile.getContributions Not implemented");
    }

    public List<Issue> getIssues() throws Exception {
        String rawData = "Abortion Issues\n" +
            "Agriculture Issues\n" +
            "Animal Rights and Wildlife Issues\n" +
            "Arts and Humanities\n" +
            "Budget, Spending and Taxes\n" +
            "Business and Consumers\n" +
            "Campaign Finance and Election Issues\n" +
            "Civil Liberties and Civil Rights\n" +
            "Congressional and Legislative Affairs\n" +
            "Conservative\n" +
            "Crime Issues\n" +
            "Death Penalty\n" +
            "Defense\n" +
            "Drug Issues\n" +
            "Education\n" +
            "Employment and Affirmative Action\n" +
            "Energy Issues\n" +
            "Environmental Issues\n" +
            "Executive Branch\n" +
            "Family and Children Issues\n" +
            "Federal, State, and Local Relations\n" +
            "Foreign Aid and Policy Issues\n" +
            "Gambling and Gaming\n" +
            "Government Reform\n" +
            "Gun Issues\n" +
            "Health Issues\n" +
            "Housing and Property Issues\n" +
            "Immigration\n" +
            "Indigenous Peoples\n" +
            "Labor\n" +
            "Legal Issues\n" +
            "Liberal\n" +
            "Military Issues\n" +
            "National Security Issues\n" +
            "Reproductive Issues\n" +
            "Science and Medical Research\n" +
            "Senior and Social Security Issues\n" +
            "Sexual Orientation and Gender Identity\n" +
            "Social Issues\n" +
            "Stem Cell Research\n" +
            "Technology and Communication\n" +
            "Trade Issues\n" +
            "Transportation Issues\n" +
            "Veterans Issues\n" +
            "Welfare and Poverty\n" +
            "Women's Issues";
        final String[] issueNames = StringUtils.split(rawData, "\n");
        List<Issue> issues = new ArrayList<Issue>(32);
        for(String issueName : issueNames) {
            if(!StringUtils.isBlank(issueName)) {
                issues.add(new Issue(issueName));
            }
        }
        return issues;
    }

    public List<Candidate> getCandidates() throws Exception {
        final String names =
            "Richard Shelby\n" +
                "Jefferson Sessions, III\n" +
                "Josiah Bonner, Jr.\n" +
                "Bobby Bright\n" +
                "Michael Rogers\n" +
                "Robert Aderholt\n" +
                "R. Griffith\n" +
                "Spencer Bachus\n" +
                "Artur Davis";
        final String[] candidateNames = StringUtils.split(names, "\n");
        List<Candidate> candidates = new ArrayList<Candidate>(9);
        for(String candidateName : candidateNames) {
            if(!StringUtils.isBlank(candidateName)) {
                final String[] strings = candidateName.split(" ");
                candidates.add(new Candidate(strings[0], 2 == strings.length ? strings[1] : "n/a"));
            }
        }
        return candidates;
    }

    public List<Candidate> getCandidates(ZipCode zipCode) throws Exception {
        throw new IllegalStateException("org.ftm.impl.DataAccessObjectTextFile.getCandidates Not implemented");
    }

    public List<Bill> getBills(Candidate p) throws Exception {
        throw new IllegalStateException("org.ftm.impl.DataAccessObjectTextFile.getBills Not implemented");
    }

    public Collection<Candidate> getCandidates(String s) throws Exception {
        throw new IllegalStateException("org.ftm.impl.DataAccessObjectTextFile.getCandidates Not implemented");
    }

    public void shutdown() {
        // Clean and shutdown services
    }

    public static void main(String[] args) throws Exception {

        DataAccessObject dao = new DataAccessObjectTextFile();


        for(Candidate candidate : dao.getCandidates()) {
            System.out.println("candidate: " + candidate.getLastName());
        }
        //        for (Issue issue : new DataAccessObjectTextFile().getIssues()) {
        //            System.out.println("issue " + issue.getDescription());
        //        }
    }
}

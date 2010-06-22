package org.ftm.impl;

import org.apache.commons.lang.StringUtils;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">johnny_hujol@vrtx.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public final class DataAccessObjectTextFile implements DataAccessObject {
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
        for (String issueName : issueNames) {
            if (!StringUtils.isBlank(issueName)) {
                issues.add(new Issue(issueName));
            }
        }
        return issues;
    }

    public static void main(String[] args) throws Exception {
        for (Issue issue : new DataAccessObjectTextFile().getIssues()) {
            System.out.println("issue " + issue.getDescription());
        }
    }
}

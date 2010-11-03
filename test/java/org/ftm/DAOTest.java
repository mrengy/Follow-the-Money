package org.ftm;

import org.ftm.api.Contribution;
import org.ftm.api.DataAccessObject;
import org.ftm.impl.SimpleDataAccessObject;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 28, 2010
 */
final class DAOTest {

    public static void main(String[] args) throws Exception {
        final DataAccessObject dao = new SimpleDataAccessObject();

        // Bad because I expose methods that one should not access!
        //        final SimpleDataAccessObject dao = new SimpleDataAccessObject();

        //        final List<Candidate> candidates = dao.getPoliticians(new ZipCode("02143"));
        //        for (Candidate candidate : candidates) {
        //            System.out.println(String.format(
        //                    "Candidate first name: %s\tlastname: %s",
        //                    candidate.getFirstName(),
        //                    candidate.getLastName()
        //            ));
        //        }
        //
        //        final List<Issue> issues = dao.getIssues();
        //        for (Issue issue : issues) {
        //            System.out.println("issue = " + issue);
        //        }

        final Collection<Contribution> contributions = dao.getContributions(null); // "Kerry"
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
        StringBuilder sb = new StringBuilder();
        for(Contribution contribution : contributions) {
            // year , month, candidate name, contributor indus, contrib amount
            final Date date = contribution.getDate();
            final String year;
            final String month;
            if(null != date) {
                year = dateFormatYear.format(date);
                month = dateFormatMonth.format(date);
            }
            else {
                year = "n/a";
                month = "n/a";
            }
            sb.append(String.format("%s, %s, %s, %s, %s%n",
                year,
                month,
                contribution.getRecipientName().getLastName(),
                contribution.getContributorName().getIndustryCategory(),
                contribution.getAmountUSDollars()
            ));
        }

        /*
                final List<Bill> bills = dao.getBills(new Candidate(32795, "Berry", "Deborah"));
                for(Bill bill : bills) {
                    //                                final Date date = bill.getDate();
                    final String year = "2008";
                    final String month = "n/a";
                    //                                if(null != date) {
                    //                                    year = dateFormatYear.format(date);
                    //                                    month = dateFormatMonth.format(date);
                    //                                }
                    //                                else {
                    //                                    year = "n/a";
                    //                                    month = "n/a";
                    //                                }
                    // year , month, candidate name, issue, votes (yes or no)
                    //            System.out.println("bill = " + bill);
                    sb.append(String.format("%s, %s, %s, %s, %s%n",
                        year,
                        month,
                        "Berry",
                        bill.getIssue(),
                        bill.getVote().toString()
                    ));
                }
        */
        System.out.println(sb.toString());
    }
}

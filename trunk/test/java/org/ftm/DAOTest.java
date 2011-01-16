package org.ftm;

import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.DataAccessObject;
import org.ftm.impl.SimpleDataAccessObject;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 28, 2010
 */
final class DAOTest {

    public static void main(String[] args) throws Exception {
        final DataAccessObject dao = new SimpleDataAccessObject();

        // Bad because I expose methods that one should not access!
        //        final SimpleDataAccessObject dao = new SimpleDataAccessObject();

        //        final List<Candidate> candidates = dao.getCandidates(new ZipCode("02143"));
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

        StringBuilder sb = new StringBuilder();

        /*
                final Collection<Contribution> contributions = dao.getContributions(null); // "Kerry"
                SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
                SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
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
        */

        final long currentTime65 = System.currentTimeMillis();
        final List<Bill> bills = dao.getBills(new Candidate(32795, "Berry", "Deborah"));
        final long delta65 = System.currentTimeMillis() - currentTime65;
        System.out.println("**** Executed getBills in " + new DecimalFormat("0.0000").format(delta65 / 1000f) + "s");

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
            sb.append(String.format("%s, %s, %s, %s%n",
                year,
                month,
                "Berry",
                bill.toString()
            ));
        }
        System.out.println(sb.toString());
        dao.shutdown();
    }
}

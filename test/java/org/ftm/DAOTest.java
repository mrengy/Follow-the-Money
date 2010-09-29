package org.ftm;

import org.ftm.api.Contribution;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.Politician;
import org.ftm.api.ZipCode;
import org.ftm.impl.SimpleDataAccessObject;

import java.util.Collection;
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

        //        final Collection<Contribution> ss = dao.getContributions("kerry");
        final List<Politician> politicians = dao.getPoliticians(new ZipCode("02143"));
        //        final List<Politician> ss = dao.getPoliticians();

        for (Politician politician : politicians) {
            System.out.println(String.format(
                    "Politician first name: %s\tlastname: %s",
                    politician.getFirstName(),
                    politician.getLastName()
            ));
        }
        //        for (Contribution s : ss) {
        //            System.out.println(s);
        //        }
        final List<Issue> issues = dao.getIssues();
        for (Issue issue : issues) {
            System.out.println("issue = " + issue);
        }

        final Collection<Contribution> contributions = dao.getContributions("kerry");
        for (Contribution contribution : contributions) {
            System.out.println("contrib: " + contribution);
        }
    }
}

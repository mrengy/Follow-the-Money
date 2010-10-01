package org.ftm.api;

import java.util.Collection;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public interface DataAccessObject {
    //    Collection<Politician> getCandidates() throws Exception;
    //

    Collection<Contribution> getContributions(String politicianName) throws Exception;

    List<Issue> getIssues() throws Exception;

    List<Politician> getPoliticians() throws Exception;

    List<Politician> getPoliticians(ZipCode zipCode) throws Exception;

    List<Bill> getBills(Politician p) throws Exception;
}                                           
